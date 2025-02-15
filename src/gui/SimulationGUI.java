package gui;

import javax.swing.*;

import agents.Agent;
import environment.Grid;

import java.awt.*;
import java.util.List;

public class SimulationGUI extends JPanel {
    private Grid grid;
    private List<Agent> agents;

    public SimulationGUI(Grid grid, List<Agent> agents) {
        this.grid = grid;
        this.agents = agents;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessiner la grille
        for (int i = 0; i < grid.getRowCount(); i++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(j * 40, i * 40, 40, 40);
                g.setColor(Color.BLACK);
                g.drawRect(j * 40, i * 40, 40, 40);
            }
        }
        // Dessiner les agents
        for (Agent agent : agents) {
            Block position = agent.getCurrentPosition();
            g.setColor(agent instanceof AgentAlice ? Color.BLUE : Color.RED);
            g.fillOval(position.getColumn() * 40, position.getRow() * 40, 40, 40);
        }
    }
}