package process;

import java.util.ArrayList;

/**
 * This class represents a decision tree for the MinMax algorithm.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class Tree {
    private TreeNode root;

    public Tree(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void buildTree(int depth) {
        buildTreeRecursively(root, depth);
    }

    private void buildTreeRecursively(TreeNode node, int remainingDepth) {
        if (remainingDepth == 0) {
            node.setValue(node.evaluate());
            return;
        }

        ArrayList<TreeNode> children = node.generateChildren();
        node.setChildren(children);
        for (TreeNode child : children) {
            buildTreeRecursively(child, remainingDepth - 1);
        }
    }
}