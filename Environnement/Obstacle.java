package Environnement;

/**
 * Classe représentant un obstacle dans l'environnement.
 * Un obstacle est défini par une position sur une case donnée.
 */
public class Obstacle {
    private Case position;

    /**
     * Constructeur de la classe Obstacle.
     * @param position La position initiale de l'obstacle.
     */
    public Obstacle(Case position) {
        this.position = position;
    }

    /**
     * Retourne la position actuelle de l'obstacle.
     * @return La case où se trouve l'obstacle.
     */
    public Case getPosition() {
        return position;
    }

    /**
     * Modifie la position de l'obstacle.
     * @param position La nouvelle position de l'obstacle.
     */
	public void setPosition(Case position) {
		this.position = position;
	}
}
