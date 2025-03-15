package gui;

import data.minmax.AdversaryNode;
import data.minmax.Node;
import data.minmax.PlayerNode;
import process.minmax.MinMaxCore;

import javax.swing.*;
import java.awt.*;

public class TreePanel extends JPanel {
    private MinMaxCore minMaxCore;
    private static final int NODE_SIZE = 40;
    private static final int LEVEL_HEIGHT = 100;
    private static final int HORIZONTAL_SPACING = 60;

    public TreePanel(MinMaxCore minMaxCore) {
        this.minMaxCore = minMaxCore;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawString("Coins remaining: " + minMaxCore.getCoin(), 10, 20);
        g.drawString("Player turn: " + minMaxCore.isPlayerTurn(), 10, 40);
        g.drawString("Nodes created: " + minMaxCore.getAmountOfNodeCreated(), 10, 60);

        Node root = minMaxCore.isPlayerTurn() ? null : new process.minmax.TreeFactory().buildPlayerNode(minMaxCore.getCoin(), minMaxCore.getDifficulty());
        if (root != null) {
            drawTree(g, root, getWidth() / 2, 100, 0, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, Node node, int x, int y, int level, int spacing) {
        if (node instanceof PlayerNode) {
            g.setColor(Color.BLUE);
        } else if (node instanceof AdversaryNode) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GRAY);
        }
        g.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
        g.setColor(Color.BLACK);
        g.drawOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
        g.drawString(node.getValue() + " (H: " + node.calculateHeuristic() + ")", x - 20, y + 5);

        int nextY = y + LEVEL_HEIGHT;
        int childSpacing = spacing / 2;

        if (node.getLeftChild() != null) {
            int leftX = x - spacing;
            g.drawLine(x, y + NODE_SIZE / 2, leftX, nextY - NODE_SIZE / 2);
            drawTree(g, node.getLeftChild(), leftX, nextY, level + 1, childSpacing);
        }
        if (node.getMiddleChild() != null) {
            int middleX = x;
            g.drawLine(x, y + NODE_SIZE / 2, middleX, nextY - NODE_SIZE / 2);
            drawTree(g, node.getMiddleChild(), middleX, nextY, level + 1, childSpacing);
        }
        if (node.getRightChild() != null) {
            int rightX = x + spacing;
            g.drawLine(x, y + NODE_SIZE / 2, rightX, nextY - NODE_SIZE / 2);
            drawTree(g, node.getRightChild(), rightX, nextY, level + 1, childSpacing);
        }
    }
}