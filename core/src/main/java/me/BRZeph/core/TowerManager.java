package me.BRZeph.core;

import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.Towers.PlacedTower;
import me.BRZeph.entities.Towers.TowerItem;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.utils.GlobalUtils;

import java.util.ArrayList;
import java.util.List;

public class TowerManager {
    private List<PlacedTower> towers; //consider removing this list
    private PlacedTower selectedTower;

    public TowerManager() {
        this.towers = new ArrayList<>();
        this.selectedTower = null;
    }

    public void placeTower(TileMap tileMap, TileType tileType, TowerType towerType, int tileX, int tileY) {
        tileMap.changeTile(tileX, tileY, tileType);
        towers.add(new PlacedTower(towerType, tileX, tileY));
    }

    public void removeTower(TileMap tileMap, int x, int y){
        tileMap.getMap()[x][y].setOriginalTileType();
    }

    public boolean canBuyTower(CurrencyManager currencyManager, TowerItem towerItem){
        if (towerItem.canBeBought(currencyManager)){
            return true;
        }
        return false;
    }
    public void buyItem(CurrencyManager currencyManager, TowerItem towerItem){
        currencyManager.spendGold(towerItem.getGoldPrice());
        currencyManager.spendEssence(towerItem.getEssencePrice());
        currencyManager.spendMomentum(towerItem.getMomentumPrice());
    }

    public void update(float delta) {

    }

    public List<PlacedTower> getTowers() {
        return towers;
    }

    public PlacedTower getSelectedTower() {
        return selectedTower;
    }

    public void setSelectedTower(PlacedTower selectedTower) {
        this.selectedTower = selectedTower;
    }
}
