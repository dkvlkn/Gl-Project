package Environnement;

import java.util.ArrayList;
import java.util.List;
import Environnement.Case;

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

    public void toggleObstacle(int ligne, int colonne) {
        Case c = getCase(ligne, colonne);
        if (c != null) {
            c.setObstacle(!c.isObstacle());
        }
    }

    public boolean estObstacle(int ligne, int colonne) {
        Case c = getCase(ligne, colonne);
        return c != null && c.isObstacle();
    }
    
    public void ajouterObstacle(int ligne, int colonne) {
        if (ligne >= 0 && ligne < nombreLignes && colonne >= 0 && colonne < nombreColonnes) {
            cases[ligne][colonne].setObstacle(true); // Marque la case comme un obstacle
        }
    }

	public int getNombreLignes() {
		return nombreLignes;
	}

	public void setNombreLignes(int nombreLignes) {
		this.nombreLignes = nombreLignes;
	}

	public int getNombreColonnes() {
		return nombreColonnes;
	}

	public void setNombreColonnes(int nombreColonnes) {
		this.nombreColonnes = nombreColonnes;
	}
}
