package me.BRZeph.core.Managers;

import me.BRZeph.entities.monster.MonsterType;

public class CurrencyManager {
    private float gold;     // CurrencyType = 0.
    private float essence;  // CurrencyType = 1.
    private float momentum; // CurrencyType = 2.

    public CurrencyManager() {
        this.gold = 0;
        this.essence = 0;
        this.momentum = 0;
    }

    public void addGold(float amount) {
        gold += amount;
    }

    public void addEssence(float amount) {
        essence += amount;
    }

    public void addMomentum(float amount) {
        momentum += amount;
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

    public boolean spendGold(float amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    public boolean spendEssence(float amount) {
        if (essence >= amount) {
            essence -= amount;
            return true;
        }
        return false;
    }

    public boolean spendMomentum(float amount) {
        if (momentum >= amount) {
            momentum -= amount;
            return true;
        }
        return false;
    }

    public void addMonsterLoot(MonsterType type){
        addGold(type.getGoldLoot());
        addEssence(type.getEssenceLoot());
        addMomentum(type.getMomentumLoot());
    }
}

