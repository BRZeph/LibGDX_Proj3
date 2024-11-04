package me.BRZeph.entities.Towers;

import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.Main;
import me.BRZeph.utils.Constants;

public enum TowerType {
    ARCHER(
        Constants.Values.TowerValues.ARCHER_TOWER_PRICE_GOLD, Constants.Values.TowerValues.ARCHER_TOWER_PRICE_ESSENCE,
        Constants.Values.TowerValues.ARCHER_TOWER_PRICE_MOMENTUM, Constants.Values.TowerValues.ARCHER_TOWER_DAMAGE,
        Constants.Values.TowerValues.ARCHER_TOWER_RANGE, Constants.Values.TowerValues.ARCHER_TOWER_ATTACK_COOLDOWN,
        Constants.Values.TowerValues.ARCHER_TOWER_IS_AOE,
        Main.getAssetManager().get(Constants.Paths.TowersTexturesPath.ARCHER_TOWER_PLACED),
        Main.getAssetManager().get(Constants.Paths.TowersTexturesPath.ARCHER_TOWER_ITEM),
        Main.getAssetManager().get(Constants.Paths.TowersTexturesPath.ARCHER_TOWER_PROJECTILE)
    );

    private final float goldCost, essenceCost, momentumCost, damage, range, attackCooldown;
    private final boolean aoe;
    private final Texture itemTexture;
    private Texture placedTexture, projectileTexture;
    //when using discounts, apply them in the towerItem.class, not here, prices should be final

    TowerType(float goldCost, float essenceCost, float momentumCost,
              float damage, float range, float attackCooldown,
              boolean aoe, Texture placedTexture, Texture itemTexture, Texture projectileTexture) {
        this.goldCost = goldCost;
        this.essenceCost = essenceCost;
        this.momentumCost = momentumCost;
        this.damage = damage;
        this.range = range;
        this.attackCooldown = attackCooldown;
        this.aoe = aoe;
        this.placedTexture = placedTexture;
        this.itemTexture = itemTexture;
        this.projectileTexture = projectileTexture;
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
}
