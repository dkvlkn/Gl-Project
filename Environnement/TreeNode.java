package Environnement;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un nœud dans l'arbre MinMax.
 * Chaque nœud contient une valeur entière et une liste de nœuds enfants.
 * 
 * @author Imane
 */
public class TreeNode {
    private int value; 
    private List<TreeNode> children; 

    /**
     * Constructeur de la classe TreeNode.
     * Initialise un nœud avec une valeur donnée et une liste vide d'enfants.
     * 
     * @param value Valeur du nœud.
     */
    public TreeNode(int value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    /**
     * Ajoute un enfant à la liste des enfants du nœud.
     * 
     * @param child Le nœud enfant à ajouter.
     */
    public void addChild(TreeNode child) {
        children.add(child);
    }

    /**
     * Retourne la liste des enfants du nœud.
     * 
     * @return Liste des nœuds enfants.
     */
    public List<TreeNode> getChildren() {
        return children;
    }

    /**
     * Retourne la valeur du nœud.
     * 
     * @return La valeur entière stockée dans le nœud.
     */
    public int getValue() {
        return value;
    }

    /**
     * Vérifie si le nœud est une feuille (c'est-à-dire s'il n'a pas d'enfants).
     * 
     * @return true si le nœud est une feuille, false sinon.
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }
}
