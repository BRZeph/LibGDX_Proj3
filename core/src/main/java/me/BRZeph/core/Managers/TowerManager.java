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

    public Tower placeTower(TileMap tileMap, TileType tileType, TowerType towerType, int tileX, int tileY, int wave) {
        tileMap.changeTile(tileX, tileY, tileType);
        switch (towerType){
            case ARCHER:
                ArcherTower archerTower = new ArcherTower(towerType, tileX, tileY);
                archerTower.setWavePlaced(wave);
                towers.add(archerTower);
                return archerTower;
            case CANNON:
                CannonTower cannonTower = new CannonTower(towerType, tileX, tileY);
                cannonTower.setWavePlaced(wave);
                towers.add(cannonTower);
                return cannonTower;
            case LIGHTNING:
                LightningTower lightningTower = new LightningTower(towerType, tileX, tileY);
                lightningTower.setWavePlaced(wave);
                towers.add(lightningTower);
                return lightningTower;
            default:
                throw new IllegalArgumentException("Unregistered tower type -> " + towerType);
        }
    }

    public static boolean canBuyTower(CurrencyManager currencyManager, TowerItem towerItem){
        return towerItem.canBeBought(currencyManager);
    }

    public static boolean canBuyTower(CurrencyManager currencyManager, TowerType type){
        return currencyManager.getGold() >= type.getGoldCost() &&
            currencyManager.getEssence() >= type.getEssenceCost() &&
            currencyManager.getMomentum() >= type.getMomentumCost();
    }

    public void buyItem(CurrencyManager currencyManager, TowerItem towerItem){
        currencyManager.spendGold(towerItem.getGoldPrice());
        currencyManager.spendEssence(towerItem.getEssencePrice());
        currencyManager.spendMomentum(towerItem.getMomentumPrice());
    }

    public void buyItem(CurrencyManager currencyManager, TowerType towerType){
        currencyManager.spendGold(towerType.getGoldCost());
        currencyManager.spendEssence(towerType.getEssenceCost());
        currencyManager.spendMomentum(towerType.getMomentumCost());
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
