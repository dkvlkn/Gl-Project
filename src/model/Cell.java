package model;

/**
 * Représente une case de la grille.
 */
public class Cell {
    public int x;
    public int y;
    public boolean walkable; // true si la case est accessible, false si c'est un obstacle

    public Cell(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cell)) return false;
        Cell other = (Cell) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "( ligne = " + x + ", colonne = " + y + ") \n";
    }
} 
