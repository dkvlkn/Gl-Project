package gui;

import javax.swing.*;
import process.Simulation;
import java.awt.*;

/**
 * The main graphical user interface for the simulation (home page).
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class MainGUI {
    private Simulation simulation;
    private JFrame frame; // Référence explicite

    public MainGUI() {
        simulation = new Simulation();
        frame = new JFrame("AI Learning Simulation - Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("Intelligent Features", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(25, 25, 112));
        title.setBackground(new Color(173, 216, 230));
        title.setOpaque(true);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton aStarButton = new JButton("AStar");
        JButton minMaxButton = new JButton("MinMax");
        JButton qLearningButton = new JButton("QLearning");
        JButton quitButton = new JButton("Quit");

        styleButton(aStarButton, new Color(144, 238, 144));
        styleButton(minMaxButton, new Color(255, 182, 193));
        styleButton(qLearningButton, new Color(173, 216, 230));
        styleButton(quitButton, new Color(240, 128, 128));

        aStarButton.addActionListener(e -> new AStarGUI(simulation).display());
        minMaxButton.addActionListener(e -> new MinMaxGUI(simulation).display());
        qLearningButton.addActionListener(e -> new QLearningGUI(simulation).display());
        quitButton.addActionListener(e -> {
            System.out.println("Quit button clicked");
            frame.dispose(); // Ferme la fenêtre principale
            System.exit(0); // Termine l'application
        });

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(aStarButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(minMaxButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(qLearningButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(quitButton);
        buttonPanel.add(Box.createVerticalGlue());

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}