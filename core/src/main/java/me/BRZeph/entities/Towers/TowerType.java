package me.BRZeph.entities.Towers;

import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.TowerDefenseGame;

import static me.BRZeph.utils.Constants.Constants.Paths.TowersTexturesPath.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.ArcherTowerValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.CannonTowerValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.LightningTowerValues.*;

public enum TowerType {
    ARCHER(
        ARCHER_TOWER_PRICE_GOLD, ARCHER_TOWER_PRICE_ESSENCE, ARCHER_TOWER_PRICE_MOMENTUM,
        ARCHER_TOWER_DAMAGE, ARCHER_TOWER_RANGE, ARCHER_TOWER_ATTACK_COOLDOWN,
        ARCHER_TOWER_IS_AOE, ARCHER_TOWER_AOE_RANGE, ARCHER_TOWER_AOE_DAMAGE,
        TowerDefenseGame.getAssetManager().get(ARCHER_TOWER_PLACED),
        TowerDefenseGame.getAssetManager().get(ARCHER_TOWER_ITEM),
        TowerDefenseGame.getAssetManager().get(ARCHER_TOWER_PROJECTILE)
    ),
    CANNON(
        CANNON_TOWER_PRICE_GOLD, CANNON_TOWER_PRICE_ESSENCE, CANNON_TOWER_PRICE_MOMENTUM,
        CANNON_TOWER_DAMAGE, CANNON_TOWER_RANGE, CANNON_TOWER_ATTACK_COOLDOWN,
        CANNON_TOWER_IS_AOE, CANNON_TOWER_AOE_RANGE, CANNON_TOWER_AOE_DAMAGE,
        TowerDefenseGame.getAssetManager().get(CANNON_TOWER_PLACED),
        TowerDefenseGame.getAssetManager().get(CANNON_TOWER_ITEM),
        TowerDefenseGame.getAssetManager().get(CANNON_TOWER_PROJECTILE)
    ),
    LIGHTNING(
        LIGHTNING_TOWER_PRICE_GOLD, LIGHTNING_TOWER_PRICE_ESSENCE, LIGHTNING_TOWER_PRICE_MOMENTUM,
        LIGHTNING_TOWER_DAMAGE, LIGHTNING_TOWER_RANGE, LIGHTNING_TOWER_ATTACK_COOLDOWN,
        LIGHTNING_TOWER_IS_AOE, LIGHTNING_TOWER_AOE_RANGE, LIGHTNING_TOWER_AOE_DAMAGE,
        TowerDefenseGame.getAssetManager().get(LIGHTNING_TOWER_PLACED),
        TowerDefenseGame.getAssetManager().get(LIGHTNING_TOWER_ITEM),
        TowerDefenseGame.getAssetManager().get(LIGHTNING_TOWER_PROJECTILE)
    );

    /*
    When creating a new tower, put the prices inside the currencyManager.class class too.
     */



    private final float goldCost, essenceCost, momentumCost, damage, range, attackCooldown, aoeRange, aoeDamage;
    private final boolean aoe;
    private final Texture itemTexture;
    private Texture placedTexture, projectileTexture;
    //when using discounts, apply them in the towerItem.class, not here, prices should be final

    TowerType(float goldCost, float essenceCost, float momentumCost,
              float damage, float range, float attackCooldown,
              boolean aoe, float aoeRange, float aoeDamage,
              Texture placedTexture, Texture itemTexture, Texture projectileTexture) {
        this.goldCost = goldCost;
        this.essenceCost = essenceCost;
        this.momentumCost = momentumCost;
        this.damage = damage;
        this.range = range;
        this.attackCooldown = attackCooldown;
        this.aoe = aoe;
        this.aoeRange = aoeRange;
        this.aoeDamage = aoeDamage;
        this.placedTexture = placedTexture;
        this.itemTexture = itemTexture;
        this.projectileTexture = projectileTexture;
    }

    @Override
    public String toString() {
        String basicInfo = "TowerType{" +
            "damage=" + damage +
            ", range=" + range +
            ", attackCooldown=" + attackCooldown +
            ", aoe=" + aoe +
            ", aoeRange=" + aoeRange +
            ", aoeDamage=" + aoeDamage +
            "}";

        switch (this) {
            case ARCHER:
                return "ARCHER " + basicInfo +
                    ", goldCost=" + goldCost +
                    ", essenceCost=" + essenceCost +
                    ", momentumCost=" + momentumCost;
            case CANNON:
                return "CANNON " + basicInfo +
                    ", goldCost=" + goldCost +
                    ", essenceCost=" + essenceCost +
                    ", momentumCost=" + momentumCost;
            case LIGHTNING:
                return "LIGHTNING " + basicInfo +
                    ", goldCost=" + goldCost +
                    ", essenceCost=" + essenceCost +
                    ", momentumCost=" + momentumCost;
            default:
                return "Unknown TowerType";
        }
    }

    public String getTowerTypeName() {
        return this.toString().split(" ")[0];
    }

    public float getGoldCost() {
        return goldCost;
    }

    public float getEssenceCost() {
        return essenceCost;
    }

    public float getMomentumCost() {
        return momentumCost;
    }

    public float getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }

    public float getAttackCooldown() {
        return attackCooldown;
    }

    public boolean isAoe() {
        return aoe;
    }

    public Texture getPlacedTexture() {
        return placedTexture;
    }

    public Texture getItemTexture() {
        return itemTexture;
    }

    public void setPlacedTexture(Texture placedTexture) {
        this.placedTexture = placedTexture;
    }

    public Texture getProjectileTexture() {
        return projectileTexture;
    }

    public float getAoeRange() {
        return aoeRange;
    }

    public float getAoeDamage() {
        return aoeDamage;
    }
}
