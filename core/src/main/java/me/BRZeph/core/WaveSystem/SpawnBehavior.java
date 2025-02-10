package me.BRZeph.core.WaveSystem;

import me.BRZeph.entities.monster.Variant;

public class SpawnBehavior {
    private final float triggerTime;
    private final float newInterval;
    private boolean behaviorApplied;
    private final int amountPerSpawn;
    private Variant variant;

    public SpawnBehavior(int amountPerSpawn, float triggerTime, float newInterval, Variant variant) {
        this.triggerTime = triggerTime;
        this.newInterval = newInterval;
        this.behaviorApplied = false;
        this.amountPerSpawn = amountPerSpawn;
        this.variant = variant;
    }

    public Variant getVariant() {
        return variant;
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
