package Algorithmes;

import java.util.Random;

public class QLearning {
    private double[][] qTable;
    private double learningRate;
    private double discountFactor;
    private double explorationRate;
    private int stateCount;
    private int actionCount;

    public QLearning(int stateCount, int actionCount, double learningRate, double discountFactor, double explorationRate) {
        this.stateCount = stateCount;
        this.actionCount = actionCount;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        this.explorationRate = explorationRate;
        this.qTable = new double[stateCount][actionCount];
    }

    public int chooseAction(int state) {
        Random rand = new Random();
        if (rand.nextDouble() < explorationRate) {
            return rand.nextInt(actionCount);
        } else {
            return getBestAction(state);
        }
    }

    private int getBestAction(int state) {
        int bestAction = 0;
        double maxQ = qTable[state][0];
        for (int i = 1; i < actionCount; i++) {
            if (qTable[state][i] > maxQ) {
                maxQ = qTable[state][i];
                bestAction = i;
            }
        }
        return bestAction;
    }

    public void updateQTable(int state, int action, int nextState, double reward) {
        double maxFutureQ = qTable[nextState][getBestAction(nextState)];
        qTable[state][action] += learningRate * (reward + discountFactor * maxFutureQ - qTable[state][action]);
    }
}
