package GUI;

import Algorithmes.Astar;
import Environnement.Case;
import Environnement.Grille;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant l'interface graphique pour la simulation de l'algorithme A*.
 * Permet d'afficher une grille, de définir un départ et une arrivée, et d'ajouter des obstacles.
 */
public class AstarGUI extends JFrame {
    private static final int SIZE = 30;
    private Grille grille;
    private Case depart;
    private Case arrivee;
    private Set<Case> chemin; 
    private boolean ajoutObstacleMode = false;

    /**
     * Constructeur de la classe AstarGUI.
     * Initialise la fenêtre, la grille et les boutons pour interagir avec l'algorithme.
     */
    public AstarGUI() {
        setTitle("A* - Simulation avec Obstacles");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        grille = new Grille(10, 10); 
        depart = grille.getCase(0, 0); 
        arrivee = grille.getCase(9, 9);
        chemin = new HashSet<>();

        // Création des boutons pour exécuter l'algorithme et ajouter des obstacles
        JButton astarButton = new JButton("Lancer A*");
        JButton ajouterObstacleButton = new JButton("Ajouter Obstacles");

        // Panneau contenant les boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(astarButton);
        panelBoutons.add(ajouterObstacleButton);
        add(panelBoutons, BorderLayout.SOUTH);

        /**
         * Action associée au bouton "Lancer A*".
         * Exécute l'algorithme A* et met à jour le chemin trouvé.
         */
        astarButton.addActionListener(e -> {
            Astar astar = new Astar(grille, depart, arrivee);
            chemin = new HashSet<>(astar.calculerChemin());
            repaint(); // Redessine la fenêtre avec le chemin mis à jour
        });

        /**
         * Action associée au bouton "Ajouter Obstacles".
         * Active ou désactive le mode permettant d'ajouter des obstacles sur la grille.
         */
        ajouterObstacleButton.addActionListener(e -> {
            ajoutObstacleMode = !ajoutObstacleMode;
            JOptionPane.showMessageDialog(this, "Cliquez sur une case pour ajouter/supprimer un obstacle.");
        });

        /**
         * Gestion des clics de souris pour ajouter ou supprimer des obstacles.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ajoutObstacleMode) {
                    int colonne = (e.getX() - 50) / SIZE;
                    int ligne = (e.getY() - 50) / SIZE;

                    if (grille.estDansLesBornes(ligne, colonne)) {
                        grille.toggleObstacle(ligne, colonne); // Ajoute ou enlève un obstacle
                        repaint(); // Redessine la grille après modification
                    }
                }
            }
        });
    }

    /**
     * Méthode de dessin qui affiche la grille et ses éléments.
     * 
     * @param g Objet Graphics utilisé pour dessiner la grille.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < grille.getNombreLignes(); i++) {
            for (int j = 0; j < grille.getNombreColonnes(); j++) {
                Case c = grille.getCase(i, j);
                
                // Définition de la couleur des cases
                if (grille.estObstacle(i, j)) {
                    g.setColor(Color.GRAY); // Obstacle
                } else {
                    g.setColor(Color.WHITE); // Case normale
                }
                
                // Dessin de la case
                g.fillRect(j * SIZE + 50, i * SIZE + 50, SIZE, SIZE);
                
                // Contour de la case
                g.setColor(Color.BLACK);
                g.drawRect(j * SIZE + 50, i * SIZE + 50, SIZE, SIZE);
            }
        }
    }
}
