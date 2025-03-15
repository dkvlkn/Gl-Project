package data.elements;

import java.awt.Color;
import data.astar.Coordinate; // Choisis data.astar ou data.utils selon ton projet
import process.visitor.ElementVisitor;

public interface Element {
    <E> E accept(ElementVisitor<E> visitor);
    Color getColor();
    Coordinate getCoordinate();
}