package GUI;

import Environnement.Grille;
import Environnement.Case;
import Algorithmes.Astar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une interface graphique permettant d'afficher le chemin trouvé par l'algorithme A*.
 */
public class MainGUI extends JFrame {
    private static final int SIZE = 50;
    private Grille grille;
    private ArrayList<Case> chemin; 

    /**
     * Constructeur de la classe MainGUI.
     * Initialise la fenêtre avec la grille et le chemin calculé.
     *
     * @param grille Grille représentant l'environnement.
     * @param chemin Liste des cases formant le chemin trouvé par l'algorithme A*.
     */
    public MainGUI(Grille grille, ArrayList<Case> chemin) {
        this.grille = grille;
        this.chemin = chemin;
        setTitle("Affichage A*");
        setSize(grille.getNombreColonnes() * SIZE + 100, grille.getNombreLignes() * SIZE + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
    }

    /**
     * Méthode de dessin qui affiche la grille et le chemin trouvé par A*.
     * 
     * @param g Objet Graphics utilisé pour dessiner la grille et le chemin.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < grille.getNombreLignes(); i++) {
            for (int j = 0; j < grille.getNombreColonnes(); j++) {
                Case c = grille.getCase(i, j);

                // Si la case fait partie du chemin trouvé par A*, elle est colorée en bleu
                if (chemin.contains(c)) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.WHITE); // Sinon, elle est blanche
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
     * Méthode principale qui lance l'interface graphique et exécute l'algorithme A*.
     */
    public static void main(String[] args) {
        // Création d'une grille de 10x10 cases
        Grille grille = new Grille(10, 10);
        Case depart = grille.getCase(0, 0); // Case de départ en haut à gauche
        Case arrivee = grille.getCase(9, 9); // Case d'arrivée en bas à droite

        // Exécution de l'algorithme A* pour trouver un chemin entre départ et arrivée
        Astar aStar = new Astar(grille, depart, arrivee);
        ArrayList<Case> chemin = aStar.calculerChemin();

        // Création et affichage de la fenêtre graphique
        SwingUtilities.invokeLater(() -> {
            MainGUI fenetre = new MainGUI(grille, chemin);
            fenetre.setVisible(true);
        });
    }
}
