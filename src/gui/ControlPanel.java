package gui;

import data.astar.ACell;
import data.astar.AGrid;
import data.qlearn.QCell;
import data.qlearn.QGrid;
import gui.instrument.ChartManager;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.astar.AStarCore;
import process.minmax.MinMaxCore;
import process.qlearn.QLearnCore;
import process.visitor.ElementCountVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class ControlPanel extends JPanel {
    private static final Logger logger = LoggerUtility.getLogger(ControlPanel.class, "html");
    private MainFrame mainFrame;
    private String type;
    private ChartManager chartManager;

    public ControlPanel(MainFrame mainFrame, String type) {
        this.mainFrame = mainFrame;
        this.type = type;
        this.chartManager = new ChartManager();
        setLayout(new FlowLayout());

        if (type.equals("A*")) {
            JButton solveButton = new JButton("Solve A* (Step-by-Step)");
            solveButton.addActionListener(e -> solveAStarStepByStep());
            JButton instantSolveButton = new JButton("Solve A* (Instant)");
            instantSolveButton.addActionListener(e -> solveAStarInstant());
            JButton statsButton = new JButton("Show Stats");
            statsButton.addActionListener(e -> showStats());
            add(solveButton);
            add(instantSolveButton);
            add(statsButton);
        } else if (type.equals("Q-Learning")) {
            JButton trainButton = new JButton("Train Q-Learning (Step-by-Step)");
            trainButton.addActionListener(e -> trainQLearningStepByStep());
            JButton trainInstantButton = new JButton("Train Q-Learning (Instant)");
            trainInstantButton.addActionListener(e -> trainQLearningInstant());
            JButton pathButton = new JButton("Show Best Path");
            pathButton.addActionListener(e -> showQLearningPath());
            JButton statsButton = new JButton("Show Stats");
            statsButton.addActionListener(e -> showStats());
            add(trainButton);
            add(trainInstantButton);
            add(pathButton);
            add(statsButton);
        } else if (type.equals("MinMax")) {
            JButton move1Button = new JButton("Player: Take 1");
            move1Button.addActionListener(e -> playerMove(1));
            JButton move2Button = new JButton("Player: Take 2");
            move2Button.addActionListener(e -> playerMove(2));
            JButton move3Button = new JButton("Player: Take 3");
            move3Button.addActionListener(e -> playerMove(3));
            JButton aiMoveButton = new JButton("AI Move");
            aiMoveButton.addActionListener(e -> aiMove());
            add(move1Button);
            add(move2Button);
            add(move3Button);
            add(aiMoveButton);
        }
    }
/**
    private void solveAStarStepByStep() {
        logger.info("Starting A* algorithm (step-by-step)");
        AGrid grid = mainFrame.getAStarGrid();
        AStarCore aStar = new AStarCore(grid.getSize());
        aStar.getGrid().setGrid(grid.getGrid());
        aStar.getGrid().setStartingCell(grid.getStartingCell());
        aStar.getGrid().setEndingCell(grid.getEndingCell());

        GridPanel gridPanel = ((GridPanel) ((JPanel) mainFrame.getTabbedPane().getComponent(0)).getComponent(0));

        new Thread(() -> {
            while (!aStar.workFinished()) {
                aStar.process();
                ArrayList<ACell> currentPath = aStar.getClosedList().get(aStar.getClosedListSize() - 1).getGenealogy();
                gridPanel.setPath(currentPath);
                gridPanel.setExploredCells(aStar.getClosedList()); // Toutes les cellules explorées
                mainFrame.refreshAStar();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.error("A* step-by-step interrupted", e);
                }
            }
            if (aStar.isEnded()) {
                ArrayList<ACell> finalPath = aStar.getClosedList().get(aStar.getClosedListSize() - 1).getGenealogy();
                logger.info("A* path found with " + finalPath.size() + " steps (step-by-step)");
                gridPanel.setPath(finalPath);
                gridPanel.setExploredCells(aStar.getClosedList());
            } else {
                logger.warn("A* failed: No path found (step-by-step)");
                JOptionPane.showMessageDialog(mainFrame, "No path found!");
            }
        }).start();
    }
**/
    //a tester
    private void solveAStarStepByStep() {
        logger.info("Starting A* algorithm (step-by-step)");
        AGrid grid = mainFrame.getAStarGrid(); // Grille générée par GridFactory dans HomePage
        AStarCore aStar = new AStarCore(grid.getSize()); // Crée AStarCore avec la taille
        aStar.getGrid().setGrid(grid.getGrid());
        aStar.getGrid().setStartingCell(grid.getStartingCell());
        aStar.getGrid().setEndingCell(grid.getEndingCell());
        aStar.getOpenList().getQueue().clear(); // Vider l'openList initial
        aStar.getOpenList().getQueue().add(grid.getStartingCell()); // Réinitialiser avec le bon départ
        aStar.getClosedList().clear(); // Vider la closedList

        GridPanel gridPanel = ((GridPanel) ((JPanel) mainFrame.getTabbedPane().getComponent(0)).getComponent(0));

        new Thread(() -> {
            while (!aStar.workFinished()) {
                aStar.process();
                ArrayList<ACell> currentPath = aStar.getClosedList().isEmpty() ? new ArrayList<>() : aStar.getClosedList().get(aStar.getClosedListSize() - 1).getGenealogy();
                gridPanel.setPath(currentPath);
                gridPanel.setExploredCells(aStar.getClosedList());
                mainFrame.refreshAStar();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.error("A* step-by-step interrupted", e);
                }
            }
            if (aStar.isEnded()) {
                ArrayList<ACell> finalPath = aStar.getClosedList().get(aStar.getClosedListSize() - 1).getGenealogy();
                logger.info("A* path found with " + finalPath.size() + " steps (step-by-step)");
                gridPanel.setPath(finalPath);
                gridPanel.setExploredCells(aStar.getClosedList());
            } else {
                logger.warn("A* failed: No path found (step-by-step)");
                JOptionPane.showMessageDialog(mainFrame, "No path found!");
            }
        }).start();
    }

    private void solveAStarInstant() {
        logger.info("Starting A* algorithm (instant)");
        AGrid grid = mainFrame.getAStarGrid();
        AStarCore aStar = new AStarCore(grid.getSize());
        aStar.getGrid().setGrid(grid.getGrid());
        aStar.getGrid().setStartingCell(grid.getStartingCell());
        aStar.getGrid().setEndingCell(grid.getEndingCell());
        aStar.getOpenList().getQueue().clear();
        aStar.getOpenList().getQueue().add(grid.getStartingCell());
        aStar.getClosedList().clear();

        GridPanel gridPanel = ((GridPanel) ((JPanel) mainFrame.getTabbedPane().getComponent(0)).getComponent(0));

        while (!aStar.workFinished()) {
            aStar.process();
        }
        if (aStar.isEnded()) {
            ArrayList<ACell> finalPath = aStar.getClosedList().get(aStar.getClosedListSize() - 1).getGenealogy();
            logger.info("A* path found with " + finalPath.size() + " steps (instant)");
            gridPanel.setPath(finalPath);
            gridPanel.setExploredCells(aStar.getClosedList());
        } else {
            logger.warn("A* failed: No path found (instant)");
            JOptionPane.showMessageDialog(mainFrame, "No path found!");
        }
    }
