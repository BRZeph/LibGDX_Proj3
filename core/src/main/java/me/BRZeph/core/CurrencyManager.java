package me.BRZeph.core;

import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.monster.MonsterType;

import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.ArcherTowerValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.CannonTowerValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.LightningTowerValues.*;

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

    private double getGoldCost(TowerType towerType) {
        switch (towerType) {
            case ARCHER:
                return ARCHER_TOWER_PRICE_GOLD;
            case CANNON:
                return CANNON_TOWER_PRICE_GOLD;
            case LIGHTNING:
                return LIGHTNING_TOWER_PRICE_GOLD;
            default:
                return 0;
        }
    }

    private double getEssenceCost(TowerType towerType) {
        switch (towerType) {
            case ARCHER:
                return ARCHER_TOWER_PRICE_ESSENCE;
            case CANNON:
                return CANNON_TOWER_PRICE_ESSENCE;
            case LIGHTNING:
                return LIGHTNING_TOWER_PRICE_ESSENCE;
            default:
                return 0;
        }
    }

    private double getMomentumCost(TowerType towerType) {
        switch (towerType) {
            case ARCHER:
                return ARCHER_TOWER_PRICE_MOMENTUM;
            case CANNON:
                return CANNON_TOWER_PRICE_MOMENTUM;
            case LIGHTNING:
                return LIGHTNING_TOWER_PRICE_MOMENTUM;
            default:
                return 0;
        }
    }
}

