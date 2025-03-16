package data.Astar;

import javax.swing.*;
import java.awt.*;

public class Grille {
    private int nombreLignes;
    private int nombreColonnes;
    private Case[][] cases;

    public Grille(int nombreLignes, int nombreColonnes) {
        this.nombreLignes = nombreLignes;
        this.nombreColonnes = nombreColonnes;
        this.cases = new Case[nombreLignes][nombreColonnes];

        // Initialisation correcte des cases
        initialiserCases();
    }

    private void initialiserCases() {
        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                if (cases[i][j] == null) {
                    cases[i][j] = new Case(i, j);
                }
            }
        }
    }

    public Case getCase(int ligne, int colonne) {
        if (estDansLesBornes(ligne, colonne)) {
            if (cases[ligne][colonne] == null) {
                cases[ligne][colonne] = new Case(ligne, colonne); // Correction finale
            }
            return cases[ligne][colonne];
        }
        return null;
    }

    public boolean estDansLesBornes(int ligne, int colonne) {
        return ligne >= 0 && ligne < nombreLignes && colonne >= 0 && colonne < nombreColonnes;
    }

    public void afficherGrille(Graphics g) {
        int cellSize = Math.min(600 / nombreColonnes, 600 / nombreLignes);

        // Dessiner un fond blanc pour la grille
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, nombreColonnes * cellSize, nombreLignes * cellSize);

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                if (cases[i][j] == null) {
                    cases[i][j] = new Case(i, j); // Correction finale pour éviter null
                }
                g.setColor(Color.GRAY);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }
}
