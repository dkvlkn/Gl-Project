package gui;

import core.Astar; 
import data.Grille;
import data.Cellule;


import process.ObstacleManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Interface graphique pour l'algorithme A*
 */
public class AStarGUI extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private Grille grille;
    private Astar astar;
    private JPanel gridPanel;
    private JTextField sizeField; 
    private JTextField speedField; 
    private JLabel exploredTilesLabel;
    private JLabel pathLengthLabel; 
    private JTextArea logArea;
    private float currentSpeed = 1.0f;
    private ArrayList<Cellule> cheminActuel;
    private int pathIndex = 0;
    private boolean stop = true;
    private Thread animationThread;
    

    private JRadioButton randomObstaclesButton;
    private JRadioButton manualObstaclesButton;
    private JTextField obstacleCoordsField;

    /**
     * Constructeur de la fenêtre AStarGUI.
     */
    public AStarGUI() {
        super("A* - Apprentissage IA");
        initAStar();
        initUI();
    }



    private void initAStar() {
        grille = new Grille(10);

        ObstacleManager.placerObstaclesAleatoires(grille, 10);

        Cellule depart = grille.getCellule(0, 0);
        Cellule arrivee = grille.getCellule(9, 9);
        astar = new Astar(grille, depart, arrivee);
    }

    /**
     * Construit l'interface graphique
     */
    private void initUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Panneau principal 
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Titre en haut
        JLabel title = new JLabel("A* - Trouver le chemin", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(25, 25, 112));
        title.setBackground(new Color(173, 216, 230));
        title.setOpaque(true);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        //Panneau gauche 
        JPanel parametersPanel = new JPanel();
        parametersPanel.setLayout(new BoxLayout(parametersPanel, BoxLayout.Y_AXIS));
        parametersPanel.setBackground(new Color(240, 248, 255));
        parametersPanel.setBorder(BorderFactory.createTitledBorder("PARAMETRES"));

        // Taille
        JPanel sizeLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        sizeLine.setBackground(parametersPanel.getBackground());
        JLabel sizeLabel = new JLabel("Taille :");
        sizeField = new JTextField(Integer.toString(grille.getTaille()), 5);
        JButton applySizeButton = new JButton("Appliquer");
        applySizeButton.addActionListener(e -> applyNewSize());
        sizeLine.add(sizeLabel);
        sizeLine.add(sizeField);
        sizeLine.add(applySizeButton);

        // Vitesse
        JPanel speedLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        speedLine.setBackground(parametersPanel.getBackground());
        JLabel speedLab = new JLabel("Vitesse :");
        speedField = new JTextField("1.0", 5);
        JButton applySpeedButton = new JButton("Appliquer");
        applySpeedButton.addActionListener(e -> applyNewSpeed());
        speedLine.add(speedLab);
        speedLine.add(speedField);
        speedLine.add(applySpeedButton);

        // Ligne "Explored Tiles"
        JPanel exploredLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        exploredLine.setBackground(parametersPanel.getBackground());
        JLabel exploredLab = new JLabel("Cases Explorées :");
        exploredTilesLabel = new JLabel("0");
        exploredLine.add(exploredLab);
        exploredLine.add(exploredTilesLabel);

        // Ligne "Path Length"
        JPanel pathLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pathLine.setBackground(parametersPanel.getBackground());
        JLabel pathLab = new JLabel("Longueur du chemin :");
        pathLengthLabel = new JLabel("0");
        pathLine.add(pathLab);
        pathLine.add(pathLengthLabel);
        

        // Ajout des lignes au panel de paramètres
        parametersPanel.add(Box.createVerticalStrut(10));
        parametersPanel.add(sizeLine);
        parametersPanel.add(speedLine);
        parametersPanel.add(Box.createVerticalStrut(20));
        parametersPanel.add(exploredLine);
        parametersPanel.add(pathLine);
        parametersPanel.add(Box.createVerticalGlue());

        mainPanel.add(parametersPanel, BorderLayout.WEST);
        
     // Choix du mode d'obstacles
        randomObstaclesButton = new JRadioButton("Aléatoires", true);
        manualObstaclesButton = new JRadioButton("Manuels");

        ButtonGroup obstacleGroup = new ButtonGroup();
        obstacleGroup.add(randomObstaclesButton);
        obstacleGroup.add(manualObstaclesButton);

        JButton applyObstaclesButton = new JButton("Appliquer Obstacles");
        applyObstaclesButton.addActionListener(e -> applyObstacles());

        parametersPanel.add(randomObstaclesButton);
        parametersPanel.add(manualObstaclesButton);
        parametersPanel.add(applyObstaclesButton);

        //Zone centrale 
        gridPanel = new JPanel();
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        updateGridDisplay(null);
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Panneau droit 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton startButton = new JButton("LANCER");
        styleButton(startButton, new Color(144, 238, 144));
        startButton.addActionListener(new StartAction());

        JButton restartButton = new JButton("RELANCER");
        styleButton(restartButton, new Color(255, 182, 193));
        restartButton.addActionListener(new RestartAction());

        JButton stopButton = new JButton("STOP");
        styleButton(stopButton, new Color(255, 215, 0));
        stopButton.addActionListener(e -> {
            log("STOP cliqué");
            stopThreadIfRunning();
        });

        JButton backButton = new JButton("RETOUR");
        styleButton(backButton, new Color(240, 128, 128));
        backButton.addActionListener(e -> {
            log("RETOUR cliqué, fermeture de la fenêtre");
            stopThreadIfRunning();
            dispose();
        });

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(restartButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(stopButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createVerticalGlue());

        mainPanel.add(buttonPanel, BorderLayout.EAST);

        // Bande inférieure
        logArea = new JTextArea(5, 50);
        logArea.setEditable(false);
        logArea.setBackground(new Color(245, 245, 245));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("DEROULEMENT"));
        mainPanel.add(scrollPane, BorderLayout.SOUTH);


        add(mainPanel);
        mainPanel.add(parametersPanel, BorderLayout.WEST);

    }

    /**
     * Affiche la fenêtre.
     */
    public void display() {
        setVisible(true);
    }

    /**
     * Affiche le chemin A* case par case tant que 'stop' = false.
     */
    @Override
    public void run() {
        log("Animation commencé (speed=" + currentSpeed + " case/s).");

        while (!stop && cheminActuel != null && pathIndex < cheminActuel.size()) {
            try {

                long delay = (long)(1000 / currentSpeed);
                if (delay < 1) delay = 1;
                Thread.sleep(delay);
            } catch (InterruptedException e) {

            }

            if (!stop) {

                pathIndex++;

                SwingUtilities.invokeLater(() -> {
                    ArrayList<Cellule> partial = new ArrayList<>(cheminActuel.subList(0, pathIndex));
                    updateGridDisplay(partial);

                    Cellule newlyRevealed = cheminActuel.get(pathIndex - 1);
                    int x = newlyRevealed.getCoordonnee().getX();
                    int y = newlyRevealed.getCoordonnee().getY();
                    log("Case " + pathIndex + " révélée : (" + x + "," + y + ")");
                    
                    exploredTilesLabel.setText(String.valueOf(astar.getCasesExplorees()));
                    pathLengthLabel.setText(String.valueOf(pathIndex));
                });
            }
        }

        if (!stop && cheminActuel != null && pathIndex >= cheminActuel.size()) {
            SwingUtilities.invokeLater(() -> {
                int nbObstacles = countObstacles(grille);

                String msg = "Chemin entièrement affiché.\n"
                           + "Cases explorées : " + astar.getCasesExplorees() + "\n"
                           + "Longueur du chemin : " + cheminActuel.size() + "\n"
                           + "Nombre d'obstacles : " + nbObstacles;
                
                JOptionPane.showMessageDialog(AStarGUI.this, msg);
                log(msg);
            });
        }

        log("Animation finie.");
    }

    /**
     * Lance le calcul de chemin via A*, puis démarre l'animation si un chemin existe.
     */
    private void startAnimation() {
        log("Calcul du chemin via A*...");
        cheminActuel = astar.calculerChemin();

        if (cheminActuel == null) {
            JOptionPane.showMessageDialog(this, "Aucun chemin trouvé !");
            log("Aucun chemin trouvé !");
            return;
        }

        log("Chemin trouvé. Cases explorées = " + astar.getCasesExplorees()
            + ", Longueur totale = " + cheminActuel.size());

        stopThreadIfRunning();

        stop = false;
        pathIndex = 0;
        animationThread = new Thread(this);
        animationThread.start();
    }

    /**
     * Arrête l'animation si elle est en cours.
     */
    private void stopThreadIfRunning() {
        if (!stop) {
            stop = true;
            if (animationThread != null && animationThread.isAlive()) {
                animationThread.interrupt();
            }
            log("Animation stoppée.");
        }
    }

    /**
     * Listener du bouton START.
     */
    private class StartAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            log("LANCER cliqué");
            startAnimation();
        }
    }

    /**
     * Réinitialise la grille tout en conservant la même taille actuelle.
     */
    private class RestartAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            log("RELANCER cliqué");
            stopThreadIfRunning();

            int tailleActuelle = grille.getTaille();
            grille = new Grille(tailleActuelle);

            Cellule depart2 = grille.getCellule(0, 0);
            Cellule arrivee2 = grille.getCellule(tailleActuelle - 1, tailleActuelle - 1);
            astar = new Astar(grille, depart2, arrivee2);

            cheminActuel = null;
            exploredTilesLabel.setText("0");
            pathLengthLabel.setText("0");
            updateGridDisplay(null);

            log("Grille réinitialisée (taille = " + tailleActuelle + ").");
        }
    }

    /**
     * Construit l'affichage de la grille
     */
    private void updateGridDisplay(ArrayList<Cellule> partialPath) {
        int taille = grille.getTaille();
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(taille, taille));

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                final int x = i, y = j;
                Cellule cell = grille.getCellule(x, y);

                JButton cellButton = new JButton();
                cellButton.setOpaque(true);
                cellButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cellButton.setPreferredSize(new Dimension(35, 35));

                if (cell.equals(astar.getDepart())) {
                    cellButton.setBackground(Color.GREEN);
                } else if (cell.equals(astar.getArrivee())) {
                    cellButton.setBackground(Color.RED);
                } else if (cell.estObstacle()) {
                    cellButton.setBackground(Color.BLACK);
                } else if (partialPath != null && partialPath.contains(cell)) {
                    cellButton.setBackground(Color.YELLOW);
                } else {
                    cellButton.setBackground(Color.WHITE);
                }


                cellButton.addActionListener(e -> {
                    if (manualObstaclesButton.isSelected() && !cell.equals(astar.getDepart()) && !cell.equals(astar.getArrivee())) {
                        grille.setObstacle(x, y, !cell.estObstacle());
                        cellButton.setBackground(cell.estObstacle() ? Color.BLACK : Color.WHITE);
                    }
                });

                gridPanel.add(cellButton);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }


    /**
     * Applique la nouvelle taille saisie dans sizeField et recrée la grille et l'algorithme
     */
    private void applyNewSize() {
        try {
            int newSize = Integer.parseInt(sizeField.getText());
            if (newSize <= 0) {
                throw new NumberFormatException("Taille <= 0");
            }
            stopThreadIfRunning();

            grille = new Grille(newSize);
            grille.setObstacle(2, 2, true);

            Cellule newDepart = grille.getCellule(0,0);
            Cellule newArrivee = grille.getCellule(newSize-1, newSize-1);
            astar = new Astar(grille, newDepart, newArrivee);

            cheminActuel = null;
            exploredTilesLabel.setText("0");
            pathLengthLabel.setText("0");
            updateGridDisplay(null);

            log("Nouvelle taille appliquée : " + newSize);

        } catch (NumberFormatException ex) {
            String msg = "Valeur de taille invalide. Entrez un entier > 0.";
            JOptionPane.showMessageDialog(this, msg, "Erreur Taille", JOptionPane.ERROR_MESSAGE);
            log("ERREUR : " + msg);
        }
    }

    /**
     * Applique la nouvelle vitesse saisie dans speedField (cases / seconde).
     */
    private void applyNewSpeed() {
        try {
            float speedVal = Float.parseFloat(speedField.getText());
            if (speedVal <= 0) {
                throw new NumberFormatException("Vitesse <= 0");
            }
            currentSpeed = speedVal;
            log("Nouvelle vitesse = " + currentSpeed + " case(s)/seconde");

            if (!stop && animationThread != null && animationThread.isAlive()) {
                int oldPathIndex = pathIndex;
                ArrayList<Cellule> oldPath = cheminActuel;

                stopThreadIfRunning();
                if (oldPath != null) {
                    pathIndex = oldPathIndex;
                    stop = false;
                    animationThread = new Thread(this);
                    animationThread.start();
                }
            }

            JOptionPane.showMessageDialog(this,
                "Vitesse mise à jour : " + currentSpeed + " case(s)/seconde",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            String msg = "Valeur de vitesse invalide. Entrez un réel > 0.";
            JOptionPane.showMessageDialog(this, msg, "Erreur Vitesse", JOptionPane.ERROR_MESSAGE);
            log("ERREUR : " + msg);
        }
    }

    /**
     * Ajoute un message à la zone de texte logArea
     */
    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    /**
     * Applique un style à un JButton.
     */
    private void styleButton(JButton button, Color color) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    }

    /**
     * Parcourt la grille et compte combien de cellules sont des obstacles.
     */
    private int countObstacles(Grille g) {
        int count = 0;
        int n = g.getTaille();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (g.estObstacle(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private void applyObstacles() {
        stopThreadIfRunning();

        int tailleActuelle = grille.getTaille();
        grille = new Grille(tailleActuelle);

        if (randomObstaclesButton.isSelected()) {
            ObstacleManager.placerObstaclesAleatoires(grille, tailleActuelle);
            log("Obstacles placés aléatoirement.");
        } else {
            log("Cliquez sur les cases pour placer les obstacles manuellement.");
        }

        Cellule depart = grille.getCellule(0, 0);
        Cellule arrivee = grille.getCellule(tailleActuelle - 1, tailleActuelle - 1);
        astar = new Astar(grille, depart, arrivee);

        exploredTilesLabel.setText("0");
        pathLengthLabel.setText("0");
        updateGridDisplay(null);
        log("Nouvelle grille appliquée avec obstacles.");
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AStarGUI gui = new AStarGUI();
            gui.display();
        });
    }
}
