package me.BRZeph.AI.Core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static me.BRZeph.utils.Constants.Constants.AIValues.*;
import static me.BRZeph.utils.GlobalUtils.consoleLog;

public class QLearningAgent {
    private HashMap<State, HashMap<Action, Double>> qTable = new HashMap<>();
    private double learningRate;  // Alpha
    private double discountFactor;  // Gamma
    private double explorationRate;  // Epsilon

    public QLearningAgent(double learningRate, double discountFactor, double explorationRate) {
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        this.explorationRate = explorationRate;
    }

    public Action chooseAction(State state, List<Action> possibleActions) {
        Random random = new Random();
        if (random.nextDouble() < explorationRate) {
            // Exploration
            return possibleActions.get(random.nextInt(possibleActions.size()));
        }

        // Exploitation
        return possibleActions.stream()
            .max(Comparator.comparing(action -> getQValue(state, action)))
            .orElse(null);
    }

    private double getQValue(State state, Action action) {
        return qTable.getOrDefault(state, new HashMap<>()).getOrDefault(action, 0.0);
    }

    public void updateQValue(State state, Action action, double reward, State nextState, List<Action> nextActions) {
        double currentQ = getQValue(state, action);

        // If nextActions is empty, we are in a terminal state, so the max Q-value is 0
        double maxNextQ = 0.0;
        if (!nextActions.isEmpty()) {
            maxNextQ = nextActions.stream()
                .mapToDouble(a -> getQValue(nextState, a))
                .max()
                .orElse(0.0); // In case nextActions is empty, maxNextQ remains 0
        }
        double newQ = currentQ + learningRate * (reward + discountFactor * maxNextQ - currentQ);

        qTable.computeIfAbsent(state, k -> new HashMap<>()).put(action, newQ);
    }

    public void adjustExplorationRate(int currentEpisode) {
        if (currentEpisode == MAX_EPISODES){ // Episode count starts at 1.
            this.explorationRate = 0;
        } else {
            // Exponential decay
//            double decayRate = 0.01; // Adjust decay rate for faster or slower transitions
//            this.explorationRate = Math.max(0.01, STARTING_EXPLORATION_RATE * Math.exp(-decayRate * currentEpisode));

            // Linear decay (comment out one approach)
            this.explorationRate = Math.max(0.01, 1.0 - (double) currentEpisode / MAX_EPISODES);
        }
    }

    public void adjustLearningRate(int currentEpisode) {
        // Exponential decay
//        double decayRate = 0.01; // Adjust this value to control the rate of decay
//        this.learningRate = Math.max(0.01, STARTING_LEARNING_RATE * Math.exp(-decayRate * currentEpisode));

        // Linear decay (comment out one approach)
         this.learningRate = Math.max(0.01, STARTING_LEARNING_RATE - (currentEpisode * STARTING_LEARNING_RATE / MAX_EPISODES));
    }

    public void finalizeEpisode(int episodeCount) {
        consoleLog("[INFO] Episode finalized: " + episodeCount);
    }

    public HashMap<State, HashMap<Action, Double>> getQTable() {
        return this.qTable;
    }
}
