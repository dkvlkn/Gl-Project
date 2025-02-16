package Environnement;

/**
 * Classe pour construire un arbre MinMax d'exemple.
 * 
 * @author Imane
 */
public class TreeBuilder {
    public static TreeNode buildExampleTree() {
        TreeNode root = new TreeNode(0);

        TreeNode child1 = new TreeNode(3);
        TreeNode child2 = new TreeNode(5);
        TreeNode child3 = new TreeNode(2);

        root.addChild(child1);
        root.addChild(child2);
        root.addChild(child3);

        child1.addChild(new TreeNode(6));
        child1.addChild(new TreeNode(4));

        child2.addChild(new TreeNode(8));
        child2.addChild(new TreeNode(7));

        child3.addChild(new TreeNode(1));
        child3.addChild(new TreeNode(3));

        return root;
    }
}
