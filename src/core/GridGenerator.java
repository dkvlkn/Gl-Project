package core;

import data.Grille;
import java.util.Random;

/**
 * Génère un labyrinthe aléatoire dans la grille.
 */
public class GridGenerator {
    public static void genererLabyrinthe(Grille grille) {
        Random rand = new Random();
        int rows = grille.getRows();
        int cols = grille.getCols();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Ne modifie pas la case de départ (0,0) et celle d'arrivée (rows-1, cols-1)
                if (rand.nextFloat() < 0.3 && !(i == 0 && j == 0) && !(i == rows - 1 && j == cols - 1)) {
                    grille.getCase(i, j).setObstacle(true);
                }
            }
        }
    }
}
