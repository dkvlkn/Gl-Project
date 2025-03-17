package gui;

import javax.swing.*;
import process.AStar;
import process.AgentManager;
import process.Simulation;
import process.SimulationUtility;
import data.Agent;
import java.awt.*;
import java.util.List;

/**
 * The graphical user interface for the A* algorithm simulation.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class AStarGUI {
    private Simulation simulation;
    private AStar aStar;
    private JPanel gridPanel;
    private JTextArea logArea;
    private volatile boolean running = false;
    private JTextField sizeField;
    private JFrame frame;

    public AStarGUI(Simulation simulation) {
        this.simulation = simulation;
        this.aStar = new AStar(simulation.getGrid());
    }

    public void display() {
        frame = new JFrame("AStar Simulation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        gridPanel = new JPanel(new GridLayout(simulation.getGrid().getHeight(), simulation.getGrid().getWidth()));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        drawGrid();

        logArea = new JTextArea(5, 20);
        logArea.setEditable(false);
        logArea.setBackground(new Color(245, 245, 220));
        logArea.setBorder(BorderFactory.createTitledBorder("Algorithm Progress"));
        JScrollPane logScroll = new JScrollPane(logArea);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(220, 220, 220));
        sidebar.setPreferredSize(new Dimension(200, 600));

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton adjustButton = new JButton("Ajuster");
        JButton backButton = new JButton("Back");

        sizeField = new JTextField("" + simulation.getGrid().getWidth(), 5);

        styleButton(startButton, new Color(144, 238, 144));
        styleButton(stopButton, new Color(240, 128, 128));
        styleButton(adjustButton, new Color(173, 216, 230));
        styleButton(backButton, new Color(255, 215, 0));

        startButton.addActionListener(e -> {
            if (!running) { // Évite de lancer plusieurs threads
                running = true;
                int goalX = simulation.getGrid().getWidth() - 1;
                int goalY = simulation.getGrid().getHeight() - 1;
                for (AgentManager manager : simulation.getAgentManagers()) {
                    Thread agentThread = new Thread(() -> {
                        try {
                            Agent agent = manager.getAgent();
                            List<int[]> path = aStar.findPath(agent, goalX, goalY);
                            if (path != null && running) {
                                logArea.append("A" + agent.getId() + " path calculated: " + path.size() + " steps\n");
                                SwingUtilities.invokeLater(() -> colorPath(path));
                                for (int[] pos : path) {
                                    if (!running) break;
                                    agent.setPosition(pos[0], pos[1]);
                                    logArea.append("A" + agent.getId() + " moved to (" + pos[0] + ", " + pos[1] + ")\n");
                                    SimulationUtility.unitTime();
                                    SwingUtilities.invokeLater(this::drawGrid);
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace(); // Capture les erreurs
                        }
                    });
                    agentThread.setDaemon(true); // Thread daemon
                    agentThread.start();
                }
            }
        });

        stopButton.addActionListener(e -> {
            System.out.println("Stop button clicked");
            running = false; // Arrête les threads
        });

        adjustButton.addActionListener(e -> {
            try {
                int newSize = Integer.parseInt(sizeField.getText());
                if (newSize > 0) {
                    running = false; // Arrête les threads avant redimensionnement
                    simulation.getGrid().resize(newSize);
                    simulation.getAgentManagers().get(0).getAgent().setPosition(0, 0);
                    simulation.getAgentManagers().get(1).getAgent().setPosition(newSize - 1, newSize - 1);
                    aStar = new AStar(simulation.getGrid());
                    gridPanel.setLayout(new GridLayout(newSize, newSize));
                    drawGrid();
                    logArea.append("Grid adjusted to " + newSize + "x" + newSize + "\n");
                } else {
                    JOptionPane.showMessageDialog(frame, "Grid size must be positive.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid grid size.");
            }
        });

        backButton.addActionListener(e -> {
            System.out.println("Back button clicked");
            running = false; // Arrête les threads
            frame.dispose(); // Ferme la fenêtre
        });

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(startButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(stopButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(new JLabel("Size:"));
        sidebar.add(sizeField);
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
                cell.setBackground(Color.WHITE);
                gridPanel.add(cell);
            }
        }
        for (AgentManager manager : simulation.getAgentManagers()) {
            Agent agent = manager.getAgent();
            int index = agent.getYPosition() * simulation.getGrid().getWidth() + agent.getXPosition();
            JButton agentCell = (JButton) gridPanel.getComponent(index);
            agentCell.setText("A" + agent.getId());
            agentCell.setBackground(new Color(135, 206, 235));
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void colorPath(List<int[]> path) {
        for (int[] pos : path) {
            int index = pos[1] * simulation.getGrid().getWidth() + pos[0];
            JButton cell = (JButton) gridPanel.getComponent(index);
            if (cell.getBackground().equals(Color.WHITE)) {
                cell.setBackground(new Color(144, 238, 144));
            }
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