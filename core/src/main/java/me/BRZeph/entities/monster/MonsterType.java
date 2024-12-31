package me.BRZeph.entities.monster;

import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.TowerDefenseGame;

import static me.BRZeph.utils.Constants.Paths.Animations.*;
import static me.BRZeph.utils.Constants.Paths.MonstersTexturesPath.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.AbyssalMatronValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.ChronoStalkerValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.DemonicImpValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.DoomHeraldValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.HellfireBruteValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.InfernalJuggernautValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.SkeletonValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.SoulReaperValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.TemporalShadeValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.VoidWraithValues.*;
import static me.BRZeph.utils.Constants.Paths.MonstersValues.ZombieValues.*;

public enum MonsterType {
    ZOMBIE(TowerDefenseGame.getAssetManager().get(ZOMBIE_TEXTURE),
        ZOMBIE_WIDTH, ZOMBIE_HEIGHT, ZOMBIE_SPEED, ZOMBIE_NEXUS_DMG,
        ZOMBIE_HEALTH, ZOMBIE_GOLD_LOOT, ZOMBIE_ESSENCE_LOOT, ZOMBIE_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, ZOMBIE_WALK_ANIMATION_NAME
    ),
    SKELETON(TowerDefenseGame.getAssetManager().get(SKELETON_TEXTURE),
        SKELETON_WIDTH, SKELETON_HEIGHT, SKELETON_SPEED, SKELETON_NEXUS_DMG,
        SKELETON_HEALTH, SKELETON_GOLD_LOOT, SKELETON_ESSENCE_LOOT, SKELETON_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, SKELETON_WALK_ANIMATION_NAME
    ),
    DEMONIC_IMP(TowerDefenseGame.getAssetManager().get(DEMONIC_IMP_TEXTURE),
        DEMONIC_IMP_WIDTH, DEMONIC_IMP_HEIGHT, DEMONIC_IMP_SPEED, DEMONIC_IMP_NEXUS_DMG,
        DEMONIC_IMP_HEALTH, DEMONIC_IMP_GOLD_LOOT, DEMONIC_IMP_ESSENCE_LOOT, DEMONIC_IMP_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, DEMONIC_IMP_WALK_ANIMATION_NAME
    ),
    HELLFIRE_BRUTE(TowerDefenseGame.getAssetManager().get(HELLFIRE_BRUTE_TEXTURE),
        HELLFIRE_BRUTE_WIDTH, HELLFIRE_BRUTE_HEIGHT, HELLFIRE_BRUTE_SPEED, HELLFIRE_BRUTE_NEXUS_DMG,
        HELLFIRE_BRUTE_HEALTH, HELLFIRE_BRUTE_GOLD_LOOT, HELLFIRE_BRUTE_ESSENCE_LOOT, HELLFIRE_BRUTE_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, HELLFIRE_BRUTE_WALK_ANIMATION_NAME
    ),
    SOUL_REAPER(TowerDefenseGame.getAssetManager().get(SOUL_REAPER_TEXTURE),
        SOUL_REAPER_WIDTH, SOUL_REAPER_HEIGHT, SOUL_REAPER_SPEED, SOUL_REAPER_NEXUS_DMG,
        SOUL_REAPER_HEALTH, SOUL_REAPER_GOLD_LOOT, SOUL_REAPER_ESSENCE_LOOT, SOUL_REAPER_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, SOUL_REAPER_WALK_ANIMATION_NAME
    ),
    DOOM_HERALD(TowerDefenseGame.getAssetManager().get(DOOM_HERALD_TEXTURE),
        DOOM_HERALD_WIDTH, DOOM_HERALD_HEIGHT, DOOM_HERALD_SPEED, DOOM_HERALD_NEXUS_DMG,
        DOOM_HERALD_HEALTH, DOOM_HERALD_GOLD_LOOT, DOOM_HERALD_ESSENCE_LOOT, DOOM_HERALD_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, DOOM_HERALD_WALK_ANIMATION_NAME
    ),
    VOID_WRAITH(TowerDefenseGame.getAssetManager().get(VOID_WRAITH_TEXTURE),
        VOID_WRAITH_WIDTH, VOID_WRAITH_HEIGHT, VOID_WRAITH_SPEED, VOID_WRAITH_NEXUS_DMG,
        VOID_WRAITH_HEALTH, VOID_WRAITH_GOLD_LOOT, VOID_WRAITH_ESSENCE_LOOT, VOID_WRAITH_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, VOID_WRAITH_WALK_ANIMATION_NAME
    ),
    CHRONO_STALKER(TowerDefenseGame.getAssetManager().get(CHRONO_STALKER_TEXTURE),
        CHRONO_STALKER_WIDTH, CHRONO_STALKER_HEIGHT, CHRONO_STALKER_SPEED, CHRONO_STALKER_NEXUS_DMG,
        CHRONO_STALKER_HEALTH, CHRONO_STALKER_GOLD_LOOT, CHRONO_STALKER_ESSENCE_LOOT, CHRONO_STALKER_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, CHRONO_STALKER_WALK_ANIMATION_NAME
    ),
    INFERNAL_JUGGERNAUT(TowerDefenseGame.getAssetManager().get(INFERNAL_JUGGERNAUT_TEXTURE),
        INFERNAL_JUGGERNAUT_WIDTH, INFERNAL_JUGGERNAUT_HEIGHT, INFERNAL_JUGGERNAUT_SPEED, INFERNAL_JUGGERNAUT_NEXUS_DMG,
        INFERNAL_JUGGERNAUT_HEALTH, INFERNAL_JUGGERNAUT_GOLD_LOOT, INFERNAL_JUGGERNAUT_ESSENCE_LOOT, INFERNAL_JUGGERNAUT_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, INFERNAL_JUGGERNAUT_WALK_ANIMATION_NAME
    ),
    TEMPORAL_SHADE(TowerDefenseGame.getAssetManager().get(TEMPORAL_SHADE_TEXTURE),
        TEMPORAL_SHADE_WIDTH, TEMPORAL_SHADE_HEIGHT, TEMPORAL_SHADE_SPEED, TEMPORAL_SHADE_NEXUS_DMG,
        TEMPORAL_SHADE_HEALTH, TEMPORAL_SHADE_GOLD_LOOT, TEMPORAL_SHADE_ESSENCE_LOOT, TEMPORAL_SHADE_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, TEMPORAL_SHADE_WALK_ANIMATION_NAME
    ),
    ABYSSAL_MATRON(TowerDefenseGame.getAssetManager().get(ABYSSAL_MATRON_TEXTURE),
        ABYSSAL_MATRON_WIDTH, ABYSSAL_MATRON_HEIGHT, ABYSSAL_MATRON_SPEED, ABYSSAL_MATRON_NEXUS_DMG,
        ABYSSAL_MATRON_HEALTH, ABYSSAL_MATRON_GOLD_LOOT, ABYSSAL_MATRON_ESSENCE_LOOT, ABYSSAL_MATRON_MOMENTUM_LOOT,
        MONSTER_WALK_ANIMATION, ABYSSAL_MATRON_WALK_ANIMATION_NAME
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
