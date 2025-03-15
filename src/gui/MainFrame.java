package gui;

import data.astar.AGrid;
import process.minmax.MinMaxCore;
import process.qlearn.QLearnCore;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final Logger logger = LoggerUtility.getLogger(MainFrame.class, "html");
    private JTabbedPane tabbedPane;
    private GridPanel aStarPanel;
    private GridPanel qLearningPanel;
    private TreePanel minMaxPanel;
    private AGrid aStarGrid;
    private QLearnCore qLearnCore;
    private MinMaxCore minMaxCore;

    public MainFrame(AGrid aStarGrid, QLearnCore qLearnCore, MinMaxCore minMaxCore) {
        this.aStarGrid = aStarGrid;
        this.qLearnCore = qLearnCore;
        this.minMaxCore = minMaxCore;

        logger.info("Initializing MainFrame with A*, Q-Learning, and MinMax components");

        setTitle("AI Algorithms Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        aStarPanel = new GridPanel(aStarGrid, "A*");
        JPanel aStarTab = new JPanel(new BorderLayout());
        aStarTab.add(aStarPanel, BorderLayout.CENTER);
        aStarTab.add(new ControlPanel(this, "A*"), BorderLayout.SOUTH);
        tabbedPane.addTab("A* Pathfinding", aStarTab);

        qLearningPanel = new GridPanel(qLearnCore.getGrid(), "Q-Learning");
        JPanel qLearningTab = new JPanel(new BorderLayout());
        qLearningTab.add(qLearningPanel, BorderLayout.CENTER);
        qLearningTab.add(new ControlPanel(this, "Q-Learning"), BorderLayout.SOUTH);
        tabbedPane.addTab("Q-Learning", qLearningTab);

        minMaxPanel = new TreePanel(minMaxCore);
        JPanel minMaxTab = new JPanel(new BorderLayout());
        minMaxTab.add(new JScrollPane(minMaxPanel), BorderLayout.CENTER);
        minMaxTab.add(new ControlPanel(this, "MinMax"), BorderLayout.SOUTH);
        tabbedPane.addTab("MinMax Game", minMaxTab);

        add(tabbedPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        logger.info("MainFrame initialized and displayed");
    }

    public AGrid getAStarGrid() { return aStarGrid; }
    public QLearnCore getQLearnCore() { return qLearnCore; }
    public MinMaxCore getMinMaxCore() { return minMaxCore; }
    public JTabbedPane getTabbedPane() { return tabbedPane; }

    public void refreshAStar() { aStarPanel.repaint(); }
    public void refreshQLearning() { qLearningPanel.repaint(); }
    public void refreshMinMax() { minMaxPanel.repaint(); }
}