package gui.management;

import data.elements.Element;
import java.awt.Graphics;

public interface PaintStrategy {
    void setColor(Graphics g, Element element);
    void draw(Graphics g, int x, int y, int cellSize);
}