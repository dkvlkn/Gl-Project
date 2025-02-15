package environment;

public class Block {
    private int row;
    private int column;
    private boolean walkable;

    public Block(int row, int column, boolean walkable) {
        this.row = row;
        this.column = column;
        this.walkable = walkable;
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }
    public boolean isWalkable() { return walkable; }

    @Override
    public String toString() {
        return "Block [row=" + row + ", column=" + column + ", walkable=" + walkable + "]";
    }
}