package data.elements;

import java.awt.Color;
import data.astar.Coordinate; // Aligné avec ACell et GridFactory
import process.visitor.ElementVisitor;

public class Wall implements Element {
    private Coordinate coordinate;

    public Wall(Coordinate c) {
        if (c == null) {
            throw new IllegalArgumentException("Coordinate cannot be null in Wall constructor");
        }
        this.coordinate = c;
    }

    @Override
    public <E> E accept(ElementVisitor<E> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return "Wall";
    }
}