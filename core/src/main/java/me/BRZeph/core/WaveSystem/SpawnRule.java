package me.BRZeph.core.WaveSystem;

import me.BRZeph.entities.monster.MonsterType;
import me.BRZeph.entities.monster.Variant;

public class SpawnRule {
    private final MonsterType monsterType;
    private Variant variant;
    private int amountPerSpawn;
    private final float startTime;  // When this monster starts spawning in the wave
    private float spawnInterval;    // Interval between spawns
    private float ruleClock;
    private boolean firstSpawn;

    public SpawnRule(MonsterType monsterType, int amountPerSpawn, float startTime, float spawnInterval, Variant variant) {
        this.monsterType = monsterType;
        this.amountPerSpawn = amountPerSpawn;
        this.startTime = startTime;
        this.spawnInterval = spawnInterval;
        this.ruleClock = 0;
        this.firstSpawn = true;
        this.variant = variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Variant getVariant() {
        return variant;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public int getAmountPerSpawn() {
        return amountPerSpawn;
    }

    public float getStartTime() {
        return startTime;
    }

    public float getSpawnInterval() {
        return spawnInterval;
    }

    public void setSpawnInterval(float spawnInterval) {
        this.spawnInterval = spawnInterval;
    }

    public float getRuleClock() {
        return ruleClock;
    }

    public void setRuleClock(float ruleClock) {
        this.ruleClock = ruleClock;
    }

    public boolean isFirstSpawn() {
        return firstSpawn;
    }

    public void setFirstSpawn(boolean firstSpawn) {
        this.firstSpawn = firstSpawn;
    }

    public void setAmountPerSpawn(int amountPerSpawn) {
        this.amountPerSpawn = amountPerSpawn;
    }
}
