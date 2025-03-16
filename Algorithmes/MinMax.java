package Algorithmes;

import data.Astar.Case;
import data.Astar.Grille;

import java.util.ArrayList;

/**
 * Classe implémentant une méthode simple de type MinMax pour le choix d'un chemin.
 */
public class MinMax {
    private Grille grille;
    private Case depart;
    private Case arrivee;

    /**
     * Constructeur de MinMax.
     *
     * @param grille  La grille utilisée.
     * @param depart  La case de départ.
     * @param arrivee La case d'arrivée.
     */
    public MinMax(Grille grille, Case depart, Case arrivee) {
        this.grille = grille;
        this.depart = depart;
        this.arrivee = arrivee;
    }

    /**
     * Calcule un "chemin" en choisissant à chaque étape le voisin dont le score (distance de Manhattan) est le plus faible.
     *
     * @return Le chemin sous forme d'une ArrayList de cases.
     */
    public ArrayList<Case> calculerChemin() {
        ArrayList<Case> chemin = new ArrayList<>();
        Case caseActuelle = depart;

        while (!caseActuelle.equals(arrivee)) {
            chemin.add(caseActuelle);
            Case meilleureCase = null;
            int meilleurScore = Integer.MAX_VALUE;

            for (Case voisin : grille.getVoisins(caseActuelle)) {
                if (!grille.estObstacle(voisin.getLigne(), voisin.getColonne())) {
                    int score = calculerScore(voisin);
                    if (score < meilleurScore) {
                        meilleureCase = voisin;
                        meilleurScore = score;
                    }
                }
            }

            if (meilleureCase == null) {
                break;
            }
            caseActuelle = meilleureCase;
        }
        return chemin;
    }

    /**
     * Calcule le score d'une case basé sur la distance de Manhattan par rapport à l'arrivée.
     *
     * @param c La case à évaluer.
     * @return Le score de la case.
     */
    private int calculerScore(Case c) {
        return Math.abs(c.getLigne() - arrivee.getLigne()) + Math.abs(c.getColonne() - arrivee.getColonne());
    }
}
