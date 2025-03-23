package core;

import data.Grille;
import data.Cellule;
import data.Coordonnee;
import java.util.*;

/**
 * Implémentation simplifiée de l'algorithme A* en utilisant les nouvelles classes.
 */
public class Astar {
    private Grille grille; // Grille où se déroule la recherche du chemin
    private Cellule depart; // Cellule de départ
    private Cellule arrivee; // Cellule d'arrivée

    private HashMap<Cellule, Cellule> cameFrom; // Stocke la provenance de chaque cellule
    private HashMap<Cellule, Integer> gScore; // Stocke le coût réel pour atteindre chaque cellule
    private HashMap<Cellule, Integer> fScore; // Stocke le coût estimé total (gScore + heuristique)
    
    private ArrayList<Cellule> openList; // Liste des cellules à explorer (triée manuellement)
    private int casesExplorees = 0; // Nombre de cellules explorées
    private int coutTotal = 0; // Coût total du chemin trouvé

    /**
     * Constructeur de l'algorithme A*.
     *
     *  grille  Grille contenant les cellules.
     * depart  Cellule de départ.
     *  arrivee Cellule d'arrivée.
     */
    public Astar(Grille grille, Cellule depart, Cellule arrivee) {
        this.grille = grille;
        this.depart = depart;
        this.arrivee = arrivee;
        this.cameFrom = new HashMap<>();
        this.gScore = new HashMap<>();
        this.fScore = new HashMap<>();
        this.openList = new ArrayList<>();

        // Initialisation des coûts pour la cellule de départ
        gScore.put(depart, 0);
        fScore.put(depart, heuristic(depart.getCoordonnee(), arrivee.getCoordonnee()));
        
        // Ajout de la cellule de départ dans la liste des cellules à explorer
        openList.add(depart);
    }

    /**
     * Calcule le chemin optimal de départ à l'arrivée.
     *
     * elle retourne Liste des cellules représentant le chemin ou null si aucun chemin trouvé.
     */
    public ArrayList<Cellule> calculerChemin() {
        while (!openList.isEmpty()) { // Tant qu'il y a des cellules à explorer
            //  On prend la cellule avec le plus petit fScore (car openList est triée)
            Cellule current = openList.remove(0);
            casesExplorees++;

            //  Si on est arrivé à destination, on reconstruit le chemin
            if (current.equals(arrivee)) {
                return reconstruireChemin(current);
            }

            //  Exploration des voisins
            for (Cellule voisin : grille.getVoisins(current)) {
                int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1;

                // Si on trouve un chemin plus court pour atteindre ce voisin
                if (tentativeGScore < gScore.getOrDefault(voisin, Integer.MAX_VALUE)) {
                    cameFrom.put(voisin, current); // On mémorise d'où on vient
                    gScore.put(voisin, tentativeGScore);
                    fScore.put(voisin, tentativeGScore + heuristic(voisin.getCoordonnee(), arrivee.getCoordonnee()));

                    // On ajoute le voisin dans la liste des cellules à explorer s'il n'y est pas déjà
                    if (!openList.contains(voisin)) {
                        openList.add(voisin);
                    }
                }
            }

            //  On trie la liste pour que la meilleure cellule soit en premier
            Collections.sort(openList, Comparator.comparingInt(c -> fScore.getOrDefault(c, Integer.MAX_VALUE)));
        }
        return null; // Aucun chemin trouvé
    }

    /**
     * Retourne le nombre de cellules explorées.
     */
    public int getCasesExplorees() {
        return casesExplorees;
    }

    /**
     * Retourne le coût total du chemin trouvé.
     */
    public int getCoutTotal() {
        return coutTotal;
    }

    /**
     * Reconstruit le chemin optimal en remontant les prédécesseurs.
     *
     *  current Cellule d'arrivée.
     *  Liste des cellules formant le chemin du départ à l'arrivée.
     */
    private ArrayList<Cellule> reconstruireChemin(Cellule current) {
        ArrayList<Cellule> chemin = new ArrayList<>();
        while (cameFrom.containsKey(current)) { // Tant que la cellule a un prédécesseur
            chemin.add(current);
            current = cameFrom.get(current); // On remonte au parent
            coutTotal++;
        }
        Collections.reverse(chemin); // Le chemin est reconstruit à l'envers, on le remet dans le bon ordre
        return chemin;
    }

    /**
     * Fonction heuristique : distance de Manhattan entre deux coordonnées.
     *
     *  a Première coordonnée.
     *  b Seconde coordonnée.
     *  Distance heuristique entre les deux coordonnées.
     */
    private int heuristic(Coordonnee a, Coordonnee b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

	public Grille getGrille() {
		return grille;
	}

	public void setGrille(Grille grille) {
		this.grille = grille;
	}

	public Cellule getDepart() {
		return depart;
	}

	public void setDepart(Cellule depart) {
		this.depart = depart;
	}

	public Cellule getArrivee() {
		return arrivee;
	}

	public void setArrivee(Cellule arrivee) {
		this.arrivee = arrivee;
	}

	public HashMap<Cellule, Cellule> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(HashMap<Cellule, Cellule> cameFrom) {
		this.cameFrom = cameFrom;
	}

	public HashMap<Cellule, Integer> getgScore() {
		return gScore;
	}

	public void setgScore(HashMap<Cellule, Integer> gScore) {
		this.gScore = gScore;
	}

	public HashMap<Cellule, Integer> getfScore() {
		return fScore;
	}

	public void setfScore(HashMap<Cellule, Integer> fScore) {
		this.fScore = fScore;
	}

	public void setCasesExplorees(int casesExplorees) {
		this.casesExplorees = casesExplorees;
	}

	public void setCoutTotal(int coutTotal) {
		this.coutTotal = coutTotal;
	}
    
}
