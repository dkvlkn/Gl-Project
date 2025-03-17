package process;

import data.Agent;

/**
 * This class manages an individual agent in the simulation.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class AgentManager {
    private Agent agent;
    private boolean running; // Pour gérer l'état (ex. pour Stop dans GUI)

    // Constructeur prenant un Agent
    public AgentManager(Agent agent) {
        this.agent = agent;
        this.running = false; // Par défaut, non actif
    }

    public Agent getAgent() {
        return agent;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}