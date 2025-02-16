package test;

import Environnement.Case;
import Environnement.Grille;
import Algorithmes.Astar;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Grille grille = new Grille(10, 10);

  
        grille.ajouterObstacle(1, 1);
        grille.ajouterObstacle(2, 2);
        grille.ajouterObstacle(3, 3);

        Case depart = grille.getCase(0, 0);
        Case arrivee = grille.getCase(9, 9);

        Astar astar = new Astar(grille, depart, arrivee);
        ArrayList<Case> chemin = astar.calculerChemin();

        if (chemin != null) {
            System.out.println("Chemin trouvé :");
            for (Case c : chemin) {
                System.out.println(c);
            }
        } else {
            System.out.println("Aucun chemin trouvé.");
        }
    }
}
