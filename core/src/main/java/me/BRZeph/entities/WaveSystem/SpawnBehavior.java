package me.BRZeph.entities.WaveSystem;

public class SpawnBehavior {
    private final float triggerTime;
    private final float newInterval;
    private boolean behaviorApplied;
    private final int amountPerSpawn;

    public SpawnBehavior(int amountPerSpawn, float triggerTime, float newInterval) {
        this.triggerTime = triggerTime;
        this.newInterval = newInterval;
        this.behaviorApplied = false;
        this.amountPerSpawn = amountPerSpawn;
    }

    public float getTriggerTime() {
        return triggerTime;
    }

    public float getNewInterval() {
        return newInterval;
    }

    public boolean isBehaviorApplied() {
        return behaviorApplied;
    }

    public void setBehaviorApplied(boolean behaviorApplied) {
        this.behaviorApplied = behaviorApplied;
    }

    public int getAmountPerSpawn() {
        return amountPerSpawn;
    }
}
