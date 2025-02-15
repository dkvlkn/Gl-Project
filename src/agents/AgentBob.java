package agents;

import environment.Block;

public class AgentBob extends Agent {
    public AgentBob(String name, Block startPosition, Environment environment) {
        super(name, startPosition, environment);
    }

    @Override
    public void updateState() {
        // Implémentation simple : déplacement vers le bas
        Block newPosition = environment.getGrid().getBlock(getCurrentPosition().getRow() + 1, getCurrentPosition().getColumn());
        if (newPosition != null && newPosition.isWalkable()) {
            setCurrentPosition(newPosition);
        }
    }
}