package data;


/**
 * This class represents an autonomous agent (Alice or Bob) in the simulation.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class Agent {
    private int id;
    private int xPosition;
    private int yPosition;

    public Agent(int id, int xPosition, int yPosition) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public int getId() {
        return id;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }
}