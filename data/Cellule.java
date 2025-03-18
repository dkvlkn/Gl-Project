package data;

/**
 * Représente une cellule dans la grille.
 */
public class Cellule {
    private Coordonnee coordonnee; // Position de la cellule (x, y)
    private boolean obstacle; // Indique si la cellule est un obstacle

    public Cellule(int x, int y, boolean obstacle) {
        this.coordonnee = new Coordonnee(x, y);
        this.obstacle = obstacle;
    }

    public Coordonnee getCoordonnee() {
        return coordonnee;
    }
    

    public boolean estObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    @Override
    public String toString() {
        return "Cellule{" +
                "coordonnee=" + coordonnee +
                ", obstacle=" + obstacle +
                '}';
    }
}

