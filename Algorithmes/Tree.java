package Algorithmes;

/**
 * Interface représentant un arbre pour l'algorithme MinMax.
 */
public interface Tree {
    Tree getLeftChild();
    Tree getRightChild();
    int evaluate();
}
