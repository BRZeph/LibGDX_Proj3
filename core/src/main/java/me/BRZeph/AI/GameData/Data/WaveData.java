package me.BRZeph.AI.GameData.Data;

import me.BRZeph.entities.monster.MonsterType;

import java.util.HashMap;

public class WaveData {
    private int waveNumber;
    private HashMap<MonsterType, Integer> monstersSpawned = new HashMap<>();
    private HashMap<MonsterType, RewardData> rewardData = new HashMap<>();
    private RewardData totalRewards = new RewardData();

    public WaveData(int waveNumber) {
        this.waveNumber = waveNumber;
    }

    public void addMonstersSpawned(MonsterType type, int count) {
        monstersSpawned.put(type, count);

        // Calculate rewards for the specific monster type
        float goldReward = type.getGoldLoot() * count;
        float essenceReward = type.getEssenceLoot() * count;
        float momentumReward = type.getMomentumLoot() * count;

        // Update reward data for the monster type
        rewardData.computeIfAbsent(type, k -> new RewardData())
            .addRewards(goldReward, essenceReward, momentumReward);

        // Update total rewards for the wave
        totalRewards.addRewards(goldReward, essenceReward, momentumReward);
    }

    public HashMap<MonsterType, Integer> getMonstersSpawned() {
        return monstersSpawned;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public HashMap<MonsterType, RewardData> getRewardData() {
        return rewardData;
    }

    public RewardData getTotalRewards() {
        return totalRewards;
    }

    public static class RewardData {
        private float gold;
        private float essence;
        private float momentum;

        public void addRewards(float gold, float essence, float momentum) {
            this.gold += gold;
            this.essence += essence;
            this.momentum += momentum;
        }

        public float getGold() {
            return gold;
        }

        public float getEssence() {
            return essence;
        }

        public float getMomentum() {
            return momentum;
        }

    }
}

