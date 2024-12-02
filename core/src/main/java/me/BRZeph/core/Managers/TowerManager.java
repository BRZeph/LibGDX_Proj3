package me.BRZeph.core.Managers;

import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.Towers.PlacedTower.ArcherTower;
import me.BRZeph.entities.Towers.PlacedTower.CannonTower;
import me.BRZeph.entities.Towers.PlacedTower.LightningTower;
import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.Towers.TowerItem;
import me.BRZeph.entities.Towers.TowerType;

import java.util.ArrayList;
import java.util.List;

public class TowerManager {
    private List<Tower> towers; //consider removing this list
    private Tower selectedTower;

    public TowerManager() {
        this.towers = new ArrayList<>();
        this.selectedTower = null;
    }

    public void placeTower(TileMap tileMap, TileType tileType, TowerType towerType, int tileX, int tileY) {
        tileMap.changeTile(tileX, tileY, tileType);
        switch (towerType){
            case ARCHER:
                towers.add(new ArcherTower(towerType, tileX, tileY));
                break;
            case CANNON:
                towers.add(new CannonTower(towerType, tileX, tileY));
                break;
            case LIGHTNING:
                towers.add(new LightningTower(towerType, tileX, tileY));
                break;
            default:
                throw new IllegalArgumentException("Unregistered tower type -> " + towerType);
        }
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

    public List<Tower> getTowers() {
        return towers;
    }

    public Tower getSelectedTower() {
        return selectedTower;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }
}
