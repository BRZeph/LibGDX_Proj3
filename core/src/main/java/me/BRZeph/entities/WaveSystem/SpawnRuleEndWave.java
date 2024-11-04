package me.BRZeph.entities.WaveSystem;

import me.BRZeph.entities.monster.MonsterType;

public class SpawnRuleEndWave extends SpawnRule {

    private final float timeLimit;
    private boolean appliedBehavior;
    private boolean endBehavior;

    public SpawnRuleEndWave(MonsterType monsterType, int amountPerSpawn, float startTime, float spawnInterval, float timeLimit) {
        super(monsterType, amountPerSpawn, startTime, spawnInterval);
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
