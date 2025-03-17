package data;

/**
 * Classe représentant un personnage qui peut se déplacer avec A*.
 */
public class Personnage {
    private String name;
    private Case position;
    private Grille grille;

    public Personnage(String name, Case position, Grille grille) {
        this.name = name;
        this.position = position;
        this.grille = grille;
    }

    public void setPosition(Case nouvellePosition) {
        this.position = nouvellePosition;
    }

    public Case getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "name='" + name + '\'' +
                ", position=(" + position.getLigne() + "," + position.getColonne() + ")" +
                '}';
    }
}
