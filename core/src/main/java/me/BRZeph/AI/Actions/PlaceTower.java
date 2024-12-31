package me.BRZeph.AI.Actions;

import me.BRZeph.AI.GameEnvironment.GameEnvironment;
import me.BRZeph.AI.Core.Action;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.Towers.TowerType;

import static me.BRZeph.utils.Constants.AIValues.*;
import static me.BRZeph.utils.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Paths.TileValues.TILE_WIDTH;

public class PlaceTower implements Action {
    private Tower lastPlacedTower;

    private TowerType towerType;
    private TileMap tileMap;
    private TileType tileType;
    private int tileX, tileY;
    private float reward;
    private float towerX;
    private float towerY;
    private int wave;

    public PlaceTower(TowerType towerType, TileMap tileMap, TileType tileType, int tileX, int tileY, int wave) {
        this.towerType = towerType;
        this.tileMap = tileMap;
        this.tileType = tileType;
        this.tileX = tileX;
        this.tileY = tileY;
        this.towerX = tileX*TILE_WIDTH;
        this.towerY = tileY*TILE_HEIGHT;
        this.wave = wave;
        if (towerType == TowerType.ARCHER){
            reward = PLACE_ARCHER_TOWER_REWARD;
        } else if (towerType == TowerType.CANNON){
            reward = PLACE_CANNON_TOWER_REWARD;
        } else if (towerType == TowerType.LIGHTNING){
            reward = PLACE_LIGHTNING_TOWER_REWARD;
        } else {
            throw new IllegalArgumentException("Unregistered tower type");
        }
    }

    public Tower getLastPlacedTower(){
        return lastPlacedTower;
    }

    @Override
    public double performAction(GameEnvironment environment) {
        Tower placedTower = environment.getTowerManager().placeTower(tileMap, tileType, towerType, tileX, tileY, wave);
        environment.getTowerManager().buyItem(environment.getCurrencyManager(), towerType);
        this.lastPlacedTower = placedTower;
        return 0; // Delayed reward.
    }

    @Override
    public String toString() {
        return "PlaceTowerAction{" +
            "towerType=" + towerType +
            ", tileX=" + tileX +
            ", tileY=" + tileY +
            ", reward=" + reward +
            '}';
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public float getReward() {
        return reward;
    }

    public float getTowerX() {
        return towerX;
    }

    public float getTowerY() {
        return towerY;
    }

    public int getWave() {
        return wave;
    }
}
