package environment;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Block[][] blocks;
    private int rowCount;
    private int columnCount;

    public Grid(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.blocks = new Block[rowCount][columnCount];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                blocks[i][j] = new Block(i, j, true); // Tous les blocs sont praticables par défaut
            }
        }
    }

    public Block getBlock(int row, int column) {
        return blocks[row][column];
    }

    public List<Block> getNeighbors(Block block) {
        List<Block> neighbors = new ArrayList<>();
        int row = block.getRow();
        int column = block.getColumn();

        if (row > 0) neighbors.add(blocks[row - 1][column]); // Haut
        if (row < rowCount - 1) neighbors.add(blocks[row + 1][column]); // Bas
        if (column > 0) neighbors.add(blocks[row][column - 1]); // Gauche
        if (column < columnCount - 1) neighbors.add(blocks[row][column + 1]); // Droite

        return neighbors;
    }
}