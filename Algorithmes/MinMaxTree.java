package Algorithmes;

import Environnement.TreeNode;

/**
 * Implémentation de l'algorithme MinMax sous forme d'arbre.
 */
public class MinMaxTree implements Tree {
    private TreeNode root;

    public MinMaxTree(TreeNode root) {
        this.root = root;
    }

    /**
     * Applique l'algorithme MinMax de manière récursive.
     *
     * @param node         Le nœud courant.
     * @param isMaximizing Indique si l'on maximise ou minimise.
     * @return La valeur obtenue.
     */
    public int minMax(TreeNode node, boolean isMaximizing) {
        if (node.isLeaf()) {
            return node.getValue();
        }

        int bestValue = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (TreeNode child : node.getChildren()) {
            int value = minMax(child, !isMaximizing);
            bestValue = isMaximizing ? Math.max(bestValue, value) : Math.min(bestValue, value);
        }
        return bestValue;
    }

    /**
     * Renvoie le meilleur coup (score) selon MinMax.
     *
     * @return Le meilleur score.
     */
    public int getBestMove() {
        return minMax(root, true);
    }

    @Override
    public Tree getLeftChild() {
        return root.getChildren().size() > 0 ? (Tree) root.getChildren().get(0) : null;
    }

    @Override
    public Tree getRightChild() {
        return root.getChildren().size() > 1 ? (Tree) root.getChildren().get(1) : null;
    }

    @Override
    public int evaluate() {
        return getBestMove();
    }
}
