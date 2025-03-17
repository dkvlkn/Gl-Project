
package process;

import data.Agent;
import data.Grid;
import java.util.*;

/**
 * Q-learning algorithm for reinforcement learning in a dynamic environment.
 * 
 * @author MOUSSAOUI Imane, HAMITOUCHE Dania, SAMAH Yanis
 */
public class QLearning {
    private Grid grid;
    private HashMap<String, double[]> qTable = new HashMap<>();
    private double alpha = 0.1; // Learning rate
    private double gamma = 0.9; // Discount factor
    private Random random = new Random();

    public QLearning(Grid grid) {
        this.grid = grid;
    }

    public int[] decideMove(Agent agent, int goalX, int goalY) {
        int x = agent.getXPosition();
        int y = agent.getYPosition();
        String state = x + "," + y + "," + goalX + "," + goalY;
        double[] qValues = qTable.computeIfAbsent(state, k -> new double[]{0, 0, 0, 0}); // up, right, down, left

        int action = random.nextDouble() < 0.1 ? random.nextInt(4) : argMax(qValues);
        int[] move = getMoveFromAction(action);
        int newX = x + move[0];
        int newY = y + move[1];

        if (isValid(newX, newY) && !grid.getCell(newX, newY).equals("obstacle")) {
            double reward = (newX == goalX && newY == goalY) ? 10 : -0.1;
            String newState = newX + "," + newY + "," + goalX + "," + goalY;
            double[] nextQValues = qTable.computeIfAbsent(newState, k -> new double[]{0, 0, 0, 0});
            double maxNextQ = Arrays.stream(nextQValues).max().getAsDouble();
            qValues[action] += alpha * (reward + gamma * maxNextQ - qValues[action]);
            return move;
        }
        return new int[]{0, 0}; // Stay if invalid
    }

    private int argMax(double[] array) {
        int maxIdx = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIdx]) maxIdx = i;
        }
        return maxIdx;
    }

    private int[] getMoveFromAction(int action) {
        return switch (action) {
            case 0 -> new int[]{-1, 0}; // up
            case 1 -> new int[]{0, 1};  // right
            case 2 -> new int[]{1, 0};  // down
            case 3 -> new int[]{0, -1}; // left
            default -> new int[]{0, 0};
        };
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight();
    }
}