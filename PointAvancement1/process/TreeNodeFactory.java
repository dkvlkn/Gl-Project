package process;

import data.Grid;

/**
 * Factory class to create TreeNode instances.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class TreeNodeFactory {
    public static TreeNode createNode(int x, int y, int oppX, int oppY, int resX, int resY, Grid grid, boolean isMaximizing) {
        return new TreeNode(x, y, oppX, oppY, resX, resY, grid, isMaximizing);
    }
}