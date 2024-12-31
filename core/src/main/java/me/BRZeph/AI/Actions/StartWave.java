package me.BRZeph.AI.Actions;

import me.BRZeph.AI.GameEnvironment.GameEnvironment;
import me.BRZeph.AI.Core.Action;

import static me.BRZeph.utils.Constants.AIValues.*;

public class StartWave implements Action {
    private float reward;
    private GameEnvironment environment;
    private int skippedWave;

    public StartWave(int skippedWave){
        this.skippedWave = skippedWave;
    }

    public float getReward() {
        reward = START_WAVE_ACTION_REWARD;
        reward += environment.getCurrencyManager().getGold() * SAVED_GOLD_MULTIPLIER / environment.getWaveManager().getCurrentWaveIndex();
        reward += environment.getCurrencyManager().getEssence() * SAVED_ESSENCE_MULTIPLIER / environment.getWaveManager().getCurrentWaveIndex();
        reward += environment.getCurrencyManager().getMomentum() * SAVED_MOMENTUM_MULTIPLIER / environment.getWaveManager().getCurrentWaveIndex();
        return reward;
    }

    public int getSkippedWave() {
        return skippedWave;
    }

    @Override
    public double performAction(GameEnvironment environment) {
        this.environment = environment;
        if (environment.getWaveManager().isCanStartNewWave()) {
            environment.getWaveManager().startNextWave();
            return 0; // No immediate reward;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "StartWaveAction{" +
            "reward=" + reward +
            ", goldMultiplierResult=" + environment.getCurrencyManager().getGold() * SAVED_GOLD_MULTIPLIER +
            ", essenceMultiplierResult=" + environment.getCurrencyManager().getEssence() * SAVED_ESSENCE_MULTIPLIER +
            ", momentumMultiplierResult=" + environment.getCurrencyManager().getMomentum() * SAVED_MOMENTUM_MULTIPLIER +
            '}';
    }
}

