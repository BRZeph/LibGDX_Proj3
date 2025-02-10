package me.BRZeph.entities.Towers.Visuals;

import me.BRZeph.core.Assets.AdvancedAssetsManager;
import me.BRZeph.entities.Towers.TowerType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TowerVisualsFactory {

    public static final Map<TowerType, TowerVisuals> towerVisualsMap = new HashMap<>();
    static {
        TowerVisuals archerVisuals = new TowerVisuals();
        /*
        issue: what if path1 tier2 and path1 tier3 both have attack animations? IDK, I don't want to think about this now.
        when implementing the animations think about that ^.
        maybe make a priority parameter as well, or simply do not make a combination of upgrades that can be bought at the same time AND modify the animation.
         */

        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 1, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 1, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 1, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 2, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 2, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 2, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 3, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 3, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 3, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 4, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 4, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 1, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 1, 4, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 1, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 1, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 1, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 2, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 2, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 2, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 3, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 3, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 3, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 4, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 4, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 2, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 2, 4, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 1, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 1, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 1, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 1, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 2, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 2, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 2, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 2, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 3, "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 3, "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 3, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 3, "projectile"))); // projectile animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 4,  "idle"))); // idle animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 4,  "attack"))); // attack animation
        archerVisuals.addAnimation(keyBuilder(TowerType.ARCHER, 3, 4, ""), AdvancedAssetsManager.getAnimation(keyBuilder(TowerType.ARCHER, 3, 4,  "projectile"))); // projectile animation
    }

    public static TowerVisuals getTowerVisuals(TowerType type){
        return towerVisualsMap.getOrDefault(type, null);
    }

    protected static String keyBuilder(TowerType type, int path, int tier, String state){
        if (Objects.equals(state, "")) {
            return type.name() + "_path" + path + "_tier" + tier;
        } else {
            return type.name() + "_path" + path + "_tier" + tier + "_state_" + state;
        }
    }
}
