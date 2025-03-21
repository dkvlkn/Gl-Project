package data;

/**
 * Classe représentant un personnage qui peut se déplacer avec A*.
 */
public class Personnage {
    private String name;
    private Cellule position;
    private Grille grille;

    public Personnage(String name, Cellule position, Grille grille) {
        this.name = name;
        this.position = position;
        this.grille = grille;
    }

    public void setPosition(Cellule nouvellePosition) {
        this.position = nouvellePosition;
    }

    public Cellule getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "name='" + name + '\'' +
                ", position=(" + position.getCoordonnee().getX() + "," + position.getCoordonnee().getY() + ")" +
                '}';
    }
}
