package GUI;

import Environnement.Grille;
import Environnement.Case;
import Algorithmes.Astar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI extends JFrame {
    private static final int SIZE = 50;
    private Grille grille;
    private ArrayList<Case> chemin;

    public MainGUI(Grille grille, ArrayList<Case> chemin) {
        this.grille = grille;
        this.chemin = chemin;
        setTitle("Affichage A*");
        setSize(grille.getNombreColonnes() * SIZE + 100, grille.getNombreLignes() * SIZE + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < grille.getNombreLignes(); i++) {
            for (int j = 0; j < grille.getNombreColonnes(); j++) {
                Case c = grille.getCase(i, j);
                if (chemin.contains(c)) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * SIZE + 50, i * SIZE + 50, SIZE, SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * SIZE + 50, i * SIZE + 50, SIZE, SIZE);
            }
        }
    }

    public static void main(String[] args) {
        Grille grille = new Grille(10, 10);
        Case depart = grille.getCase(0, 0);
        Case arrivee = grille.getCase(9, 9);

        Astar aStar = new Astar(grille, depart, arrivee);
        ArrayList<Case> chemin = aStar.calculerChemin();

        SwingUtilities.invokeLater(() -> {
            MainGUI fenetre = new MainGUI(grille, chemin);
            fenetre.setVisible(true);
        });
    }
}
