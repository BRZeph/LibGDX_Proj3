package me.BRZeph.entities.Towers.TargetingSystem;

import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.monster.Monster;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;

import java.util.Comparator;
import java.util.List;

import static me.BRZeph.utils.Constants.AssetsTiles.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.AssetsTiles.TILE_WIDTH;

public class ClosestMonsterStrategy implements TargetingStrategy {
    private int towerXPos;
    private int towerYPos;

    @Override
    public Monster selectTarget(List<Monster> monstersInRange) {
        return monstersInRange.stream()
            .filter(monster -> monster.getIncomingDamage() <= monster.getCurrentHealth())
            .min(Comparator.comparing(monster -> monster.getDistanceToPoint(towerXPos, towerYPos)))
            .orElse(null);
    }

    @Override
    public String toString() {
        return "Targetting system -> closestMonsterStrategy";
    }

    private void setTowerXPos(int towerXPos) {
        this.towerXPos = towerXPos;
    }

    private void setTowerYPos(int towerYPos) {
        this.towerYPos = towerYPos;
    }

    public static ClosestMonsterStrategy instantiateClosestMonsterStrategy(Tower tower){
        ClosestMonsterStrategy strategy = new ClosestMonsterStrategy();
        strategy.setTowerXPos((int) ((0.5f + tower.getxPos()) * TILE_WIDTH)); // +0.5f to get the center of the tower.
        strategy.setTowerYPos((int) ((0.5f + tower.getyPos()) * TILE_HEIGHT)); // +0.5f to get the center of the tower.
        return strategy;
    }
}
