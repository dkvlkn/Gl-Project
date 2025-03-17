package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau contenant les boutons de contrôle.
 */
public class ControlPanel extends JPanel {
    private Dashboard dashboard;

    public ControlPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 0));

        // PANEL DES BOUTONS PRINCIPAUX
        JPanel panelBoutons = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBoutons.setBorder(BorderFactory.createTitledBorder("Contrôles"));

        JButton btnStart = new JButton("Lancer A*");
        JButton btnReset = new JButton("Réinitialiser");
        JButton btnBack = new JButton("Retour");

        btnStart.addActionListener(e -> dashboard.lancerAStar());
        btnReset.addActionListener(e -> dashboard.resetGrille());
        btnBack.addActionListener(e -> System.exit(0)); // Ferme la fenêtre

        panelBoutons.add(btnStart);
        panelBoutons.add(btnReset);
        panelBoutons.add(btnBack);

        // PANEL POUR L'AJOUT DES OBSTACLES
        JPanel panelObstacles = new JPanel(new GridLayout(2, 1, 10, 10));
        panelObstacles.setBorder(BorderFactory.createTitledBorder("Mode Obstacles"));

        JButton btnAjouterObstacle = new JButton("Ajouter un obstacle");
        JButton btnAutoMaze = new JButton("Générer Labyrinthe");

        // Ajout manuel d'un obstacle via une boîte de dialogue
        btnAjouterObstacle.addActionListener(e -> dashboard.activerAjoutObstacle());

        // Génération automatique d'un labyrinthe
        btnAutoMaze.addActionListener(e -> {
            dashboard.genererLabyrinthe();
            JOptionPane.showMessageDialog(this, "Labyrinthe généré !");
        });

        panelObstacles.add(btnAjouterObstacle);
        panelObstacles.add(btnAutoMaze);

        // AJOUT DES PANELS À L'INTERFACE
        add(panelBoutons, BorderLayout.NORTH);
        add(panelObstacles, BorderLayout.SOUTH);
    }
}
