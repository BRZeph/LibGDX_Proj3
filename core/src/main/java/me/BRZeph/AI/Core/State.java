package me.BRZeph.AI.Core;

import me.BRZeph.core.Managers.CurrencyManager;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Towers.PlacedTower.Tower;

import java.util.List;

public class State {
    private List<Tower> towers;
    private float gold;
    private float essence;
    private float momentum;
    private int currentWave;
    private float health;
    private CurrencyManager currencyManager;
    private TileMap tileMap;

    public State(List<Tower> towers, float gold, float essence, float momentum, int currentWave, float health, TileMap tileMap) {
        this.towers = towers;
        this.gold = gold;
        this.essence = essence;
        this.momentum = momentum;
        this.currentWave = currentWave;
        this.health = health;
        this.currencyManager = new CurrencyManager();
        currencyManager.addGold(gold);
        currencyManager.addEssence(essence);
        currencyManager.addMomentum(momentum);
        this.tileMap = tileMap;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    // Getter methods for all fields
    public List<Tower> getTowers() {
        return towers;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
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

    public int getCurrentWave() {
        return currentWave;
    }

    public float getHealth() {
        return health;
    }

    public CurrencyManager getCurrencyManager() {
        return currencyManager;
    }
}
