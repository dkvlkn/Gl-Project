package data.astar;

import data.elements.Element;

import java.util.ArrayList;

public class ACell {
    private Coordinate coordinate;
    private boolean canAccess;
    private ACell parent;
    private double cost; // Coût depuis le départ (g)
    private double heuristicCost; // Coût heuristique (h)
    private Element element;

    public ACell(int x, int y) {
        this.coordinate = new Coordinate(x, y);
        this.canAccess = true;
        this.parent = null;
        this.cost = 0;
        this.heuristicCost = 0;
        this.element = null;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isCanAccess() {
        return canAccess;
    }

    public void setCanAccess(boolean canAccess) {
        this.canAccess = canAccess;
    }

    public void setParent(ACell parent) {
        this.parent = parent;
    }

    public ACell getParent() {
        return parent;
    }

    public void calculateCost() {
        if (parent != null) {
            cost = parent.cost + 1; // Coût uniforme (1 par pas)
        }
    }

    public double getCost() {
        return cost + heuristicCost; // f = g + h
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public ArrayList<ACell> getGenealogy() {
        ArrayList<ACell> genealogy = new ArrayList<>();
        ACell current = this;
        while (current != null) {
            genealogy.add(current);
            current = current.getParent();
        }
        return genealogy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ACell)) return false;
        ACell other = (ACell) obj;
        return this.coordinate.equals(other.coordinate);
    }
}