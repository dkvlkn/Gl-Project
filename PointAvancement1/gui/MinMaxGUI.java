package gui;

import javax.swing.*;
import process.MinMax;
import process.AgentManager;
import process.Simulation;
import process.SimulationUtility;
import data.Agent;
import java.awt.*;

/**
 * The graphical user interface for the MinMax algorithm simulation.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class MinMaxGUI {
    private Simulation simulation;
    private MinMax minMax;
    private JPanel gridPanel;
    private JTextArea logArea;
    private volatile boolean running = false;

    public MinMaxGUI(Simulation simulation) {
        this.simulation = simulation;
        this.minMax = new MinMax(simulation.getGrid());
    }

    public void display() {
        JFrame frame = new JFrame("MinMax Simulation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        gridPanel = new JPanel(new GridLayout(SimuPara.GRID_HEIGHT, SimuPara.GRID_WIDTH));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        simulation.getGrid().setCell(5, 5, "resource");
        drawGrid();

        logArea = new JTextArea(5, 20);
        logArea.setEditable(false);
        logArea.setBackground(new Color(245, 245, 220)); // Beige clair
        logArea.setBorder(BorderFactory.createTitledBorder("Algorithm Progress"));
        JScrollPane logScroll = new JScrollPane(logArea);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(220, 220, 220)); // Gris clair
        sidebar.setPreferredSize(new Dimension(200, 600));

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton adjustButton = new JButton("Ajuster");
        JButton backButton = new JButton("Back");

        styleButton(startButton, new Color(144, 238, 144)); // Vert clair
        styleButton(stopButton, new Color(240, 128, 128)); // Rouge clair
        styleButton(adjustButton, new Color(173, 216, 230)); // Bleu clair
        styleButton(backButton, new Color(255, 215, 0)); // Jaune clair

        startButton.addActionListener(e -> {
            running = true;
            for (AgentManager manager : simulation.getAgentManagers()) {
                new Thread(() -> {
                    Agent agent = manager.getAgent();
                    Agent opponent = simulation.getAgentManagers().get(agent.getId() == 1 ? 1 : 0).getAgent();
                    while (running) {
                        int[] move = minMax.decideMove(agent, opponent, 5, 5);
                        agent.setPosition(move[0], move[1]);
                        logArea.append("A" + agent.getId() + " moved to (" + move[0] + ", " + move[1] + ")\n");
                        SimulationUtility.unitTime();
                        SwingUtilities.invokeLater(this::drawGrid);
                    }
                }).start();
            }
        });

        stopButton.addActionListener(e -> running = false);

        adjustButton.addActionListener(e -> {
            String width = JOptionPane.showInputDialog(frame, "Enter new grid width:");
            String height = JOptionPane.showInputDialog(frame, "Enter new grid height:");
            try {
                int newWidth = Integer.parseInt(width);
                int newHeight = Integer.parseInt(height);
                simulation = new Simulation();
                simulation.getGrid().setCell(5, 5, "resource");
                gridPanel.setLayout(new GridLayout(newHeight, newWidth));
                drawGrid();
                logArea.append("Grid adjusted to " + newWidth + "x" + newHeight + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid grid size.");
            }
        });

        backButton.addActionListener(e -> frame.dispose());

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(startButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(stopButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(adjustButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(backButton);
        sidebar.add(Box.createVerticalGlue());

        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(logScroll, BorderLayout.SOUTH);
        mainPanel.add(sidebar, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void drawGrid() {
        gridPanel.removeAll();
        for (int y = 0; y < simulation.getGrid().getHeight(); y++) {
            for (int x = 0; x < simulation.getGrid().getWidth(); x++) {
                JButton cell = new JButton(simulation.getGrid().getCell(x, y));
                cell.setPreferredSize(new Dimension(40, 40));
                cell.setBackground("resource".equals(simulation.getGrid().getCell(x, y)) ? Color.YELLOW : Color.WHITE);
                gridPanel.add(cell);
            }
        }
        for (AgentManager manager : simulation.getAgentManagers()) {
            Agent agent = manager.getAgent();
            int index = agent.getYPosition() * simulation.getGrid().getWidth() + agent.getXPosition();
            JButton agentCell = (JButton) gridPanel.getComponent(index);
            agentCell.setText("A" + agent.getId());
            agentCell.setBackground(new Color(135, 206, 235)); // Bleu ciel pour agents
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void styleButton(JButton button, Color color) {
        button.setMaximumSize(new Dimension(120, 30));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    }
}