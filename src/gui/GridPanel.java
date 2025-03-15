package gui;

import data.astar.ACell;
import data.astar.AGrid;
import data.qlearn.Direction;
import data.qlearn.QCell;
import data.qlearn.QGrid;
import data.elements.Element;
import gui.management.DefaultPaintStrategy;
import gui.management.PaintStrategy;
import process.visitor.DrawingVisitor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridPanel extends JPanel {
    private Object grid;
    private String type;
    private ArrayList<?> path; // Chemin final
    private List<ACell> exploredCells; // Cellules explorées (pour A*)
    private static final int CELL_SIZE = 60;
    private PaintStrategy paintStrategy;
    private DrawingVisitor drawingVisitor;

    public GridPanel(Object grid, String type) {
        this.grid = grid;
        this.type = type;
        this.path = new ArrayList<>();
        this.exploredCells = new ArrayList<>();
        this.paintStrategy = new DefaultPaintStrategy();
        this.drawingVisitor = new DrawingVisitor(getGraphics(), paintStrategy, CELL_SIZE);
        int size = (type.equals("A*") ? ((AGrid) grid).getSize() : ((QGrid) grid).getSize());
        setPreferredSize(new Dimension(size * CELL_SIZE, size * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawingVisitor = new DrawingVisitor(g, paintStrategy, CELL_SIZE);
        if (type.equals("A*")) {
            drawGrid(g, (AGrid) grid);
        } else if (type.equals("Q-Learning")) {
            drawGrid(g, (QGrid) grid);
        }
    }

    private void drawGrid(Graphics g, AGrid aGrid) {
        int size = aGrid.getSize();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                ACell cell = aGrid.getCell(x, y);
                drawCell(g, cell, x, y, aGrid.getStartingCell(), aGrid.getEndingCell(), (ArrayList<ACell>) path);
            }
        }
    }

    private void drawGrid(Graphics g, QGrid qGrid) {
        int size = qGrid.getSize();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                QCell cell = qGrid.getCell(x, y);
                drawCell(g, cell, x, y, qGrid.getStartingCell(), qGrid.getEndingCell(), (ArrayList<QCell>) path);
                float[] qValues = cell.getqValue();
                g.setColor(Color.BLACK);
                g.drawString(String.format("%.1f", qValues[Direction.UP.getValue()]), x * CELL_SIZE + 20, y * CELL_SIZE + 15);
                g.drawString(String.format("%.1f", qValues[Direction.DOWN.getValue()]), x * CELL_SIZE + 20, y * CELL_SIZE + 45);
                g.drawString(String.format("%.1f", qValues[Direction.LEFT.getValue()]), x * CELL_SIZE + 5, y * CELL_SIZE + 30);
                g.drawString(String.format("%.1f", qValues[Direction.RIGHT.getValue()]), x * CELL_SIZE + 35, y * CELL_SIZE + 30);
            }
        }
    }
/**
    private void drawCell(Graphics g, Object cell, int x, int y, Object start, Object end, ArrayList<?> path) {
        Element element = (cell instanceof ACell) ? ((ACell) cell).getElement() : ((QCell) cell).getElement();
        boolean canAccess = (cell instanceof ACell) ? ((ACell) cell).isCanAccess() : true;

        if (element != null) {
            if (!canAccess) {
                element = new data.elements.Wall(((ACell) cell).getCoordinate());
            }
            element.accept(drawingVisitor);
        } else {
            paintStrategy.setColor(g, null);
            paintStrategy.draw(g, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE);
        }

        if (cell == start) {
            g.setColor(Color.GREEN);
            g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        } else if (cell == end) {
            g.setColor(Color.RED);
            g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        } else if (path.contains(cell)) {
            g.setColor(Color.GREEN); // Chemin final en vert
            g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        } else if (type.equals("A*") && exploredCells.contains(cell)) {
            g.setColor(Color.RED); // Chemins explorés non corrects en rouge
            g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
**/
    //a essayer
    private void drawCell(Graphics g, Object cell, int x, int y, Object start, Object end, ArrayList<?> path) {
        Element element = (cell instanceof ACell) ? ((ACell) cell).getElement() : ((QCell) cell).getElement();
        boolean canAccess = (cell instanceof ACell) ? ((ACell) cell).isCanAccess() : true;

        if (element != null) {
            if (!canAccess) {
                element = new data.elements.Wall(((ACell) cell).getCoordinate());
            }
            element.accept(drawingVisitor);
        } else {
            paintStrategy.setColor(g, null);
            paintStrategy.draw(g, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE);
        }

        int centerX = x * CELL_SIZE + CELL_SIZE / 2;
        int centerY = y * CELL_SIZE + CELL_SIZE / 2;

        if (cell == start) {
            g.setColor(Color.GREEN);
            g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        } else if (cell == end) {
            g.setColor(Color.RED);
            g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        } else if (path.contains(cell)) {
            g.setColor(Color.GREEN);
            int index = path.indexOf(cell);
            if (index > 0) {
                ACell prev = (ACell) path.get(index - 1);
                int prevX = prev.getCoordinate().coordinateX() * CELL_SIZE + CELL_SIZE / 2;
                int prevY = prev.getCoordinate().coordinateY() * CELL_SIZE + CELL_SIZE / 2;
                g.drawLine(prevX, prevY, centerX, centerY);
            }
        } else if (type.equals("A*") && exploredCells.contains(cell)) {
            g.setColor(Color.RED);
            int index = exploredCells.indexOf(cell);
            if (index > 0) {
                ACell prev = exploredCells.get(index - 1);
                int prevX = prev.getCoordinate().coordinateX() * CELL_SIZE + CELL_SIZE / 2;
                int prevY = prev.getCoordinate().coordinateY() * CELL_SIZE + CELL_SIZE / 2;
                g.drawLine(prevX, prevY, centerX, centerY);
            }
        }
    }
    
    
    public void setPath(ArrayList<?> path) {
        this.path = path;
        repaint();
    }

    public void setExploredCells(List<ACell> exploredCells) {
        this.exploredCells = exploredCells != null ? exploredCells : new ArrayList<>();
        repaint();
    }
}