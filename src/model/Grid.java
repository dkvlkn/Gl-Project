package model;

/**
 * Représente une grille d'environnement composée de cellules.
 */
public class Grid {
    private int width;
    private int height;
    private Cell[][] cells;

    /**
     * Construit une grille de dimensions spécifiées.
     * Toutes les cases sont initialisées comme accessibles.
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        // Initialisation de toutes les cellules en tant que walkable
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y, true);
            }
        }
    }

    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la cellule aux coordonnées (x, y).
     */
    public Cell getCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return null;
        return cells[x][y];
    }

    /**
     * Définit la cellule comme obstacle (si obstacle est true, la case devient non accessible).
     */
    public void setObstacle(int x, int y, boolean obstacle) {
        Cell cell = getCell(x, y);
        if (cell != null) {
            cell.walkable = !obstacle;
        }
    } 
}
