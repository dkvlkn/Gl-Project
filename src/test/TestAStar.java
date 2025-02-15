package test;

import java.util.List;
import algorithms.AStar;
import model.Cell;
import model.Grid;

public class TestAStar {
    public static void main(String[] args) {
        // Création d'une grille 20x20
        Grid grid = new Grid(20, 20);
        // Optionnel : définir quelques obstacles
        grid.setObstacle(3, 3, true);
        grid.setObstacle(3, 4, true);
        grid.setObstacle(3, 5, true);
        grid.setObstacle(6, 6, true);
        grid.setObstacle(0, 1, true);
        grid.setObstacle(1, 1, true);
        grid.setObstacle(2, 1, true);
        grid.setObstacle(2, 2, true);
        grid.setObstacle(2, 3, true);
        grid.setObstacle(2, 4, true);

        // Définir les cellules de départ et d'arrivée
        Cell start = grid.getCell(0, 0);
        Cell goal = grid.getCell(19, 19);

        // Calcul du chemin avec A*
        AStar aStar = new AStar();
        List<Cell> path = aStar.findPath(grid, start, goal);

        // Affichage du chemin trouvé
        if (path.isEmpty()) {
            System.out.println("Aucun chemin trouvé.");
        } else {
            System.out.println("Chemin trouvé:");
            for (Cell cell : path) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
