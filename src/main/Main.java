package main;

import javax.swing.*;

import agents.AgentAlice;
import agents.AgentBob;
import environment.Block;
import environment.Grid;
import gui.SimulationGUI;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création de la grille
        Grid grid = new Grid(10, 10);
        Environment environment = new Environment(grid, new ArrayList<>(), new ArrayList<>());

        // Création des agents
        Agent alice = new AgentAlice("Alice", grid.getBlock(5, 5), environment);
        Agent bob = new AgentBob("Bob", grid.getBlock(2, 2), environment);
        List<Agent> agents = new ArrayList<>();
        agents.add(alice);
        agents.add(bob);

        // Création de la simulation
        Simulation simulation = new Simulation(environment, agents);

        // Création de l'interface graphique
        JFrame frame = new JFrame("Simulation");
        SimulationGUI gui = new SimulationGUI(grid, agents);
        frame.add(gui);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Exécution de la simulation
        for (int i = 0; i < 10; i++) {
            simulation.nextRound();
            gui.repaint();
            try {
                Thread.sleep(500); // Pause pour visualiser les déplacements
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}