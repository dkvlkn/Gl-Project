// package view;
package view;

import javax.swing.JFrame;
import model.Grid;
import model.Cell;

public class TestAStarGUI {
    public static void main(String[] args) {
        // Création d'une grille 10x10
        Grid grid = new Grid(15, 15);
        // Définition de quelques obstacles
        grid.setObstacle(3, 3, true);
        grid.setObstacle(3, 4, true);
        grid.setObstacle(3, 5, true);
        grid.setObstacle(6, 6, true);
        grid.setObstacle(0, 1, true);
        grid.setObstacle(1, 1, true);
        grid.setObstacle(2, 1, true);
        grid.setObstacle(2, 2, true);
        grid.setObstacle(2, 3, true);
        grid.setObstacle(2, 4, true);
        // Définition de la cellule de départ et d'arrivée
        Cell start = grid.getCell(0, 0);
        Cell goal = grid.getCell(14, 14);

        // Création du panneau AStarPanel avec la grille, le départ et l'arrivée
        AStarPanel panel = new AStarPanel(grid, start, goal);

        // Création et configuration de la fenêtre
        JFrame frame = new JFrame("Visualisation de A*");
        frame.add(panel);
        // Ajustement de la taille de la fenêtre en fonction de la grille et de la taille des cellules
        frame.setSize(grid.getWidth() * AStarPanel.CELL_SIZE + 16, grid.getHeight() * AStarPanel.CELL_SIZE + 39);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
