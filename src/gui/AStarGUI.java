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
 * Interface graphique pour l'algorithme A*,
 * s'appuyant sur les classes Grille, Cellule, Coordonnee et Astar.
 * 
 * Cette classe :
 *  - Crée une fenêtre avec un panneau de paramètres à gauche, 
 *    un panneau de boutons à droite, un espace pour la grille au centre
 *    et une zone de log (journal) en bas.
 *  - Permet de lancer le calcul du chemin via A* (bouton START)
 *  - Affiche le chemin progressivement via un thread d'animation (run())
 *  - Logue toutes les actions (clics, changement de vitesse, cases révélées...)
 *    dans la bande inférieure (logArea).
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
    
 // Choix du mode d'obstacles
    private JRadioButton randomObstaclesButton;
    private JRadioButton manualObstaclesButton;
    private JTextField obstacleCoordsField;

    /**
     * Constructeur de la fenêtre AStarGUI.
     * Initialise la grille par défaut (10x10 + obstacles), 
     * puis construit l'interface.
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
     * Construit l'interface graphique : 
     * - Panneau de paramètres à gauche
     * - Panneau central (gridPanel) pour la grille
     * - Panneau de boutons à droite
     * - Bande inférieure (logArea) pour le journal
     */
    private void initUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Panneau principal (BorderLayout)
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

        // --------- Panneau gauche : PARAMETERS ---------
        JPanel parametersPanel = new JPanel();
        parametersPanel.setLayout(new BoxLayout(parametersPanel, BoxLayout.Y_AXIS));
        parametersPanel.setBackground(new Color(240, 248, 255));
        parametersPanel.setBorder(BorderFactory.createTitledBorder("PARAMETERS"));

        // Ligne "Taille"
        JPanel sizeLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        sizeLine.setBackground(parametersPanel.getBackground());
        JLabel sizeLabel = new JLabel("Size :");
        sizeField = new JTextField(Integer.toString(grille.getTaille()), 5);
        JButton applySizeButton = new JButton("Appliquer");
        applySizeButton.addActionListener(e -> applyNewSize());
        sizeLine.add(sizeLabel);
        sizeLine.add(sizeField);
        sizeLine.add(applySizeButton);

        // Ligne "Vitesse"
        JPanel speedLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        speedLine.setBackground(parametersPanel.getBackground());
        JLabel speedLab = new JLabel("Speed :");
        speedField = new JTextField("1.0", 5);
        JButton applySpeedButton = new JButton("Appliquer");
        applySpeedButton.addActionListener(e -> applyNewSpeed());
        speedLine.add(speedLab);
        speedLine.add(speedField);
        speedLine.add(applySpeedButton);

        // Ligne "Explored Tiles"
        JPanel exploredLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        exploredLine.setBackground(parametersPanel.getBackground());
        JLabel exploredLab = new JLabel("Explored Tiles :");
        exploredTilesLabel = new JLabel("0");
        exploredLine.add(exploredLab);
        exploredLine.add(exploredTilesLabel);

        // Ligne "Path Length"
        JPanel pathLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pathLine.setBackground(parametersPanel.getBackground());
        JLabel pathLab = new JLabel("Path Length :");
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

        // --------- Zone centrale : la grille ---------
        gridPanel = new JPanel();
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        updateGridDisplay(null);
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // --------- Panneau droit : boutons ---------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton startButton = new JButton("START");
        styleButton(startButton, new Color(144, 238, 144));
        startButton.addActionListener(new StartAction());

        JButton restartButton = new JButton("RESTART");
        styleButton(restartButton, new Color(255, 182, 193));
        restartButton.addActionListener(new RestartAction());

        JButton stopButton = new JButton("STOP");
        styleButton(stopButton, new Color(255, 215, 0));
        stopButton.addActionListener(e -> {
            log("STOP clicked");
            stopThreadIfRunning();
        });

        JButton backButton = new JButton("BACK");
        styleButton(backButton, new Color(240, 128, 128));
        backButton.addActionListener(e -> {
            log("BACK clicked - closing window");
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

        // --------- Bande inférieure : log (journal) ---------
        logArea = new JTextArea(5, 50);
        logArea.setEditable(false);
        logArea.setBackground(new Color(245, 245, 245));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Log / Journal"));
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Ajout du panel principal à la fenêtre
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
     * Boucle d'animation : affiche le chemin A* case par case tant que 'stop' = false.
     */
    @Override
    public void run() {
        log("Animation thread started (speed=" + currentSpeed + " case/s).");

        // On continue tant qu'on n'a pas interrompu et qu'on n'a pas atteint la fin du chemin
        while (!stop && cheminActuel != null && pathIndex < cheminActuel.size()) {
            try {
                // Calcul du délai en ms
                long delay = (long)(1000 / currentSpeed);
                if (delay < 1) delay = 1;
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                // animation interrompue
            }

            if (!stop) {
                // On révèle la case suivante
                pathIndex++;

                SwingUtilities.invokeLater(() -> {
                    // Partie du chemin visible
                    ArrayList<Cellule> partial = new ArrayList<>(cheminActuel.subList(0, pathIndex));
                    updateGridDisplay(partial);

                    // Cellule tout juste révélée (index = pathIndex-1)
                    Cellule newlyRevealed = cheminActuel.get(pathIndex - 1);
                    int x = newlyRevealed.getCoordonnee().getX();
                    int y = newlyRevealed.getCoordonnee().getY();
                    log("Case " + pathIndex + " révélée : (" + x + "," + y + ")");

                    // Mise à jour des labels
                    exploredTilesLabel.setText(String.valueOf(astar.getCasesExplorees()));
                    pathLengthLabel.setText(String.valueOf(pathIndex));
                });
            }
        }

        // Si on a terminé le chemin sans être interrompu
        if (!stop && cheminActuel != null && pathIndex >= cheminActuel.size()) {
            SwingUtilities.invokeLater(() -> {
                // On calcule aussi le nombre d'obstacles présents dans la grille
                int nbObstacles = countObstacles(grille);

                // Message final complet (chemin affiché, cases explorées, nb d'obstacles, etc.)
                String msg = "Chemin entièrement affiché.\n"
                           + "Cases explorées : " + astar.getCasesExplorees() + "\n"
                           + "Longueur du chemin : " + cheminActuel.size() + "\n"
                           + "Nombre d'obstacles : " + nbObstacles;
                
                JOptionPane.showMessageDialog(AStarGUI.this, msg);
                log(msg);
            });
        }

        log("Animation thread ended.");
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

        // On logue le résultat
        log("Chemin trouvé. Cases explorées = " + astar.getCasesExplorees()
            + ", Longueur totale = " + cheminActuel.size());

        // On stoppe toute animation en cours pour en relancer une propre
        stopThreadIfRunning();

        // Paramètres initiaux de l'animation
        stop = false;
        pathIndex = 0;

        // On lance le thread
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
            log("START clicked");
            startAnimation();
        }
    }

    /**
     * Listener du bouton RESTART.
     * Réinitialise la grille tout en conservant la même taille actuelle.
     */
    private class RestartAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            log("RESTART clicked");
            stopThreadIfRunning();

            int tailleActuelle = grille.getTaille();
            grille = new Grille(tailleActuelle);

            // On remet quelques obstacles
            grille.setObstacle(2, 2, true);
            grille.setObstacle(2, 3, true);
            grille.setObstacle(3, 2, true);
            grille.setObstacle(4, 5, true);
            grille.setObstacle(5, 5, true);

            // Nouveau Astar
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
     * Construit l'affichage de la grille (GridLayout de taille x taille).
     * On colorie chaque cellule selon :
     *  - noir si obstacle
     *  - vert si c'est la cellule de départ
     *  - rouge si c'est la cellule d'arrivée
     *  - jaune si la cellule est dans le partialPath
     *  - blanc sinon
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

                // Gestion clic obstacle manuel sans MouseListener
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
     * Applique la nouvelle taille saisie dans sizeField.
     * Recrée la grille et l'algorithme avec cette nouvelle taille.
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
     * Si une animation est en cours, on l'arrête et on la relance au même pathIndex,
     * pour appliquer immédiatement la nouvelle vitesse.
     */
    private void applyNewSpeed() {
        try {
            float speedVal = Float.parseFloat(speedField.getText());
            if (speedVal <= 0) {
                throw new NumberFormatException("Vitesse <= 0");
            }
            currentSpeed = speedVal;
            log("Nouvelle vitesse = " + currentSpeed + " case(s)/seconde");

            // Si on veut l'appliquer tout de suite pendant l'animation
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
     * Ajoute un message à la zone de texte logArea (bande inférieure),
     * puis fait défiler pour que le dernier message soit visible.
     */
    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    /**
     * Applique un style simple (couleur, taille police) à un JButton.
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
     * @param g la grille
     * @return le nombre total d'obstacles
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
