package Algorithmes;

/**
 * Interface représentant un arbre pour l'algorithme MinMax.
 * 
 * @author imane
 */
public interface Tree {
    Tree getLeftChild();
    Tree getRightChild();
    int evaluate(); 
}
