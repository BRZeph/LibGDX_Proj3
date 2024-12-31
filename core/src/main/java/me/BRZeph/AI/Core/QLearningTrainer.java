package me.BRZeph.AI.Core;

import me.BRZeph.AI.GameEnvironment.GameEnvironment;

public class QLearningTrainer {
    private QLearningAgent agent;
    private GameEnvironment environment;

    public QLearningTrainer(QLearningAgent agent, GameEnvironment environment) {
        this.agent = agent;
        this.environment = environment;
    }

    public QLearningAgent getAgent() {
        return agent;
    }
}
