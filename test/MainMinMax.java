package test;

import Environnement.TreeBuilder;
import Environnement.TreeNode;
import Algorithmes.MinMaxTree;

/**
 * Programme de test pour l'algorithme MinMax.
 * 
 * @author Imane
 */
public class MainMinMax {
    public static void main(String[] args) {
        TreeNode root = TreeBuilder.buildExampleTree();
        MinMaxTree tree = new MinMaxTree(root);

        int bestMove = tree.getBestMove();
        System.out.println("Meilleur score trouvé par MinMax : " + bestMove);
    }
}
