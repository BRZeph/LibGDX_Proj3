package me.BRZeph.entities.currency;

import me.BRZeph.utils.enums.MonsterType;

public class CurrencyManager {
    private float gold;
    private float essence;
    private float momentum;

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

    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    public boolean spendEssence(int amount) {
        if (essence >= amount) {
            essence -= amount;
            return true;
        }
        return false;
    }

    public boolean spendMomentum(int amount) {
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

