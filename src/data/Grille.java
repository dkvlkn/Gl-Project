package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une grille 2D où les entités évoluent.
 */
public class Grille {
    private int taille;
    private Cellule[][] cellules;

    public Grille(int taille) {
        this.taille = taille;
        this.cellules = new Cellule[taille][taille];

        // Initialisation des cellules
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                cellules[x][y] = new Cellule(x, y, false); // Par défaut, pas d'obstacle
            }
        }
    }

    public int getTaille() {
        return taille;
    }

    public Cellule getCellule(int x, int y) {
        if (x >= 0 && x < taille && y >= 0 && y < taille) {
            return cellules[x][y];
        }
        return null;
    }

    public void setObstacle(int x, int y, boolean obstacle) {
        if (x >= 0 && x < taille && y >= 0 && y < taille) {
            cellules[x][y].setObstacle(obstacle);
        }
    }

    public boolean estObstacle(int x, int y) {
        return cellules[x][y].estObstacle();
    }

    /**
     * 🔹 Retourne la liste des voisins accessibles (haut, bas, gauche, droite).
     *
     * @param cellule La cellule dont on veut connaître les voisins.
     * @return Liste des cellules voisines accessibles.
     */
    public ArrayList<Cellule> getVoisins(Cellule cellule) {
        ArrayList<Cellule> voisins = new ArrayList<>();
        Coordonnee coord = cellule.getCoordonnee();
        int x = coord.getX();
        int y = coord.getY();

        // Définition des directions possibles (haut, bas, gauche, droite)
        int[][] directions = {
            {-1, 0}, // Haut
            {1, 0},  // Bas
            {0, -1}, // Gauche
            {0, 1}   // Droite
        };

        // Parcours des directions et ajout des voisins valides
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < taille && newY >= 0 && newY < taille) { // Vérifie les limites
                Cellule voisin = cellules[newX][newY];
                if (!voisin.estObstacle()) { // Vérifie si ce n'est pas un obstacle
                    voisins.add(voisin);
                }
            }
        }

        return voisins;
    }
}
