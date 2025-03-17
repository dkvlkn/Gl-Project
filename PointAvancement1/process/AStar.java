
package process;

import data.Agent;
import data.Grid;
import java.util.*;

/**
 * A* algorithm for optimal pathfinding in a 2D grid.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class AStar {
    private Grid grid;

    public AStar(Grid grid) {
        this.grid = grid;
    }

    public ArrayList<int[]> findPath(Agent agent, int goalX, int goalY) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
        Set<String> closedList = new HashSet<>();
        HashMap<String, Node> allNodes = new HashMap<>();

        int startX = agent.getXPosition();
        int startY = agent.getYPosition();
        Node startNode = new Node(startX, startY, 0, heuristic(startX, startY, goalX, goalY));
        openList.add(startNode);
        allNodes.put(startX + "," + startY, startNode);

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // right, down, left, up

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            String currentKey = current.x + "," + current.y;

            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }

            closedList.add(currentKey);

            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                String newKey = newX + "," + newY;

                if (isValid(newX, newY) && !closedList.contains(newKey) && !grid.getCell(newX, newY).equals("obstacle")) {
                    double g = current.g + 1;
                    double h = heuristic(newX, newY, goalX, goalY);
                    double f = g + h;

                    Node neighbor = allNodes.getOrDefault(newKey, new Node(newX, newY, g, h));
                    if (!allNodes.containsKey(newKey)) {
                        neighbor.parent = current;
                        openList.add(neighbor);
                        allNodes.put(newKey, neighbor);
                    } else if (g < neighbor.g) {
                        neighbor.g = g;
                        neighbor.f = f;
                        neighbor.parent = current;
                    }
                }
            }
        }
        return null; // No path found
    }

    private double heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan distance
    }

    private ArrayList<int[]> reconstructPath(Node node) {
        ArrayList<int[]> path = new ArrayList<>();
        while (node != null) {
            path.add(0, new int[]{node.x, node.y});
            node = node.parent;
        }
        return path;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight();
    }

    private static class Node {
        int x, y;
        double g, h, f;
        Node parent;

        Node(int x, int y, double g, double h) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }
    }
}