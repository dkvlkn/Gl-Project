package gui.management;

import data.elements.BestPath;
import data.elements.Element;
import data.elements.Hole;
import data.elements.Tile;
import data.elements.Trail;
import data.elements.UselessTile;
import data.elements.Wall;
import java.awt.Color;
import java.awt.Graphics;

public class DefaultPaintStrategy implements PaintStrategy {
    @Override
    public void setColor(Graphics g, Element element) {
        if (element instanceof Wall) {
            g.setColor(Color.BLACK);
        } else if (element instanceof Tile) {
            g.setColor(Color.WHITE);
        } else if (element instanceof Hole) {
            g.setColor(Color.DARK_GRAY);
        } else if (element instanceof Trail) {
            g.setColor(Color.YELLOW);
        } else if (element instanceof UselessTile) {
            g.setColor(Color.LIGHT_GRAY);
        } else if (element instanceof BestPath) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.WHITE); // Par défaut
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        g.fillRect(x, y, cellSize, cellSize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, cellSize, cellSize);
    }

}