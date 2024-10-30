package me.BRZeph.entities.Towers;

import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.Monster;
import me.BRZeph.utils.enums.TowerType;

import java.util.ArrayList;
import java.util.List;

public class TowerManager {
    private List<PlacedTower> towers; //consider removing this list

    public TowerManager() {
        this.towers = new ArrayList<>();
    }

    public void placeTower(TileMap tileMap, TileType tileType, TowerType towerType, int x, int y) {
        tileMap.changeTile(x, y, tileType);
        towers.add(new PlacedTower(towerType, x, y));
    }

    public void removeTower(TileMap tileMap, int x, int y){
        tileMap.getMap()[x][y].setOriginalTileType();
    }

    public void buyTower(){

    }

    public void update(float delta) {

    }

    public static int getPrice(TowerType type) {
        return 0;
    }

    public static float getTowerRange(TowerType type) {
        return 0;
    }

    public static float getTowerDamage(TowerType type) {
        return 0;
    }

    public static float getTowerCooldown(TowerType type) {
        return 0;
    }

    public List<PlacedTower> getTowers() {
        return towers;
    }
}
