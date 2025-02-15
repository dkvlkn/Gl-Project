package model;

/**
 * Représente l'environnement de simulation contenant la grille.
 */
public class Environment {
    private int width;
    private int height;
    private Grid grid;

    /**
     * Construit l'environnement avec une grille de dimensions spécifiées.
     */
    public Environment(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Grid(width, height);
    }
     
    /**
     * Retourne la grille associée à cet environnement.
     */
    public Grid getGrid() {
        return grid;
    }
    
    /**
     * Méthode de mise à jour de l'environnement.
     * (Exemple : modification aléatoire d'obstacles, à étendre selon les besoins.)
     */
    public void update() {
        // Vous pouvez ajouter ici une logique de mise à jour dynamique.
        System.out.println("L'environnement est mis à jour.");
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
