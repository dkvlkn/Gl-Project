package controller;

import model.Environment;
import model.Agent;
import model.Cell;

/**
 * Contrôleur de simulation qui orchestre la mise à jour de l'environnement et
 * les actions des agents.
 */
public class SimulationController implements Runnable {
    private Environment environment;
    private Agent agentAlice;
    // Pour cet exemple, nous gérons un seul agent.
    private boolean running;
    private static final int SIMULATION_DELAY = 1000; // délai en millisecondes
 
    /**
     * Initialise l'environnement et l'agent.
     */
    public SimulationController() {
        // Création d'un environnement avec une grille de 10x10.
        environment = new Environment(10, 10);
        // Création de l'agent avec son environnement.
        agentAlice = new Agent("Alice", environment);
        running = false;
    }

    /**
     * Démarre la simulation dans un thread séparé.
     */
    public void startSimulation() {
        running = true;
        Thread simulationThread = new Thread(this);
        simulationThread.start();
    }

    /**
     * Arrête la simulation.
     */
    public void stopSimulation() {
        running = false;
    }

    /**
     * Boucle principale de simulation.
     * À chaque cycle, l'environnement est mis à jour et l'agent agit.
     */
    @Override
    public void run() {
        // Définir un objectif pour l'agent (par exemple, la cellule en bas à droite de la grille)
        Cell target = environment.getGrid().getCell(environment.getWidth() - 1, environment.getHeight() - 1);
        agentAlice.setTarget(target);
        
        while (running) {
            // Mise à jour de l'environnement
            environment.update();
            
            // L'agent réalise son action (calcul et suivi du chemin)
            agentAlice.act();
            
            // Pause entre chaque cycle de simulation
            try {
                Thread.sleep(SIMULATION_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
