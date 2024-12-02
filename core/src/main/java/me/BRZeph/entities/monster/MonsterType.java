package me.BRZeph.entities.monster;

import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.Main;

import static me.BRZeph.utils.Constants.AssetsMonsters.*;
import static me.BRZeph.utils.Constants.Paths.Animations.*;
import static me.BRZeph.utils.Constants.Paths.MonstersTexturesPath.*;

public enum MonsterType {
    ZOMBIE (Main.getAssetManager().get(ZOMBIE_TEXTURE),
        ZOMBIE_WIDTH, ZOMBIE_HEIGHT, ZOMBIE_SPEED, ZOMBIE_NEXUS_DMG, ZOMBIE_HEALTH,
        ZOMBIE_GOLD_LOOT, ZOMBIE_ESSENCE_LOOT, ZOMBIE_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, ZOMBIE_WALK_ANIMATION_NAME
    ),
    SKELETON (Main.getAssetManager().get(SKELETON_TEXTURE),
        SKELETON_WIDTH, SKELETON_HEIGHT, SKELETON_SPEED, SKELETON_NEXUS_DMG, SKELETON_HEALTH,
        SKELETON_GOLD_LOOT, SKELETON_ESSENCE_LOOT, SKELETON_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, SKELETON_WALK_ANIMATION_NAME
    );

    public final Texture texture;
    public float width;
    public float height;
    public float speed;
    public float nexusDmg;
    public float maxHealth;
    public float currentHealth;
    public float goldLoot;
    public float essenceLoot;
    public float momentumLoot;
    private String animationPath;
    private String walkAnimationName;

    MonsterType(Texture texture, float width, float height, float speed, int nexusDmg, float maxHealth,
                float goldLoot, float essenceLoot, float momentumLoot,
                String walkAnimationPath, String walkAnimationName) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.nexusDmg = nexusDmg;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.goldLoot = goldLoot;
        this.essenceLoot = essenceLoot;
        this.momentumLoot = momentumLoot;
        this.animationPath = walkAnimationPath;
        this.walkAnimationName = walkAnimationName;
    }

    public String getAnimationPath() {
        return animationPath;
    }

    public String getWalkAnimationName() {
        return walkAnimationName;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setNexusDmg(int nexusDmg) {
        this.nexusDmg = nexusDmg;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getNexusDmg() {
        return nexusDmg;
    }

    public void setNexusDmg(float nexusDmg) {
        this.nexusDmg = nexusDmg;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    public float getGoldLoot() {
        return goldLoot;
    }

    public float getEssenceLoot() {
        return essenceLoot;
    }

    public float getMomentumLoot() {
        return momentumLoot;
    }
}
