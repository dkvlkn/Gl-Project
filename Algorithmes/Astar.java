package Algorithmes;

import data.Astar.Case;
import data.Astar.Grille;
import java.util.*;

/**
 * Classe implémentant l'algorithme A* pour le calcul d'un chemin.
 */
public class Astar {
    private Grille grille;
    private Case depart;
    private Case arrivee;
    private HashMap<Case, Case> cameFrom;
    private HashMap<Case, Integer> gScore;
    private HashMap<Case, Integer> fScore;
    private PriorityQueue<Case> openSet;

    /**
     * Constructeur de l'algorithme A*.
     *
     * @param grille  La grille sur laquelle se fait la recherche.
     * @param depart  La case de départ.
     * @param arrivee La case d'arrivée.
     */
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
     * Calcule le chemin de départ à l'arrivée en utilisant A*.
     *
     * @return Une liste de cases représentant le chemin, ou null si aucun chemin n'est trouvé.
     */
    public ArrayList<Case> calculerChemin() {
        while (!openSet.isEmpty()) {
            Case current = openSet.poll();

            if (current.equals(arrivee)) {
                return reconstruireChemin(current);
            }

            for (Case voisin : grille.getVoisins(current)) {
                if (voisin == null || grille.estObstacle(voisin.getLigne(), voisin.getColonne())) {
                    continue;
                }

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
     * Reconstruit le chemin en remontant les prédécesseurs.
     *
     * @param current La case d'arrivée.
     * @return Une liste de cases du départ à l'arrivée.
     */
    private ArrayList<Case> reconstruireChemin(Case current) {
        ArrayList<Case> chemin = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            chemin.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(chemin);
        return chemin;
    }

    /**
     * Calcule l'heuristique (distance de Manhattan).
     *
     * @param a Case de départ.
     * @param b Case d'arrivée.
     * @return La distance de Manhattan entre les deux cases.
     */
    private int heuristic(Case a, Case b) {
        return Math.abs(a.getLigne() - b.getLigne()) + Math.abs(a.getColonne() - b.getColonne());
    }
}
