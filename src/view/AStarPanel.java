package view;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import algorithms.AStar;
import model.Grid;
import model.Cell;

public class AStarPanel extends JPanel {

    // Taille d'une cellule en pixels.
    public static final int CELL_SIZE = 40;

    private Grid grid;
    private List<Cell> path;
    private Cell start;
    private Cell goal;

    /**
     * Constructeur du panneau de visualisation.
     * @param grid La grille à afficher.
     * @param start La cellule de départ.
     * @param goal La cellule d'arrivée.
     */
    public AStarPanel(Grid grid, Cell start, Cell goal) {
        this.grid = grid;
        this.start = start;
        this.goal = goal;
        computePath(); // Calcule le chemin dès la création
    }

    /**
     * Calcule le chemin optimal en utilisant l'algorithme A*.
     */
    private void computePath() {
        AStar aStar = new AStar();
        path = aStar.findPath(grid, start, goal);
    }

    /**
     * Méthode de dessin du panneau.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Parcours de la grille pour dessiner chaque cellule
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                Cell cell = grid.getCell(x, y);
                int px = x * CELL_SIZE;
                int py = y * CELL_SIZE;
                if (!cell.walkable) {
                    // Case non accessible (obstacle)
                    g.setColor(Color.BLACK);
                    g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                } else {
                    // Case libre
                    g.setColor(Color.WHITE);
                    g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.GRAY);
                    g.drawRect(px, py, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        // Dessin du chemin calculé (en bleu)
        if (path != null && !path.isEmpty()) {
            g.setColor(Color.BLUE);
            for (Cell cell : path) {
                int px = cell.x * CELL_SIZE;
                int py = cell.y * CELL_SIZE;
                g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
            }
        }
        // Dessin de la cellule de départ (en rouge)
        if (start != null) {
            g.setColor(Color.RED);
            g.fillRect(start.x * CELL_SIZE, start.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        // Dessin de la cellule d'arrivée (en vert)
        if (goal != null) {
            g.setColor(Color.GREEN);
            g.fillRect(goal.x * CELL_SIZE, goal.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}
