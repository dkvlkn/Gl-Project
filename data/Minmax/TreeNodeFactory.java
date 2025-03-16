package data.Minmax;

/**
 * Factory pour créer des nœuds de l'arbre MinMax.
 * 
 * @author Imane
 */
public class TreeNodeFactory {
    public static TreeNode createNode(int value) {
        return new TreeNode(value);
    }
}
