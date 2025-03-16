package GUI;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainGUI() {
        setTitle("Apprentissage - Système Intelligent");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        JLabel titleLabel = new JLabel("Intelligent Learning System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));
        buttonPanel.setBackground(Color.WHITE);

        JButton astarButton = new JButton("AStar");
        JButton minmaxButton = new JButton("MinMax");
        JButton qlearningButton = new JButton("QLearning");
        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.WHITE);

        buttonPanel.add(astarButton);
        buttonPanel.add(minmaxButton);
        buttonPanel.add(qlearningButton);
        buttonPanel.add(quitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        astarButton.addActionListener(e -> SwingUtilities.invokeLater(AstarGUI::new));
        quitButton.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
