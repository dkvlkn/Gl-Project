package process;

import data.Agent;
import data.Grid;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the simulation.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class Simulation {
    private Grid grid;
    private List<AgentManager> agentManagers;

    public Simulation() {
        this.grid = new Grid(10); // Taille initiale de 10x10
        this.agentManagers = new ArrayList<>();
        Agent alice = new Agent(1, 0, 0); // Alice à (0,0)
        Agent bob = new Agent(2, grid.getWidth() - 1, grid.getHeight() - 1); // Bob au coin opposé
        agentManagers.add(new AgentManager(alice));
        agentManagers.add(new AgentManager(bob));
    }

    public Grid getGrid() {
        return grid;
    }

    public List<AgentManager> getAgentManagers() {
        return agentManagers;
    }
}