package data.Astar;

/**
 * Représente une case dans la grille.
 */
public class Case {
    private int ligne;
    private int colonne;
    private boolean obstacle;

    public Case(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.obstacle = false;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    @Override
    public String toString() {
        return "Case{" +
                "ligne=" + ligne +
                ", colonne=" + colonne +
                ", obstacle=" + obstacle +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Case) {
            Case other = (Case) obj;
            return this.ligne == other.ligne && this.colonne == other.colonne;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * ligne + colonne;
    }
}
