package main;

import data.Grille;
import data.Case;
import data.Personnage;
import core.Astar;

import java.util.List;

/**
 * Classe principale pour tester A* et le déplacement du personnage.
 */
public class Main {
    public static void main(String[] args) {
        Grille grille = new Grille(10, 10);
        grille.ajouterObstacle(3, 3);
        grille.ajouterObstacle(3, 4);
        grille.ajouterObstacle(3, 5);

        Case depart = grille.getCase(0, 0);
        Case arrivee = grille.getCase(9, 9);

        Astar astar = new Astar(grille, depart, arrivee);
        List<Case> chemin = astar.calculerChemin();

        if (chemin != null) {
            Personnage personnage = new Personnage("Alice", depart, grille);
            for (Case step : chemin) {
                personnage.setPosition(step);
                System.out.println(personnage);
            }
            System.out.println("Personnage arrivé !");
        } else {
            System.out.println("Aucun chemin trouvé.");
        }
    }
}

