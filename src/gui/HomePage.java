package gui;

import data.astar.AGrid;
import process.astar.GridFactory;
import process.minmax.MinMaxCore;
import process.qlearn.QLearnCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private AGrid aGrid;
    private QLearnCore qLearnCore;
    private MinMaxCore minMaxCore;

    public HomePage() {
        // Initialisation des instances des algorithmes
        this.aGrid = (AGrid) new GridFactory().BuildGrid(5);
        this.qLearnCore = new QLearnCore(5, 200, 0.2f, 0.5f, 0.8f);
        this.minMaxCore = new MinMaxCore(10, 3);

        // Configuration de la fenêtre
        setTitle("AI Algorithms Visualization - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Choose an Algorithm", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Boutons pour chaque algorithme
        JButton aStarButton = new JButton("A* Pathfinding");
        aStarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchMainFrame("A*");
            }
        });

        JButton qLearningButton = new JButton("Q-Learning");
        qLearningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchMainFrame("Q-Learning");
            }
        });

        JButton minMaxButton = new JButton("MinMax Game");
        minMaxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchMainFrame("MinMax");
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Ajouter les boutons au panneau
        buttonPanel.add(aStarButton);
        buttonPanel.add(qLearningButton);
        buttonPanel.add(minMaxButton);
        buttonPanel.add(exitButton);

        // Ajouter le panneau à la fenêtre
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void launchMainFrame(String selectedAlgorithm) {
        // Fermer la page d'accueil
        dispose();

        // Lancer MainFrame avec tous les algorithmes, mais on pourrait filtrer si besoin
        MainFrame mainFrame = new MainFrame(aGrid, qLearnCore, minMaxCore);

        // Sélectionner l'onglet correspondant à l'algorithme choisi
        JTabbedPane tabbedPane = mainFrame.getTabbedPane();
        switch (selectedAlgorithm) {
            case "A*":
                tabbedPane.setSelectedIndex(0);
                break;
            case "Q-Learning":
                tabbedPane.setSelectedIndex(1);
                break;
            case "MinMax":
                tabbedPane.setSelectedIndex(2);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}