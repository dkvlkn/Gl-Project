package GUI;

import Algorithmes.Astar;
import Algorithmes.MinMax;
import Environnement.Case;
import Environnement.Grille;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant une interface graphique permettant de comparer les algorithmes A* et MinMax.
 * L'utilisateur peut ajouter des obstacles et observer le chemin trouvé par chaque algorithme.
 */
public class AstarMinMaxGUI extends JFrame {
    private static final int SIZE = 50; 
    private static final int GRID_SIZE = 10; 

    private Grille grille; 
    private Case depart;
    private Case arrivee; 
    private Set<Case> cheminAStar; 
    private Set<Case> cheminMinMax; 
    private boolean ajoutObstacleMode = false; 

    /**
     * Constructeur de la classe AstarMinMaxGUI.
     * Initialise la fenêtre, la grille et les boutons pour interagir avec les algorithmes.
     */
    public AstarMinMaxGUI() {
        setTitle("A* et MinMax - Simulation");
        setSize(GRID_SIZE * SIZE + 100, GRID_SIZE * SIZE + 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        grille = new Grille(GRID_SIZE, GRID_SIZE); // Initialisation de la grille
        depart = grille.getCase(0, 0); // Position de départ en haut à gauche
        arrivee = grille.getCase(GRID_SIZE - 1, GRID_SIZE - 1); // Position d'arrivée en bas à droite
        cheminAStar = new HashSet<>();
        cheminMinMax = new HashSet<>();

        // Boutons pour interagir avec l'interface
        JButton astarButton = new JButton("Lancer A*");
        JButton minmaxButton = new JButton("Lancer MinMax");
        JButton ajouterObstacleButton = new JButton("Ajouter Obstacles");

        // Panneau contenant les boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(astarButton);
        panelBoutons.add(minmaxButton);
        panelBoutons.add(ajouterObstacleButton);
        add(panelBoutons, BorderLayout.SOUTH);

        /**
         * Action associée au bouton "Lancer A*".
         * Exécute l'algorithme A* et met à jour le chemin trouvé.
         */
        astarButton.addActionListener(e -> {
            Astar astar = new Astar(grille, depart, arrivee);
            ArrayList<Case> chemin = astar.calculerChemin();
            cheminAStar = chemin != null ? new HashSet<>(chemin) : new HashSet<>();
            cheminMinMax.clear(); // On efface le chemin MinMax pour ne pas afficher les deux simultanément
            repaint(); // Redessine la fenêtre avec le nouveau chemin
        });

        /**
         * Action associée au bouton "Lancer MinMax".
         * Exécute l'algorithme MinMax et met à jour le chemin trouvé.
         */
        minmaxButton.addActionListener(e -> {
            MinMax minmax = new MinMax(grille, depart, arrivee);
            ArrayList<Case> chemin = minmax.calculerChemin();
            cheminMinMax = chemin != null ? new HashSet<>(chemin) : new HashSet<>();
            cheminAStar.clear(); // On efface le chemin A* pour éviter la superposition
            repaint(); // Redessine la fenêtre
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

                    if (ligne >= 0 && ligne < grille.getNombreLignes() && colonne >= 0 && colonne < grille.getNombreColonnes()) {
                        Case c = grille.getCase(ligne, colonne);
                        if (!c.equals(depart) && !c.equals(arrivee)) { // On empêche de modifier les cases de départ et d'arrivée
                            grille.toggleObstacle(ligne, colonne);
                            repaint(); // Redessine la grille après modification
                        }
                    }
                }
            }
        });
    }

    /**
     * Méthode de dessin qui affiche la grille et ses éléments (départ, arrivée, obstacles, chemins trouvés).
     * 
     * @param g Objet Graphics utilisé pour dessiner la grille.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < grille.getNombreLignes(); i++) {
            for (int j = 0; j < grille.getNombreColonnes(); j++) {
                Case c = grille.getCase(i, j);
                
                // Définition de la couleur des cases selon leur rôle
                if (c.equals(depart)) {
                    g.setColor(Color.GREEN); // Case de départ
                } else if (c.equals(arrivee)) {
                    g.setColor(Color.RED); // Case d'arrivée
                } else if (grille.estObstacle(i, j)) {
                    g.setColor(Color.GRAY); // Obstacle
                } else if (cheminAStar.contains(c)) {
                    g.setColor(Color.BLUE); // Chemin trouvé par A*
                } else if (cheminMinMax.contains(c)) {
                    g.setColor(Color.ORANGE); // Chemin trouvé par MinMax
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

    /**
     * Méthode principale qui lance l'interface graphique.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AstarMinMaxGUI().setVisible(true));
    }
}
