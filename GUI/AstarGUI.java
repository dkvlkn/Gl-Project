package GUI;

import data.Astar.Grille;

import javax.swing.*;
import java.awt.*;

public class AstarGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField gridSizeField;
    private JTextArea logArea;
    private JPanel gridPanel;
    private Grille grille;
    private int gridSize = 10;

    public AstarGUI() {
        setTitle("AStar - Recherche de Chemin");
        setSize(1000, 750);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        grille = new Grille(gridSize, gridSize);

        // 🔹 Bande latérale
        JPanel sidebar = creerSidebar();
        add(sidebar, BorderLayout.EAST);

        // 🔹 Grille centrale
        gridPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (grille != null) {
                    grille.afficherGrille(g);
                }
            }
        };
        gridPanel.setPreferredSize(new Dimension(600, 600));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(gridPanel, BorderLayout.CENTER);

        // 🔹 Bande inférieure (Logs)
        JPanel logPanel = creerLogPanel();
        add(logPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Crée la bande latérale avec les boutons et les paramètres.
     */
    private JPanel creerSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(4, 1, 10, 10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sidebar.setBackground(Color.LIGHT_GRAY);

        JButton restartButton = new JButton("RESTART");
        JButton backButton = new JButton("BACK");
        JButton applyButton = new JButton("Appliquer");
        backButton.addActionListener(e -> dispose());

        sidebar.add(restartButton);
        sidebar.add(backButton);

        // 🔹 Paramètres
        JPanel paramPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        paramPanel.setBorder(BorderFactory.createTitledBorder("Paramètres"));
        paramPanel.add(new JLabel("Taille de la Grille:"));
        gridSizeField = new JTextField("10");
        paramPanel.add(gridSizeField);
        sidebar.add(paramPanel);
        sidebar.add(applyButton);

        // 🔹 Actions des boutons
        applyButton.addActionListener(e -> updateGridSize());
        restartButton.addActionListener(e -> resetGrid());

        return sidebar;
    }

    /**
     * Crée la bande inférieure pour afficher les logs.
     */
    private JPanel creerLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Déroulement"));
        logArea = new JTextArea(6, 50);
        logArea.setEditable(false);
        logPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        return logPanel;
    }

    /**
     * Met à jour la taille de la grille.
     */
    private void updateGridSize() {
        try {
            gridSize = Integer.parseInt(gridSizeField.getText());
            grille = new Grille(gridSize, gridSize);
            log("Grille redimensionnée à " + gridSize + " x " + gridSize);
            refreshGrid();
        } catch (NumberFormatException e) {
            log("Erreur : Taille de grille invalide");
        }
    }

    /**
     * Réinitialise la grille.
     */
    private void resetGrid() {
        grille = new Grille(gridSize, gridSize);
        log("Grille réinitialisée.");
        refreshGrid();
    }

    /**
     * Rafraîchit l'affichage de la grille.
     */
    private void refreshGrid() {
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    /**
     * Ajoute un message dans la bande inférieure (logs).
     */
    private void log(String message) {
        logArea.append(message + "\n");
    }
}
