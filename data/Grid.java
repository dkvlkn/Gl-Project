package data;

/**
 * This class represents a 2D grid where agents evolve.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class Grid {
    private int width;
    private int height;
    private String[][] cells;

    public Grid(int size) { // Constructeur modifié pour une grille carrée
        this.width = size;
        this.height = size;
        this.cells = new String[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                cells[x][y] = ""; // "" au lieu de "empty"
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getCell(int x, int y) {
        return cells[x][y];
    }

    public void setCell(int x, int y, String value) {
        cells[x][y] = value;
    }

    public void resize(int newSize) { // Redimensionnement pour une grille carrée
        if (newSize > 0) {
            this.width = newSize;
            this.height = newSize;
            String[][] newCells = new String[newSize][newSize];
            for (int x = 0; x < newSize; x++) {
                for (int y = 0; y < newSize; y++) {
                    if (x < cells.length && y < cells[0].length) {
                        newCells[x][y] = cells[x][y];
                    } else {
                        newCells[x][y] = ""; // "" au lieu de "empty"
                    }
                }
            }
            this.cells = newCells;
        }
    }
}