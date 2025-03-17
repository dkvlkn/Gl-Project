package process;

import data.Agent;
import data.Grid;
import java.util.ArrayList;

/**
 * This class represents a node in the decision tree for MinMax.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class TreeNode {
    private int x, y; // Agent position
    private int oppX, oppY; // Opponent position
    private int resX, resY; // Resource position
    private Grid grid;
    private boolean isMaximizing; // True for agent's turn, false for opponent's
    private double value;
    private ArrayList<TreeNode> children;
    private TreeNode parent;

    public TreeNode(int x, int y, int oppX, int oppY, int resX, int resY, Grid grid, boolean isMaximizing) {
        this.x = x;
        this.y = y;
        this.oppX = oppX;
        this.oppY = oppY;
        this.resX = resX;
        this.resY = resY;
        this.grid = grid;
        this.isMaximizing = isMaximizing;
        this.children = new ArrayList<>();
    }

    public ArrayList<TreeNode> generateChildren() {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // right, down, left, up
        ArrayList<TreeNode> childrenList = new ArrayList<>();

        if (isMaximizing) {
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (isValid(newX, newY) && !grid.getCell(newX, newY).equals("obstacle")) {
                    TreeNode child = TreeNodeFactory.createNode(newX, newY, oppX, oppY, resX, resY, grid, false);
                    child.setParent(this);
                    childrenList.add(child);
                }
            }
        } else {
            for (int[] dir : directions) {
                int newOppX = oppX + dir[0];
                int newOppY = oppY + dir[1];
                if (isValid(newOppX, newOppY) && !grid.getCell(newOppX, newOppY).equals("obstacle")) {
                    TreeNode child = TreeNodeFactory.createNode(x, y, newOppX, newOppY, resX, resY, grid, true);
                    child.setParent(this);
                    childrenList.add(child);
                }
            }
        }
        return childrenList;
    }

    public double evaluate() {
        double distToResource = Math.abs(x - resX) + Math.abs(y - resY);
        double distToOpponent = Math.abs(x - oppX) + Math.abs(y - oppY);
        return -distToResource + (distToOpponent > 2 ? 1 : -1); // Same evaluation as before
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
}