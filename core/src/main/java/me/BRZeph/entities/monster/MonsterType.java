package me.BRZeph.entities.monster;

import static me.BRZeph.utils.Constants.Constants.Paths.Animations.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.AbyssalMatronValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.ChronoStalkerValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.DemonicImpValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.DoomHeraldValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.HellfireBruteValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.InfernalJuggernautValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.LesserAbyssalMatronValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.LesserAbyssalMatronValues.LESSER_ABYSSAL_MATRON_SPEED;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.SkeletonValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.SoulReaperValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.TemporalShadeValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.VoidWraithValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.ZombieValues.*;

public enum MonsterType {
    ZOMBIE(
        ZOMBIE_WIDTH, ZOMBIE_HEIGHT, ZOMBIE_SPEED, ZOMBIE_NEXUS_DMG,
        ZOMBIE_HEALTH, ZOMBIE_GOLD_LOOT, ZOMBIE_ESSENCE_LOOT, ZOMBIE_MOMENTUM_LOOT,
        ZOMBIE_WALK_ANIMATION_NAME
    ),
    SKELETON(
        SKELETON_WIDTH, SKELETON_HEIGHT, SKELETON_SPEED, SKELETON_NEXUS_DMG,
        SKELETON_HEALTH, SKELETON_GOLD_LOOT, SKELETON_ESSENCE_LOOT, SKELETON_MOMENTUM_LOOT,
        SKELETON_WALK_ANIMATION_NAME
    ),
    DEMONIC_IMP(
        DEMONIC_IMP_WIDTH, DEMONIC_IMP_HEIGHT, DEMONIC_IMP_SPEED, DEMONIC_IMP_NEXUS_DMG,
        DEMONIC_IMP_HEALTH, DEMONIC_IMP_GOLD_LOOT, DEMONIC_IMP_ESSENCE_LOOT, DEMONIC_IMP_MOMENTUM_LOOT,
        DEMONIC_IMP_WALK_ANIMATION_NAME
    ),
    HELLFIRE_BRUTE(
        HELLFIRE_BRUTE_WIDTH, HELLFIRE_BRUTE_HEIGHT, HELLFIRE_BRUTE_SPEED, HELLFIRE_BRUTE_NEXUS_DMG,
        HELLFIRE_BRUTE_HEALTH, HELLFIRE_BRUTE_GOLD_LOOT, HELLFIRE_BRUTE_ESSENCE_LOOT, HELLFIRE_BRUTE_MOMENTUM_LOOT,
        HELLFIRE_BRUTE_WALK_ANIMATION_NAME
    ),
    SOUL_REAPER(
        SOUL_REAPER_WIDTH, SOUL_REAPER_HEIGHT, SOUL_REAPER_SPEED, SOUL_REAPER_NEXUS_DMG,
        SOUL_REAPER_HEALTH, SOUL_REAPER_GOLD_LOOT, SOUL_REAPER_ESSENCE_LOOT, SOUL_REAPER_MOMENTUM_LOOT,
        SOUL_REAPER_WALK_ANIMATION_NAME
    ),
    DOOM_HERALD(
        DOOM_HERALD_WIDTH, DOOM_HERALD_HEIGHT, DOOM_HERALD_SPEED, DOOM_HERALD_NEXUS_DMG,
        DOOM_HERALD_HEALTH, DOOM_HERALD_GOLD_LOOT, DOOM_HERALD_ESSENCE_LOOT, DOOM_HERALD_MOMENTUM_LOOT,
        DOOM_HERALD_WALK_ANIMATION_NAME
    ),
    VOID_WRAITH(
        VOID_WRAITH_WIDTH, VOID_WRAITH_HEIGHT, VOID_WRAITH_SPEED, VOID_WRAITH_NEXUS_DMG,
        VOID_WRAITH_HEALTH, VOID_WRAITH_GOLD_LOOT, VOID_WRAITH_ESSENCE_LOOT, VOID_WRAITH_MOMENTUM_LOOT,
        VOID_WRAITH_WALK_ANIMATION_NAME
    ),
    CHRONO_STALKER(
        CHRONO_STALKER_WIDTH, CHRONO_STALKER_HEIGHT, CHRONO_STALKER_SPEED, CHRONO_STALKER_NEXUS_DMG,
        CHRONO_STALKER_HEALTH, CHRONO_STALKER_GOLD_LOOT, CHRONO_STALKER_ESSENCE_LOOT, CHRONO_STALKER_MOMENTUM_LOOT,
        CHRONO_STALKER_WALK_ANIMATION_NAME
    ),
    INFERNAL_JUGGERNAUT(
        INFERNAL_JUGGERNAUT_WIDTH, INFERNAL_JUGGERNAUT_HEIGHT, INFERNAL_JUGGERNAUT_SPEED, INFERNAL_JUGGERNAUT_NEXUS_DMG,
        INFERNAL_JUGGERNAUT_HEALTH, INFERNAL_JUGGERNAUT_GOLD_LOOT, INFERNAL_JUGGERNAUT_ESSENCE_LOOT, INFERNAL_JUGGERNAUT_MOMENTUM_LOOT,
        INFERNAL_JUGGERNAUT_WALK_ANIMATION_NAME
    ),
    TEMPORAL_SHADE(
        TEMPORAL_SHADE_WIDTH, TEMPORAL_SHADE_HEIGHT, TEMPORAL_SHADE_SPEED, TEMPORAL_SHADE_NEXUS_DMG,
        TEMPORAL_SHADE_HEALTH, TEMPORAL_SHADE_GOLD_LOOT, TEMPORAL_SHADE_ESSENCE_LOOT, TEMPORAL_SHADE_MOMENTUM_LOOT,
        TEMPORAL_SHADE_WALK_ANIMATION_NAME
    ),
    ABYSSAL_MATRON(
        ABYSSAL_MATRON_WIDTH, ABYSSAL_MATRON_HEIGHT, ABYSSAL_MATRON_SPEED, ABYSSAL_MATRON_NEXUS_DMG,
        ABYSSAL_MATRON_HEALTH, ABYSSAL_MATRON_GOLD_LOOT, ABYSSAL_MATRON_ESSENCE_LOOT, ABYSSAL_MATRON_MOMENTUM_LOOT,
        ABYSSAL_MATRON_WALK_ANIMATION_NAME
    ),
    LESSER_ABYSSAL_MATRON(
        LESSER_ABYSSAL_MATRON_WIDTH, LESSER_ABYSSAL_MATRON_HEIGHT, LESSER_ABYSSAL_MATRON_SPEED, LESSER_ABYSSAL_MATRON_NEXUS_DMG,
        LESSER_ABYSSAL_MATRON_HEALTH, LESSER_ABYSSAL_MATRON_GOLD_LOOT, LESSER_ABYSSAL_MATRON_ESSENCE_LOOT, LESSER_ABYSSAL_MATRON_MOMENTUM_LOOT,
        LESSER_ABYSSAL_MATRON_WALK_ANIMATION_NAME
    );

    public float width;
    public float height;
    public float speed;
    public float nexusDmg;
    public float maxHealth;
    public float currentHealth;
    public float goldLoot;
    public float essenceLoot;
    public float momentumLoot;
    private String walkAnimationName;

    MonsterType(float width, float height, float speed, int nexusDmg, float maxHealth,
                float goldLoot, float essenceLoot, float momentumLoot,
                String walkAnimationName) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.nexusDmg = nexusDmg;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.goldLoot = goldLoot;
        this.essenceLoot = essenceLoot;
        this.momentumLoot = momentumLoot;
        this.walkAnimationName = walkAnimationName;
    }

    public String getWalkAnimationName() {
        return walkAnimationName;
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
