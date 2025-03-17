package core;

import data.Case;
import data.Grille;
import java.util.*;

/**
 * Implémentation de l'algorithme A*.
 */
public class Astar {
    private Grille grille;
    private Case depart;
    private Case arrivee;
    private Map<Case, Case> cameFrom;
    private Map<Case, Integer> gScore;
    private Map<Case, Integer> fScore;
    private PriorityQueue<Case> openSet;

    public Astar(Grille grille, Case depart, Case arrivee) {
        this.grille = grille;
        this.depart = depart;
        this.arrivee = arrivee;
        this.cameFrom = new HashMap<>();
        this.gScore = new HashMap<>();
        this.fScore = new HashMap<>();
        this.openSet = new PriorityQueue<>(Comparator.comparingInt(c -> fScore.getOrDefault(c, Integer.MAX_VALUE)));

        gScore.put(depart, 0);
        fScore.put(depart, heuristic(depart, arrivee));
        openSet.add(depart);
    }

    /**
     * Calcule le chemin optimal de départ à l'arrivée.
     *
     * @return une liste de cases représentant le chemin ou null si aucun chemin n'est trouvé.
     */
    public List<Case> calculerChemin() {
        while (!openSet.isEmpty()) {
            Case current = openSet.poll();

            if (current.equals(arrivee)) {
                return reconstruireChemin(current);
            }

            for (Case voisin : grille.getVoisins(current)) {
                int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1;
                if (tentativeGScore < gScore.getOrDefault(voisin, Integer.MAX_VALUE)) {
                    cameFrom.put(voisin, current);
                    gScore.put(voisin, tentativeGScore);
                    fScore.put(voisin, tentativeGScore + heuristic(voisin, arrivee));

                    if (!openSet.contains(voisin)) {
                        openSet.add(voisin);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Reconstruit le chemin optimal en remontant les prédécesseurs.
     */
    private List<Case> reconstruireChemin(Case current) {
        List<Case> chemin = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            chemin.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(chemin);
        return chemin;
    }

    /**
     * Calcule l'heuristique (distance de Manhattan) entre deux cases.
     */
    private int heuristic(Case a, Case b) {
        return Math.abs(a.getLigne() - b.getLigne()) + Math.abs(a.getColonne() - b.getColonne());
    }
}
