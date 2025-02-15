package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import model.Cell;
import model.Grid;

/**
 * Implémente l'algorithme A* pour la recherche de chemin dans une grille.
 */
public class AStar {

    /**
     * Classe interne Node utilisée par l'algorithme pour stocker les informations de chaque cellule.
     */
    private class Node implements Comparable<Node> {
        Cell cell;         // Cellule courante
        Node parent;       // Parent du noeud (pour reconstituer le chemin)
        double g;          // Coût du chemin depuis le départ jusqu'à ce noeud
        double h;          // Estimation (heuristique) du coût jusqu'à l'objectif

        Node(Cell cell, Node parent, double g, double h) {
            this.cell = cell;
            this.parent = parent;
            this.g = g;
            this.h = h;
        }

        double getF() {
            return g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.getF(), other.getF());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Node)) return false;
            Node other = (Node) obj;
            return this.cell.equals(other.cell);
        }

        @Override
        public int hashCode() {
            return cell.hashCode();
        }
    }

    /**
     * Recherche le chemin optimal de la cellule de départ vers la cellule d'arrivée.
     * @param grid La grille représentant l'environnement.
     * @param start La cellule de départ.
     * @param goal La cellule d'arrivée.
     * @return Une liste de cellules représentant le chemin, ou une liste vide si aucun chemin n'est trouvé.
     */
    public List<Cell> findPath(Grid grid, Cell start, Cell goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        HashSet<Cell> closedSet = new HashSet<>();

        // Initialisation du noeud de départ
        Node startNode = new Node(start, null, 0, heuristic(start, goal));
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Si la cellule objectif est atteinte, on reconstitue le chemin
            if (current.cell.equals(goal)) {
                return reconstructPath(current);
            }

            closedSet.add(current.cell);

            // Exploration des voisins accessibles
            for (Cell neighbor : getNeighbors(grid, current.cell)) {
                if (!neighbor.walkable || closedSet.contains(neighbor)) {
                    continue;
                }

                double tentativeG = current.g + distance(current.cell, neighbor);

                // Si un noeud avec une meilleure valeur g existe déjà, on passe au suivant
                boolean skip = false;
                for (Node node : openSet) {
                    if (node.cell.equals(neighbor) && tentativeG >= node.g) {
                        skip = true;
                        break;
                    }
                }
                if (skip) continue;

                // Ajout du voisin dans l'open set
                Node neighborNode = new Node(neighbor, current, tentativeG, heuristic(neighbor, goal));
                openSet.add(neighborNode);
            }
        }
        // Aucun chemin trouvé
        return new ArrayList<>();
    }

    /**
     * Reconstitue le chemin en remontant les parents depuis le noeud objectif jusqu'au départ.
     */
    private List<Cell> reconstructPath(Node node) {
        List<Cell> path = new ArrayList<>();
        while (node != null) {
            path.add(node.cell);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Fonction heuristique : utilise la distance Manhattan.
     */
    private double heuristic(Cell a, Cell b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    /**
     * Distance entre deux cellules adjacentes (pour une grille, la distance vaut 1).
     */
    private double distance(Cell a, Cell b) {
        return 1;
    }

    /**
     * Retourne les voisins accessibles (haut, bas, gauche, droite) d'une cellule donnée.
     */
    private List<Cell> getNeighbors(Grid grid, Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.x;
        int y = cell.y;

        // Haut
        if (y - 1 >= 0) {
            neighbors.add(grid.getCell(x, y - 1));
        }
        // Bas
        if (y + 1 < grid.getHeight()) {
            neighbors.add(grid.getCell(x, y + 1));
        }
        // Gauche
        if (x - 1 >= 0) {
            neighbors.add(grid.getCell(x - 1, y));
        }
        // Droite
        if (x + 1 < grid.getWidth()) {
            neighbors.add(grid.getCell(x + 1, y));
        }
        return neighbors;
    } 
}
