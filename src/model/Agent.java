package model;

import java.util.List;
import algorithms.AStar;

/**
 * Représente un agent évoluant dans l'environnement.
 * L'agent calcule un chemin vers un objectif et le suit.
 */
public class Agent { 
    private String name;
    private Position position;
    private Environment environment;
    private List<Cell> path;  // Le chemin calculé par A*
    private Cell target;      // La cellule objectif

    /**
     * Construit un agent avec un nom et l'environnement dans lequel il évolue.
     * La position initiale est fixée à (0,0) par défaut.
     */
    public Agent(String name, Environment environment) {
        this.name = name;
        this.environment = environment;
        this.position = new Position(0, 0);
    }

    /**
     * Calcule le chemin depuis la position actuelle de l'agent jusqu'à la cellule cible.
     * Utilise l'algorithme A*.
     */
    public void computePath(Cell target) {
        this.target = target;
        // La cellule de départ correspond à la position actuelle de l'agent
        Cell startCell = environment.getGrid().getCell(position.x, position.y);
        AStar aStar = new AStar();
        this.path = aStar.findPath(environment.getGrid(), startCell, target);
    }

    /**
     * Fait avancer l'agent d'une cellule le long du chemin calculé.
     */
    public void moveAlongPath() {
        if (path != null && !path.isEmpty()) {
            // On retire la première cellule de la liste (qui correspond à la position actuelle)
            // et on se déplace vers la cellule suivante.
            // Si la première cellule est exactement la position actuelle, on la retire.
            if (path.get(0).x == position.x && path.get(0).y == position.y) {
                path.remove(0);
            }
            // Puis, si le chemin n'est pas vide, l'agent se déplace vers la prochaine cellule.
            if (!path.isEmpty()) {
                Cell next = path.remove(0);
                position.x = next.x;
                position.y = next.y;
            }
        }
    }

    /**
     * Méthode appelée à chaque cycle de simulation.
     * Si un objectif est défini, l'agent calcule le chemin s'il n'existe pas
     * et se déplace ensuite le long du chemin.
     */
    public void act() {
        if (target != null) {
            // Si l'agent est déjà à la cible, on ne fait rien.
            if (position.x == target.x && position.y == target.y) {
                System.out.println(name + " est arrivé à la cible : " + target);
            } else {
                // Si aucun chemin n'a été calculé ou s'il est vide, on le recalcule.
                if (path == null || path.isEmpty()) {
                    computePath(target);
                }
                // L'agent se déplace le long du chemin.
                moveAlongPath();
                System.out.println(name + " se déplace vers " + target + ". Position actuelle : " + position);
            }
        } else {
            // Aucun objectif défini ; comportement par défaut (à étendre).
            System.out.println(name + " n'a pas d'objectif défini.");
        }
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setTarget(Cell target) {
        this.target = target;
    }
}
