package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale pour la simulation A*.
 * Elle intègre le panneau de la grille (Dashboard) et le panneau de contrôle (ControlPanel).
 */
public class MainGUI extends JFrame {
    private Dashboard dashboard;
    private ControlPanel controlPanel;

    public MainGUI() {
        setTitle("A* Pathfinding Simulation");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Création des panneaux
        dashboard = new Dashboard(10, 10); // Grille 10x10
        controlPanel = new ControlPanel(dashboard);

        // Ajout des panneaux dans la fenêtre
        add(dashboard, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        setLocationRelativeTo(null); // Centre la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
