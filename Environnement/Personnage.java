package Environnement;

import data.Astar.Case;
import data.Astar.Grille;

/**
 * Classe représentant un personnage dans un environnement.
 * Le personnage a un nom, une position et évolue dans une grille.
 */
public class Personnage {
    private String name; 
    private Position position; 
    private Case environnement; 
    private Grille grille;

    /**
     * Constructeur de la classe Personnage.
     * @param name Nom du personnage.
     * @param environnement Case initiale du personnage.
     * @param grille Grille dans laquelle évolue le personnage.
     */
    public Personnage(String name, Case environnement, Grille grille) {
        this.name = name;
        this.environnement = environnement;
        this.grille = grille;

        this.position = new Position(environnement.getLigne(), environnement.getColonne());
    }

    /**
     * Déplace le personnage d'une case vers la droite dans la grille.
     * Si le personnage atteint le bord droit de la grille, il revient à la première colonne.
     */
    public void act() {
        // Vérifie si le personnage peut se déplacer vers la droite
        if (position.getX() < grille.getNombreColonnes() - 1) {
            position.setX(position.getX() + 1);  
        } else {
            // Si le bord est atteint, il revient à la première colonne
            position.setX(0); 
        }

        // Met à jour la case actuelle du personnage après le déplacement
        environnement = grille.getCase(position.getX(), position.getY());
    }

    /**
     * Retourne la position actuelle du personnage.
     * @return La position du personnage sous forme d'un objet Position.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Retourne la case où se trouve actuellement le personnage.
     * @return La case actuelle du personnage.
     */
    public Case getEnvironnement() {
        return environnement;
    }

    /**
     * Retourne une représentation textuelle du personnage.
     * @return Une chaîne contenant les informations du personnage.
     */
    @Override
    public String toString() {
        return "Personnage{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", environnement=" + environnement +
                '}';
    }
}
