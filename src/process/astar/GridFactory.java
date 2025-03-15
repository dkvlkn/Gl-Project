package process.astar;

import data.astar.ACell;
import data.astar.AGrid;
import data.elements.Wall;
import data.astar.Coordinate;

import java.util.Random;

public class GridFactory {
    private Random random = new Random();

    public AGrid BuildGrid() {
        return BuildGrid(5); // Taille par défaut
    }

    public AGrid BuildGrid(int size) {
        AGrid grid = new AGrid(size); // Les cellules sont maintenant initialisées
        // Initialiser toutes les cellules comme accessibles
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid.getCell(x, y).setCanAccess(true);
                grid.getCell(x, y).setElement(null);
            }
        }

        // Générer un labyrinthe simple avec murs aléatoires
        generateMaze(grid, size);

        // Définir le départ (0,0) et l'arrivée (size-1, size-1)
        grid.setStartingCell(grid.getCell(0, 0));
        grid.setEndingCell(grid.getCell(size - 1, size - 1));

        // S'assurer qu'il y a un chemin
        ensurePath(grid, size);

        return grid;
    }

    private void generateMaze(AGrid grid, int size) {
        if (grid == null || size <= 0) {
            throw new IllegalArgumentException("Grid cannot be null and size must be positive in generateMaze");
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (!(x == 0 && y == 0) && !(x == size - 1 && y == size - 1)) {
                    if (random.nextFloat() < 0.3f) {
                        ACell cell = grid.getCell(x, y);
                        cell.setElement(new Wall(cell.getCoordinate()));
                        cell.setCanAccess(false);
                    }
                }
            }
        }
    }
    
    private void ensurePath(AGrid grid, int size) {
        for (int x = 0; x < size - 1; x++) {
            grid.getCell(x, 0).setElement(null);
            grid.getCell(x, 0).setCanAccess(true);
        }
        for (int y = 0; y < size; y++) {
            grid.getCell(size - 1, y).setElement(null);
            grid.getCell(size - 1, y).setCanAccess(true);
        }
    }
}