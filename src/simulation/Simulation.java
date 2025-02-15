package simulation;

import java.util.List;

import agents.Agent;
import environment.Environment;

public class Simulation {
    private Environment environment;
    private List<Agent> agents;
    private int currentRound;

    public Simulation(Environment environment, List<Agent> agents) {
        this.environment = environment;
        this.agents = agents;
        this.currentRound = 0;
    }

    public void initialize() {
        // Initialisation de la simulation
    }

    public void nextRound() {
        currentRound++;
        for (Agent agent : agents) {
            agent.updateState();
        }
    }
}