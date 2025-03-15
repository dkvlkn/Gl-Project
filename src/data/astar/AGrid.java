package data.astar;

import java.util.ArrayList;

public class AGrid {
    private ACell[][] grid;
    private ACell startingCell;
    private ACell endingCell;
    private int size;

    public AGrid(int size) {
        this.size = size;
        this.grid = new ACell[size][size];
        // Initialiser chaque cellule avec une instance d'ACell
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = new ACell(x, y);
            }
        }
    }

    public ACell getCell(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            return grid[x][y];
        }
        return null;
    }

    public void setStartingCell(ACell cell) {
        this.startingCell = cell;
    }

    public void setEndingCell(ACell cell) {
        this.endingCell = cell;
    }

    public ACell getStartingCell() {
        return startingCell;
    }

    public ACell getEndingCell() {
        return endingCell;
    }

    public int getSize() {
        return size;
    }

    public ACell[][] getGrid() {
        return grid;
    }

    public void setGrid(ACell[][] grid) {
        this.grid = grid;
    }

    // Méthodes pour obtenir les voisins (exemples simples)
    public ACell getUp(ACell cell) throws CellIsWallException {
        int x = cell.getCoordinate().coordinateX();
        int y = cell.getCoordinate().coordinateY() - 1;
        ACell upCell = getCell(x, y);
        if (upCell != null && !upCell.isCanAccess()) {
            throw new CellIsWallException();
        }
        return upCell;
    }

    public ACell getDown(ACell cell) throws CellIsWallException {
        int x = cell.getCoordinate().coordinateX();
        int y = cell.getCoordinate().coordinateY() + 1;
        ACell downCell = getCell(x, y);
        if (downCell != null && !downCell.isCanAccess()) {
            throw new CellIsWallException();
        }
        return downCell;
    }

    public ACell getLeft(ACell cell) throws CellIsWallException {
        int x = cell.getCoordinate().coordinateX() - 1;
        int y = cell.getCoordinate().coordinateY();
        ACell leftCell = getCell(x, y);
        if (leftCell != null && !leftCell.isCanAccess()) {
            throw new CellIsWallException();
        }
        return leftCell;
    }

    public ACell getRight(ACell cell) throws CellIsWallException {
        int x = cell.getCoordinate().coordinateX() + 1;
        int y = cell.getCoordinate().coordinateY();
        ACell rightCell = getCell(x, y);
        if (rightCell != null && !rightCell.isCanAccess()) {
            throw new CellIsWallException();
        }
        return rightCell;
    }

    public ArrayList<ACell> getNeighbors(ACell cell) {
        ArrayList<ACell> neighbors = new ArrayList<>();
        try { if (getUp(cell) != null) neighbors.add(getUp(cell)); } catch (CellIsWallException ignored) {}
        try { if (getDown(cell) != null) neighbors.add(getDown(cell)); } catch (CellIsWallException ignored) {}
        try { if (getLeft(cell) != null) neighbors.add(getLeft(cell)); } catch (CellIsWallException ignored) {}
        try { if (getRight(cell) != null) neighbors.add(getRight(cell)); } catch (CellIsWallException ignored) {}
        return neighbors;
    }

    public void calculateHeuristicCost(ACell cell) {
        if (endingCell != null) {
            int dx = Math.abs(cell.getCoordinate().coordinateX() - endingCell.getCoordinate().coordinateX());
            int dy = Math.abs(cell.getCoordinate().coordinateY() - endingCell.getCoordinate().coordinateY());
            cell.setHeuristicCost(dx + dy); // Distance de Manhattan
        }
    }
}