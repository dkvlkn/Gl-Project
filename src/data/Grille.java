package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Représente la grille du jeu contenant des obstacles et des cases.
 */
public class Grille {
    private Case[][] cases;
    private int rows;
    private int cols;
    private Case personnage; // Position actuelle du personnage

    public Grille(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        cases = new Case[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cases[i][j] = new Case(i, j);
            }
        }
        // Définir la position du personnage par défaut en (0,0)
        this.personnage = cases[0][0];
    }

    public int getRows() { 
        return rows; 
    }
    public int getCols() { 
        return cols; 
    }

    public Case getCase(int row, int col) {
        if (estDansLesBornes(row, col)) {
            return cases[row][col];
        }
        return null;
    }

    public boolean estDansLesBornes(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void toggleObstacle(int row, int col) {
        if (estDansLesBornes(row, col)) {
            Case c = getCase(row, col);
            c.setObstacle(!c.isObstacle());
        }
    }

    public void genererLabyrintheAleatoire() {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Ne modifie pas la case de départ (0,0) ni celle d'arrivée (rows-1, cols-1)
                if ((i == 0 && j == 0) || (i == rows - 1 && j == cols - 1)) {
                    continue;
                }
                getCase(i, j).setObstacle(rand.nextDouble() < 0.3);
            }
        }
    }

    public void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                getCase(i, j).setObstacle(false);
            }
        }
        // Réinitialiser la position du personnage à (0,0)
        this.personnage = cases[0][0];
    }
    
    /**
     * Retourne la liste des voisins (haut, bas, gauche, droite) non-obstacles d'une case.
     */
    public List<Case> getVoisins(Case c) {
        List<Case> voisins = new ArrayList<>();
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        for (int[] dir : directions) {
            int newRow = c.getLigne() + dir[0];
            int newCol = c.getColonne() + dir[1];
            if (estDansLesBornes(newRow, newCol) && !getCase(newRow, newCol).isObstacle()) {
                voisins.add(getCase(newRow, newCol));
            }
        }
        return voisins;
    }

    /**
     * Met à jour la position actuelle du personnage dans la grille.
     */
    public void setPersonnage(Case c) {
        this.personnage = c;
    }

    /**
     * Retourne la position actuelle du personnage.
     */
    public Case getPersonnage() {
        return personnage;
    }
}
