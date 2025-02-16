package Environnement;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un nœud dans l'arbre MinMax.
 * 
 * @author Imane
 */
public class TreeNode {
    private int value;
    private List<TreeNode> children;

    public TreeNode(int value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public int getValue() {
        return value;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}
