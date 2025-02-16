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

public class AstarGUI extends JFrame {
    private static final int SIZE = 30;
    private Grille grille;
    private Case depart;
    private Case arrivee;
    private Set<Case> chemin;
    private boolean ajoutObstacleMode = false;

    public AstarGUI() {
        setTitle("A* - Simulation avec Obstacles");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        grille = new Grille(10, 10);
        depart = grille.getCase(0, 0);
        arrivee = grille.getCase(9, 9);
        chemin = new HashSet<>();

        JButton astarButton = new JButton("Lancer A*");
        JButton ajouterObstacleButton = new JButton("Ajouter Obstacles");

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(astarButton);
        panelBoutons.add(ajouterObstacleButton);
        add(panelBoutons, BorderLayout.SOUTH);

        astarButton.addActionListener(e -> {
            Astar astar = new Astar(grille, depart, arrivee);
            chemin = new HashSet<>(astar.calculerChemin());
            repaint();
        });

        ajouterObstacleButton.addActionListener(e -> {
            ajoutObstacleMode = !ajoutObstacleMode;
            JOptionPane.showMessageDialog(this, "Cliquez sur une case pour ajouter/supprimer un obstacle.");
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ajoutObstacleMode) {
                    int colonne = (e.getX() - 50) / SIZE;
                    int ligne = (e.getY() - 50) / SIZE;

                    if (grille.estDansLesBornes(ligne, colonne)) {
                        grille.toggleObstacle(ligne, colonne);
                        repaint();
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < grille.getNombreLignes(); i++) {
            for (int j = 0; j < grille.getNombreColonnes(); j++) {
                Case c = grille.getCase(i, j);
                g.setColor(grille.estObstacle(i, j) ? Color.GRAY : Color.WHITE);
                g.fillRect(j * SIZE + 50, i * SIZE + 50, SIZE, SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * SIZE + 50, i * SIZE + 50, SIZE, SIZE);
            }
        }
    }
}
