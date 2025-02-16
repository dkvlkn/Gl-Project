package test;

import Algorithmes.Astar;
import Environnement.Case;
import Environnement.Grille;
import java.util.ArrayList;

public class MainAstar {
    public static void main(String[] args) {

        int lignes = 5, colonnes = 5;
        Grille grille = new Grille(lignes, colonnes);

        Case depart = grille.getCase(0, 0);
        Case arrivee = grille.getCase(4, 4);

        Astar astar = new Astar(grille, depart, arrivee);

        ArrayList<Case> chemin = astar.calculerChemin();

        if (chemin != null) {
            System.out.println("Chemin trouvé :");
            for (Case c : chemin) {
                System.out.println(c);
            }
        } else {
            System.out.println("Aucun chemin trouvé !");
        }
    }
}
