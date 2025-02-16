package Environnement;

public class Personnage {
    private String name;
    private Position position;
    private Case environnement;  
    private Grille grille;  

    public Personnage(String name, Case environnement, Grille grille) {
        this.name = name;
        this.environnement = environnement;
        this.grille = grille;
        // Initialisation de la position à partir des coordonnées de la case dans la grille
        this.position = new Position(environnement.getLigne(), environnement.getColonne());
    }

    public void act() {
        // Déplacement du personnage sur la grille
        if (position.getX() < grille.getNombreColonnes() - 1) {
            position.setX(position.getX() + 1);  // Déplacement vers la droite
        } else {
            position.setX(0);  // Si on est à la dernière colonne, on revient à la première
        }

        // Mise à jour de l'environnement à partir de la nouvelle position
        environnement = grille.getCase(position.getX(), position.getY());
    }

    public Position getPosition() {
        return position;
    }

    public Case getEnvironnement() {
        return environnement;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", environnement=" + environnement +
                '}';
    }
}
