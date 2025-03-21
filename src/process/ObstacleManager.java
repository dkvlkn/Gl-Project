package process;

import data.Grille;
import java.util.Random;

public class ObstacleManager {

    public static void placerObstaclesAleatoires(Grille grille, int nombreObstacles) {
        Random random = new Random();
        int taille = grille.getTaille();
        int placés = 0;

        while (placés < nombreObstacles) {
            int x = random.nextInt(taille);
            int y = random.nextInt(taille);
            if (!grille.estObstacle(x, y) && !(x == 0 && y == 0) && !(x == taille - 1 && y == taille - 1)) {
                grille.setObstacle(x, y, true);
                placés++;
            }
        }
    }

    public static void placerObstaclesManuels(Grille grille, String coords) {
        String[] coordPairs = coords.split(";");
        for (String pair : coordPairs) {
            String[] xy = pair.split(",");
            int x = Integer.parseInt(xy[0].trim());
            int y = Integer.parseInt(xy[1].trim());
            if (!(x == 0 && y == 0) && !(x == grille.getTaille()-1 && y == grille.getTaille()-1)) {
                grille.setObstacle(x, y, true);
            }
        }
    }
}
