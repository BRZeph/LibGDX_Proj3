package me.BRZeph.entities.WaveSystem;

import me.BRZeph.entities.monster.MonsterType;

public class SpawnRule {
    private final MonsterType monsterType;
    private int amountPerSpawn;
    private final float startTime;  // When this monster starts spawning in the wave
    private float spawnInterval;    // Interval between spawns
    private float ruleClock;
    private boolean firstSpawn;

    public SpawnRule(MonsterType monsterType, int amountPerSpawn, float startTime, float spawnInterval) {
        this.monsterType = monsterType;
        this.amountPerSpawn = amountPerSpawn;
        this.startTime = startTime;
        this.spawnInterval = spawnInterval;
        this.ruleClock = 0;
        this.firstSpawn = true;
    }

    // Getters
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

    // Setter for spawn interval to dynamically change during gameplay
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
