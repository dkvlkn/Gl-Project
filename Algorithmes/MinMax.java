package Algorithmes;

import Environnement.Case;
import Environnement.Grille;
import java.util.ArrayList;

public class MinMax {
    private Grille grille;
    private Case depart;
    private Case arrivee;

    public MinMax(Grille grille, Case depart, Case arrivee) {
        this.grille = grille;
        this.depart = depart;
        this.arrivee = arrivee;
    }

    private int calculerScore(Case c) {
        return Math.abs(c.getLigne() - arrivee.getLigne()) + Math.abs(c.getColonne() - arrivee.getColonne());
    }

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

            if (meilleureCase == null) break; 
            caseActuelle = meilleureCase;
        }
        return chemin;
    }
}
