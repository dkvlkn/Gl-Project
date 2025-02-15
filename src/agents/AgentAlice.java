package agents;

import environment.Block;
import environment.Environment;

public class AgentAlice extends Agent {
    public AgentAlice(String name, Block startPosition, Environment environment) {
        super(name, startPosition, environment);
    }

    @Override
    public void updateState() {
        // Implémentation simple : déplacement vers le haut
        Block newPosition = environment.getGrid().getBlock(getCurrentPosition().getRow() - 1, getCurrentPosition().getColumn());
        if (newPosition != null && newPosition.isWalkable()) {
            setCurrentPosition(newPosition);
        }
    }
}