package me.BRZeph.utils.enums;

import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.Main;
import me.BRZeph.utils.Constants;

public enum MonsterType {
    ZOMBIE  (Main.getAssetManager().get(
        Constants.Paths.MonstersTexturesPath.ZOMBIE_TEXTURE), Constants.AssetsMonsters.ZOMBIE_WIDTH,
        Constants.AssetsMonsters.ZOMBIE_HEIGHT, Constants.AssetsMonsters.ZOMBIE_SPEED, Constants.AssetsMonsters.ZOMBIE_NEXUS_DMG,
        Constants.AssetsMonsters.ZOMBIE_HEALTH
    ),
    SKELETON  (Main.getAssetManager().get(
        Constants.Paths.MonstersTexturesPath.SKELETON_TEXTURE), Constants.AssetsMonsters.SKELETON_WIDTH,
        Constants.AssetsMonsters.SKELETON_HEIGHT, Constants.AssetsMonsters.SKELETON_SPEED, Constants.AssetsMonsters.SKELETON_NEXUS_DMG,
        Constants.AssetsMonsters.SKELETON_HEALTH
    );

    public final Texture texture;
    public float width;
    public float height;
    public float speed;
    public float nexusDmg;
    public float maxHealth;
    public float currentHealth;

    MonsterType(Texture texture, float width, float height, float speed, int nexusDmg, float maxHealth) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.nexusDmg = nexusDmg;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
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
}
