package gui;

import core.Astar;
import data.Case;
import data.Grille;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Interface graphique pour la simulation de l'algorithme A*.
 */
public class AstarGUI extends JFrame {
    private static final int TAILLE_CASE = 50;
    private Grille grille;
    private Case depart;
    private Case arrivee;
    private List<Case> chemin;
    private boolean ajoutObstacleMode = false;

    /**
     * Constructeur de l'interface graphique.
     */
    public AstarGUI() {
        setTitle("A* - Simulation avec Obstacles");
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        grille = new Grille(10, 10);
        depart = grille.getCase(0, 0);
        arrivee = grille.getCase(9, 9); // L'arrivée est automatiquement placée en bas à droite

        JPanel panneauGrille = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dessinerGrille(g);
            }
        };
        panneauGrille.setPreferredSize(new Dimension(500, 500));
        panneauGrille.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getY() / TAILLE_CASE;
                int y = e.getX() / TAILLE_CASE;

                if (grille.estDansLesBornes(x, y)) {
                    Case caseCliquee = grille.getCase(x, y);

                    if (ajoutObstacleMode && !caseCliquee.equals(depart) && !caseCliquee.equals(arrivee)) {
                        grille.toggleObstacle(x, y);
                        repaint();
                    }
                }
            }
        });

        // Boutons de contrôle
        JButton btnAjoutObstacle = new JButton("Mode: Ajouter Obstacles");
        JButton btnLancerAstar = new JButton("Lancer A*");
        JButton btnReset = new JButton("Réinitialiser");

        btnAjoutObstacle.addActionListener(e -> toggleModeObstacle());
        btnLancerAstar.addActionListener(e -> lancerAstar(panneauGrille));
        btnReset.addActionListener(e -> resetSimulation());

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnAjoutObstacle);
        panelBoutons.add(btnLancerAstar);
        panelBoutons.add(btnReset);

        add(panneauGrille, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Active/désactive le mode d'ajout d'obstacles.
     */
    private void toggleModeObstacle() {
        ajoutObstacleMode = !ajoutObstacleMode;
    }

    /**
     * Exécute l'algorithme A* après placement des obstacles.
     */
    private void lancerAstar(JPanel panneauGrille) {
        Astar astar = new Astar(grille, depart, arrivee);
        chemin = astar.calculerChemin();

        if (chemin == null || chemin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun chemin trouvé !");
        }

        panneauGrille.repaint();
    }

    /**
     * Réinitialise la grille et permet de tout recommencer.
     */
    private void resetSimulation() {
        dispose();
        new AstarGUI();
    }

    /**
     * Dessine la grille et les différents éléments (obstacles, départ, arrivée, chemin).
     */
    private void dessinerGrille(Graphics g) {
        for (int i = 0; i < grille.getNombreLignes(); i++) {
            for (int j = 0; j < grille.getNombreColonnes(); j++) {
                Case c = grille.getCase(i, j);

                // Définition des couleurs
                if (c.equals(depart)) {
                    g.setColor(Color.RED); // Départ en rouge
                } else if (c.equals(arrivee)) {
                    g.setColor(Color.GREEN); // Arrivée en vert
                } else if (c.isObstacle()) {
                    g.setColor(Color.BLACK); // Obstacles en noir
                } else if (chemin != null && chemin.contains(c)) {
                    g.setColor(Color.BLUE); // Chemin en bleu
                } else {
                    g.setColor(Color.WHITE); // Cases normales en blanc
                }

                // Dessin de la case
                g.fillRect(j * TAILLE_CASE + 50, i * TAILLE_CASE + 50, TAILLE_CASE, TAILLE_CASE);
                g.setColor(Color.GRAY);
                g.drawRect(j * TAILLE_CASE + 50, i * TAILLE_CASE + 50, TAILLE_CASE, TAILLE_CASE);
            }
        }
    }

    /**
     * Méthode principale pour exécuter l'interface graphique.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AstarGUI::new);
    }
}
