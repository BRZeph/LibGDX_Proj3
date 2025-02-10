package me.BRZeph.entities.Towers.UpgradingSystem;

import me.BRZeph.core.CurrencyManager;
import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.Towers.TowerType;

import static me.BRZeph.entities.Towers.UpgradingSystem.TowerUpgradeList.NeutralTowers.ArcherUpgradeList.*;
import static me.BRZeph.entities.Towers.UpgradingSystem.TowerUpgradeList.NeutralTowers.CannonUpgradeList.*;
import static me.BRZeph.entities.Towers.UpgradingSystem.TowerUpgradeList.NeutralTowers.LightningUpgradeList.*;
import static me.BRZeph.utils.Constants.Constants.TowerUpgradeValues.MAIN_PATH_UPGRADE_LIMIT;
import static me.BRZeph.utils.Constants.Constants.TowerUpgradeValues.SECONDARY_PATH_UPGRADE_LIMIT;

public class TowerUpgradeManager {
    private CurrencyManager currencyManager;

    public TowerUpgradeManager(CurrencyManager currencyManager) {
        this.currencyManager = currencyManager;
    }

    public boolean canAffordUpgrade(TowerUpgrade upgrade) {
        return currencyManager.getGold() >= upgrade.getGoldCost() &&
            currencyManager.getEssence() >= upgrade.getEssenceCost() &&
            currencyManager.getMomentum() >= upgrade.getMomentumCost();
    }

    public void applyUpgradeToTower(Tower tower, TowerUpgrade upgrade) { // use canUpgrade and canAfford before this.
        currencyManager.spendGold(upgrade.getGoldCost());
        currencyManager.spendEssence(upgrade.getEssenceCost());
        currencyManager.spendMomentum(upgrade.getMomentumCost());
        upgrade.applyUpgrade(tower);
    }

    public boolean canUpgrade(Tower tower, int path) { // check if tower [tower] can upgrade path [path]
//        int upgradesApplied = tower.getUpgradesAppliedForPath(path);
//        int limit = (path == 1) ? tower.getUpgradeSystem().getMainPathMaxUpgrades() : tower.getUpgradeSystem().getSecPathMaxUpgrades();
//        return upgradesApplied >= limit;
        /*
        case 1: tower is already tier 4 in the path [path], return true. // finished primary path.
        case 2: tower is already tier 3+ in a path ~ [path] and [path] EQ 2, return true. // finished secondary path.
        case 3: tower is already tier 3+ in a path ~ [path] and the other path is already tier 1. // finished primary path AND chosen secondary path.
         */
        tower.recalculatePaths();

        if (tower.getChosenPaths()[0] != path && tower.getChosenPaths()[1] != path
        &&  tower.getChosenPaths()[0] != -1   && tower.getChosenPaths()[1] != -1){ // It initializes with -1.
            return false; // Already chosen both path's and this is the last one.
        }

        int currentTier = tower.getTier(path);

        if (tower.getMainPath() == -1){ // Main path not chosen yet, can upgrade.
            return true;
        } else if (tower.getMainPath() == path){ // This is the main path, check if can upgrade.
            if (currentTier != MAIN_PATH_UPGRADE_LIMIT){
                return true;
            } else {
                return false;
            }
        } else { // This is the secondary path, check if path can be upgraded.
            if (currentTier < SECONDARY_PATH_UPGRADE_LIMIT){ // not yet reached the limit of secondary path, allow it.
                return true;
            } else { // Reached limit of secondary path, upgrade not allowed.
                return false;
            }
        }
    }

    public TowerUpgrade getUpgrade(Tower selectedTower, int path) {
        TowerType type = selectedTower.getType();
        int newTier = selectedTower.getTier(path) + 1;
        if (newTier == 5){
            return null;
            // When it gets at this point, it stops running this method due to the check of canUpgrade.
        }
        TowerUpgrade upgrade;
        switch (type){
            case ARCHER:
                switch (path){
                    case 1:
                        upgrade = ARCHER_PATH_1.get(newTier - 1);
                        break;
                    case 2:
                        upgrade = ARCHER_PATH_2.get(newTier - 1);
                        break;
                    case 3:
                        upgrade = ARCHER_PATH_3.get(newTier - 1);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal path: " + path);
                }
                return upgrade;
            case CANNON:
                switch (path){
                    case 1:
                        upgrade = CANNON_PATH_1.get(newTier - 1);
                        break;
                    case 2:
                        upgrade = CANNON_PATH_2.get(newTier - 1);
                        break;
                    case 3:
                        upgrade = CANNON_PATH_3.get(newTier - 1);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal path: " + path);
                }
                return upgrade;
            case LIGHTNING:
                switch (path){
                    case 1:
                        upgrade = LIGHTNING_PATH_1.get(newTier - 1);
                        break;
                    case 2:
                        upgrade = LIGHTNING_PATH_2.get(newTier - 1);
                        break;
                    case 3:
                        upgrade = LIGHTNING_PATH_3.get(newTier - 1);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal path: " + path);
                }
                return upgrade;
            default:
                throw new IllegalArgumentException("Unregistered tower type: " + type);
        }
    }
}
