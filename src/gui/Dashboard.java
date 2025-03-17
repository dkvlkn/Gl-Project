package gui;

import core.Astar;
import data.Grille;
import data.Case;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panneau principal affichant la grille et la simulation du personnage.
 */
public class Dashboard extends JPanel {
    private Grille grille;
    private List<Case> chemin;
    private boolean ajoutObstacleMode = false;
    private int obstaclesAjoutes = 0; // Nombre d'obstacles ajoutés
    private static final int MAX_OBSTACLES = 10;
    private int cellSize = 50;

    public Dashboard(int rows, int cols) {
        this.grille = new Grille(rows, cols);
        this.chemin = null;
    }

    /**
     * Active le mode d'ajout d'obstacles via une interface plus structurée.
     */
    public void activerAjoutObstacle() {
        if (obstaclesAjoutes < MAX_OBSTACLES) {
            int row = demanderValeur("Entrez la ligne de l'obstacle : ", grille.getRows());
            int col = demanderValeur("Entrez la colonne de l'obstacle : ", grille.getCols());

            if (grille.estDansLesBornes(row, col) && !grille.getCase(row, col).isObstacle()) {
                grille.toggleObstacle(row, col);
                obstaclesAjoutes++;
                repaint();
            } else {
                afficherMessage("Position invalide ou déjà occupée !");
            }
        } else {
            afficherMessage("Vous avez atteint la limite de " + MAX_OBSTACLES + " obstacles !");
        }
    }

    /**
     * Méthode pour demander une entrée utilisateur.
     */
    private int demanderValeur(String message, int max) {
        int valeur = -1;
        while (valeur < 0 || valeur >= max) {
            try {
                String input = JOptionPane.showInputDialog(message);
                valeur = Integer.parseInt(input);
            } catch (Exception e) {
                afficherMessage("Veuillez entrer un nombre valide !");
            }
        }
        return valeur;
    }

    /**
     * Affiche un message à l'utilisateur.
     */
    private void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Lance l'algorithme A* et anime le déplacement du personnage.
     */
    public void lancerAStar() {
        Astar astar = new Astar(grille, grille.getCase(0, 0), grille.getCase(grille.getRows() - 1, grille.getCols() - 1));
        chemin = astar.calculerChemin();

        if (chemin == null || chemin.isEmpty()) {
            afficherMessage("Aucun chemin trouvé !");
            return;
        }

        Timer timer = new Timer(300, e -> {
            if (!chemin.isEmpty()) {
                grille.setPersonnage(chemin.remove(0));
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    /**
     * Génère un labyrinthe aléatoire.
     */
    public void genererLabyrinthe() {
        grille.genererLabyrintheAleatoire();
        repaint();
    }

    /**
     * Réinitialise la grille.
     */
    public void resetGrille() {
        grille.reset();
        chemin = null;
        obstaclesAjoutes = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rows = grille.getRows();
        int cols = grille.getCols();
        cellSize = Math.min(getWidth() / cols, getHeight() / rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Case c = grille.getCase(i, j);
                if (c.isObstacle()) {
                    g.setColor(Color.BLACK);
                } else if (chemin != null && chemin.contains(c)) {
                    g.setColor(Color.RED);
                } else if (c.equals(grille.getPersonnage())) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }
}
