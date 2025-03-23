package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The main graphical user interface for the "Apprentissage" project.
 */
public class Main {

    private JFrame frame;

    /**
     * Constructeur de la fenêtre principale.
     */
    public Main() {
        // Message pour vérifier qu'on passe bien par ce constructeur
        System.out.println("Constructeur Main démarré");

        frame = new JFrame("Apprentissage IA - Page d'accueil");
        // Ferme totalement l'appli si c'est la seule fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Tentative de chargement de l'icône (favicon)
        try {
            java.net.URL faviconUrl = getClass().getResource("/images/favicon.png");
            System.out.println("URL de la favicon : " + faviconUrl);
            if (faviconUrl != null) {
                ImageIcon favicon = new ImageIcon(faviconUrl);
                if (favicon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    frame.setIconImage(favicon.getImage());
                    System.out.println("Favicon Main chargée avec succès");
                } else {
                    System.out.println("Échec du chargement de la favicon : image corrompue");
                }
            } else {
                System.out.println("Favicon introuvable : vérifiez /resources/logo1.png");
            }
        } catch (Exception e) {
            System.out.println("Erreur favicon Main : " + e.getMessage());
            e.printStackTrace();
        }

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));


        // Titre
        JLabel title = new JLabel("Fonctionnalités intelligentes", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(25, 25, 112));
        title.setBackground(new Color(173, 216, 230));
        title.setOpaque(true);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel de boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Boutons
        JButton aStarButton = new JButton("AStar");
        JButton minMaxButton = new JButton("MinMax");
        JButton qLearningButton = new JButton("QLearning");
        JButton backButton = new JButton("Quitter");

        // Style
        styleButton(aStarButton, new Color(144, 238, 144));
        styleButton(minMaxButton, new Color(255, 182, 193));
        styleButton(qLearningButton, new Color(173, 216, 230));
        styleButton(backButton, new Color(240, 128, 128));

        // Actions
        aStarButton.addActionListener(e -> {
            System.out.println("Bouton AStar cliqué");
            // On crée l'interface AStarGUI et on l'affiche explicitement
            AStarGUI aStarGUI = new AStarGUI();
            aStarGUI.setVisible(true);

        });

        minMaxButton.addActionListener(e -> {
            System.out.println("Bouton MinMax cliqué");
            JOptionPane.showMessageDialog(frame, "MinMax: fonctionnalité à venir.");
        });

        qLearningButton.addActionListener(e -> {
            System.out.println("Bouton QLearning cliqué");
            JOptionPane.showMessageDialog(frame, "QLearning: fonctionnalité à venir.");
        });

        backButton.addActionListener(e -> {
            System.out.println("Quitter cliqué");
            frame.dispose(); // ferme simplement la fenêtre principale
        });

        // Agencement vertical des boutons
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(aStarButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(minMaxButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(qLearningButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createVerticalGlue());

        // Ajout du titre et du panel de boutons à mainPanel
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);

    }

    /**
     * Affiche la fenêtre (après l'avoir construite dans le constructeur).
     */
    public void display() {
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
        SwingUtilities.invokeLater(() -> {
            // On instancie la fenêtre principale et on l'affiche
            Main mainWindow = new Main();
            mainWindow.display();
        });
    }
}
