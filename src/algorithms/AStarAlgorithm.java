package algorithms;

import java.util.*;

import environment.Block;
import environment.Grid;

public class AStarAlgorithm {
    public List<Block> computePath(Grid grid, Block start, Block goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<Block, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, heuristic(start, goal));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.block.equals(goal)) {
                return buildPath(current);
            }

            for (Block neighbor : grid.getNeighbors(current.block)) {
                double tentativeG = current.g + 1; // Coût de déplacement entre deux blocs voisins

                Node neighborNode = allNodes.getOrDefault(neighbor, new Node(neighbor));
                allNodes.put(neighbor, neighborNode);

                if (tentativeG < neighborNode.g) {
                    neighborNode.parent = current;
                    neighborNode.g = tentativeG;
                    neighborNode.f = tentativeG + heuristic(neighbor, goal);

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        return Collections.emptyList(); // Aucun chemin trouvé
    }

    private double heuristic(Block a, Block b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getColumn() - b.getColumn()); // Distance de Manhattan
    }

    private List<Block> buildPath(Node node) {
        List<Block> path = new ArrayList<>();
        while (node != null) {
            path.add(node.block);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static class Node implements Comparable<Node> {
        Block block;
        Node parent;
        double g; // Coût réel
        double f; // Coût total (g + heuristique)

        Node(Block block, Node parent, double g, double f) {
            this.block = block;
            this.parent = parent;
            this.g = g;
            this.f = f;
        }

        Node(Block block) {
            this(block, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.f, other.f);
        }
    }
}