/** 
    private void solveAStarInstant() {
        logger.info("Starting A* algorithm (instant)");
        AGrid grid = mainFrame.getAStarGrid();
        AStarCore aStar = new AStarCore(grid.getSize());
        aStar.getGrid().setGrid(grid.getGrid());
        aStar.getGrid().setStartingCell(grid.getStartingCell());
        aStar.getGrid().setEndingCell(grid.getEndingCell());

        GridPanel gridPanel = ((GridPanel) ((JPanel) mainFrame.getTabbedPane().getComponent(0)).getComponent(0));

        while (!aStar.workFinished()) {
            aStar.process();
        }
        if (aStar.isEnded()) {
            ArrayList<ACell> finalPath = aStar.getClosedList().get(aStar.getClosedListSize() - 1).getGenealogy();
            logger.info("A* path found with " + finalPath.size() + " steps (instant)");
            gridPanel.setPath(finalPath);
            gridPanel.setExploredCells(aStar.getClosedList());
        } else {
            logger.warn("A* failed: No path found (instant)");
            JOptionPane.showMessageDialog(mainFrame, "No path found!");
        }
    }
**/
    private void trainQLearningStepByStep() {
        logger.info("Starting Q-Learning training (step-by-step)");
        QLearnCore core = mainFrame.getQLearnCore();
        new Thread(() -> {
            while (core.getNbIte() > 0) {
                core.doOneIteration();
                ArrayList<QCell> currentPath = core.getPath();
                ((GridPanel) ((JPanel) mainFrame.getTabbedPane().getComponent(1)).getComponent(0)).setPath(currentPath);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    logger.error("Q-Learning step-by-step interrupted", e);
                }
            }
            logger.info("Q-Learning training completed (step-by-step), success rate: " + (core.success * 100.0 / core.getNbTot()) + "%");
            JOptionPane.showMessageDialog(mainFrame, "Training completed! Success rate: " + (core.success * 100.0 / core.getNbTot()) + "%");
        }).start();
    }

    private void trainQLearningInstant() {
        logger.info("Starting Q-Learning training (instant)");
        QLearnCore core = mainFrame.getQLearnCore();
        while (core.getNbIte() > 0) {
            core.doOneIteration();
        }
        logger.info("Q-Learning training completed (instant), success rate: " + (core.success * 100.0 / core.getNbTot()) + "%");
        mainFrame.refreshQLearning();
        JOptionPane.showMessageDialog(mainFrame, "Training completed! Success rate: " + (core.success * 100.0 / core.getNbTot()) + "%");
    }

    private void showQLearningPath() {
        logger.info("Showing Q-Learning best path");
        QLearnCore core = mainFrame.getQLearnCore();
        ArrayList<QCell> bestPath = core.bestPath();
        ((GridPanel) ((JPanel) mainFrame.getTabbedPane().getComponent(1)).getComponent(0)).setPath(bestPath);
        logger.info("Best path shown with " + bestPath.size() + " steps");
    }

    private void playerMove(int coins) {
        logger.info("Player move: Taking " + coins + " coins");
        MinMaxCore core = mainFrame.getMinMaxCore();
        if (core.isPlayerTurn() && core.getCoin() >= coins) {
            core.playerMove(core.getCoin() - coins);
            mainFrame.refreshMinMax();
            if (core.isEnded()) {
                logger.info("Game ended: Player wins!");
                JOptionPane.showMessageDialog(mainFrame, "Game Over! You win!");
            }
        } else {
            logger.warn("Invalid move: Not player's turn or not enough coins");
            JOptionPane.showMessageDialog(mainFrame, "Invalid move!");
        }
    }

    private void aiMove() {
        logger.info("AI move initiated");
        MinMaxCore core = mainFrame.getMinMaxCore();
        if (!core.isPlayerTurn() && !core.isEnded()) {
            core.process();
            mainFrame.refreshMinMax();
            if (core.isEnded()) {
                logger.info("Game ended: AI wins!");
                JOptionPane.showMessageDialog(mainFrame, "Game Over! AI wins!");
            } else {
                logger.info("AI updated coin count, remaining: " + core.getCoin());
            }
        } else {
            logger.warn("Invalid AI move: It's player's turn or game is ended");
            JOptionPane.showMessageDialog(mainFrame, "It's your turn or game is over!");
        }
    }

    private void showStats() {
        ElementCountVisitor visitor = new ElementCountVisitor(chartManager);
        chartManager.reset();
        
        if (type.equals("A*")) {
            AGrid grid = mainFrame.getAStarGrid();
            for (int x = 0; x < grid.getSize(); x++) {
                for (int y = 0; y < grid.getSize(); y++) {
                    ACell cell = grid.getCell(x, y);
                    if (cell.getElement() != null) {
                        cell.getElement().accept(visitor);
                    }
                }
            }
        } else if (type.equals("Q-Learning")) {
            QGrid grid = mainFrame.getQLearnCore().getGrid();
            for (int x = 0; x < grid.getSize(); x++) {
                for (int y = 0; y < grid.getSize(); y++) {
                    QCell cell = grid.getCell(x, y);
                    if (cell.getElement() != null) {
                        cell.getElement().accept(visitor);
                    }
                }
            }
        }

        String stats = "Element Counts:\n";
        for (Map.Entry<String, Integer> entry : chartManager.getElementCounts().entrySet()) {
            stats += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        logger.info("Stats displayed: " + stats);
        JOptionPane.showMessageDialog(mainFrame, stats);
    }
}