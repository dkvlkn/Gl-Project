package data.astar;

public class CellIsWallException extends Exception {
    public CellIsWallException() {
        super("Cell is a wall");
    }
}