package me.BRZeph.entities.Towers.PlacedTower;

import me.BRZeph.entities.Towers.TowerType;

import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.ArcherTowerValues.*;

public class ArcherTower extends Tower {

    public ArcherTower(TowerType type, int xPos, int yPos) {
        super(type, xPos, yPos);
    }

    @Override
    public String toString() {
        return "ArcherTower{" +
            "attackRange=" + attackRange +
            ", damage=" + damage +
            ", attackCooldown=" + attackCooldown +
            ", cooldownClock=" + cooldownClock +
            ", xPos=" + xPos +
            ", yPos=" + yPos +
            ", type=" + type +
            ", targetingStrategy=" + targetingStrategy +
            ", currentStrategyIndex=" + currentStrategyIndex +
            ", activeProjectiles=" + activeProjectiles.size() +
            '}';
    }

    @Override
    public void initializeProjectileData() {
        this.projectileSpeed  = ARCHER_TOWER_PROJECTILE_SPEED;
        this.projectileWidth  = ARCHER_TOWER_PROJECTILE_WIDTH;
        this.projectileHeight = ARCHER_TOWER_PROJECTILE_HEIGHT;
    }
}
