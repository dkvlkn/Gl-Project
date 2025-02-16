package Environnement;

/**
 * Classe représentant une position en 2D avec des coordonnées x et y.
 */
public class Position {
    public int x; 
    public int y; 

    /**
     * Constructeur de la classe Position.
     * @param x Coordonnée X initiale.
     * @param y Coordonnée Y initiale.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne une représentation textuelle de la position.
     * @return Une chaîne de caractères représentant les coordonnées sous la forme (x, y).
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Retourne la coordonnée X.
     * @return La valeur de x.
     */
    public int getX() {
        return x;
    }

    /**
     * Modifie la coordonnée X.
     * @param x Nouvelle valeur pour x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retourne la coordonnée Y.
     * @return La valeur de y.
     */
    public int getY() {
        return y;
    }

    /**
     * Modifie la coordonnée Y.
     * @param y Nouvelle valeur pour y.
     */
    public void setY(int y) {
        this.y = y;
    }
}
