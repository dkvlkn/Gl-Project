package process;

import data.Agent;
import data.Grid;

/**
 * MinMax algorithm for strategic decision-making using a decision tree.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class MinMax {
    private Grid grid;
    private static final int MAX_DEPTH = 3;

    public MinMax(Grid grid) {
        this.grid = grid;
    }

    public int[] decideMove(Agent agent, Agent opponent, int resourceX, int resourceY) {
        TreeNode root = TreeNodeFactory.createNode(agent.getXPosition(), agent.getYPosition(),
                opponent.getXPosition(), opponent.getYPosition(), resourceX, resourceY, grid, true);
        Tree tree = new Tree(root);
        tree.buildTree(MAX_DEPTH);

        double bestValue = Double.NEGATIVE_INFINITY;
        TreeNode bestChild = null;
        for (TreeNode child : root.getChildren()) {
            double childValue = minimax(child, true);
            if (childValue > bestValue) {
                bestValue = childValue;
                bestChild = child;
            }
        }
        return bestChild != null ? new int[]{bestChild.getX(), bestChild.getY()} : new int[]{agent.getXPosition(), agent.getYPosition()};
    }

    private double minimax(TreeNode node, boolean maximizing) {
        if (node.getChildren().isEmpty()) {
            return node.evaluate();
        }

        if (maximizing) {
            double maxValue = Double.NEGATIVE_INFINITY;
            for (TreeNode child : node.getChildren()) {
                maxValue = Math.max(maxValue, minimax(child, false));
            }
            node.setValue(maxValue);
            return maxValue;
        } else {
            double minValue = Double.POSITIVE_INFINITY;
            for (TreeNode child : node.getChildren()) {
                minValue = Math.min(minValue, minimax(child, true));
            }
            node.setValue(minValue);
            return minValue;
        }
    }
}