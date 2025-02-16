package Environnement;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente la grille de jeu.
 */
public class Grille {
    private int nombreLignes;
    private int nombreColonnes;
    private Case[][] cases;

    public Grille(int nombreLignes, int nombreColonnes) {
        this.nombreLignes = nombreLignes;
        this.nombreColonnes = nombreColonnes;
        this.cases = new Case[nombreLignes][nombreColonnes];

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                cases[i][j] = new Case(i, j);
            }
        }
    }

    /**
     * Renvoie la liste des voisins d'une case (haut, bas, gauche, droite) qui ne sont pas des obstacles.
     *
     * @param c La case dont on veut les voisins.
     * @return La liste des cases voisines.
     */
    public List<Case> getVoisins(Case c) {
        List<Case> voisins = new ArrayList<>();
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

        for (int[] dir : directions) {
            int newLigne = c.getLigne() + dir[0];
            int newColonne = c.getColonne() + dir[1];

            if (estDansLesBornes(newLigne, newColonne) && !estObstacle(newLigne, newColonne)) {
                voisins.add(getCase(newLigne, newColonne));
            }
        }
        return voisins;
    }

    public boolean estDansLesBornes(int ligne, int colonne) {
        return ligne >= 0 && ligne < nombreLignes && colonne >= 0 && colonne < nombreColonnes;
    }

    public Case getCase(int ligne, int colonne) {
        if (estDansLesBornes(ligne, colonne)) {
            return cases[ligne][colonne];
        }
        return null;
    }

    /**
     * Bascule l'état obstacle d'une case.
     *
     * @param ligne   La ligne de la case.
     * @param colonne La colonne de la case.
     */
    public void toggleObstacle(int ligne, int colonne) {
        Case c = getCase(ligne, colonne);
        if (c != null) {
            c.setObstacle(!c.isObstacle());
        }
    }

    /**
     * Marque explicitement une case comme obstacle.
     *
     * @param ligne   La ligne de la case.
     * @param colonne La colonne de la case.
     */
    public void ajouterObstacle(int ligne, int colonne) {
        if (estDansLesBornes(ligne, colonne)) {
            getCase(ligne, colonne).setObstacle(true);
        }
    }

    public boolean estObstacle(int ligne, int colonne) {
        Case c = getCase(ligne, colonne);
        return c != null && c.isObstacle();
    }

    public int getNombreLignes() {
        return nombreLignes;
    }

    public int getNombreColonnes() {
        return nombreColonnes;
    }
}
