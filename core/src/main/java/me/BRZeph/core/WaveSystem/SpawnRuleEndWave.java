package me.BRZeph.core.WaveSystem;

import me.BRZeph.entities.monster.MonsterType;
import me.BRZeph.entities.monster.Variant;

public class SpawnRuleEndWave extends SpawnRule {

    private final float timeLimit;
    private boolean appliedBehavior;
    private boolean endBehavior;

    public SpawnRuleEndWave(MonsterType monsterType, int amountPerSpawn, float startTime, float spawnInterval, float timeLimit,
                            Variant variant) {
        super(monsterType, amountPerSpawn, startTime, spawnInterval, variant);
        this.timeLimit = timeLimit;
        this.appliedBehavior = false;
        this.endBehavior = false;
    }

    public float getTimeLimit() {
        return timeLimit;
    }

    public boolean isAppliedBehavior() {
        return appliedBehavior;
    }

    public void setAppliedBehavior(boolean appliedBehavior) {
        this.appliedBehavior = appliedBehavior;
    }

    public boolean isEndBehavior() {
        return endBehavior;
    }

    public void setEndBehavior(boolean endBehavior) {
        this.endBehavior = endBehavior;
    }
}
