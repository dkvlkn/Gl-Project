package agents;

import environment.Block;
import environment.Environment;

public abstract class Agent {
    private String name;
    private Block currentPosition;
    private Environment environment;

    public Agent(String name, Block startPosition, Environment environment) {
        this.name = name;
        this.currentPosition = startPosition;
        this.environment = environment;
    }

    public Block getCurrentPosition() { return currentPosition; }
    public void setCurrentPosition(Block position) { this.currentPosition = position; }

    public Environment getEnvironment() { return environment; } // Ajout de la méthode

    public abstract void updateState();
}