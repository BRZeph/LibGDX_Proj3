package me.BRZeph.core.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import static me.BRZeph.utils.Constants.Constants.Paths.Animations.*;
import static me.BRZeph.utils.Constants.Constants.Paths.UIPath.UI_ATLAS;
import static me.BRZeph.utils.Constants.Constants.Paths.UIPath.UI_ATLAS_REGION_LENGTH;
import static me.BRZeph.utils.Constants.Constants.PlaceHolderValues.PLACE_HOLDER_TEXTURE_PATH;
import static me.BRZeph.utils.Constants.TowerUpgradeConstants.ArcherTowerUpgrades.*;
import static me.BRZeph.utils.Constants.TowerUpgradeConstants.CannonTowerUpgrades.*;
import static me.BRZeph.utils.Constants.TowerUpgradeConstants.LightningTowerUpgrades.*;
import static me.BRZeph.utils.Constants.TowerUpgradeConstants.TOWER_UPGRADE_ATLAS;

public class AdvancedAssetsManager {

    private static final HashMap<String, Animation<TextureRegion>> animations = new HashMap<>();
    private static final HashMap<String, TextureRegion> UITextures = new HashMap<>();

    public static void loadAdvancedAssets(AssetManager assetManager) {
        loadAtlases(assetManager);

        processAnimationsAtlases(assetManager); // Process all animations here.
        ProcessUIAtlases(assetManager); // Process all static textures that are inside atlases here.
    }

    private static void loadAtlases(AssetManager assetManager) {
        assetManager.load(PLAYER_WALK_ANIMATION_ATLAS, Texture.class);
        assetManager.load(MONSTER_WALK_ANIMATION_ATLAS, Texture.class);
        assetManager.load(UI_ATLAS, Texture.class);
        assetManager.load(TOWER_UPGRADE_ATLAS, Texture.class);
        assetManager.finishLoading(); // Load all atlases here
    }

    private static void processAnimationsAtlases(AssetManager assetManager) {
        loadPlayerWalkAnimation(assetManager);
        loadMonstersWalkAnimation(assetManager);
    }

/*
Position of the regions inside the TextureRegion.split():

[0][0]   [0][1]   [0][2]   [0][3]   ... [0][15]
[1][0]   [1][1]   [1][2]   [1][3]   ... [1][15]
[2][0]   [2][1]   [2][2]   [2][3]   ... [2][15]
...
[15][0]  [15][1]  [15][2]  [15][3]  ... [15][15]
 */

    private static void loadMonstersWalkAnimation(AssetManager assetManager) {

        Texture MonsterWalkAtlas = assetManager.get(MONSTER_WALK_ANIMATION_ATLAS, Texture.class);
        TextureRegion[][] regions = TextureRegion.split(MonsterWalkAtlas, 64, 64);

        LoadZombieWalkAnimation(regions);
        LoadSkeletonWalkAnimation(regions);
        loadDemonicImpWalkAnimation(regions);
        loadHellfireBruteWalkAnimation(regions);
        loadSoulReaperWalkAnimation(regions);
        loadDoomHeraldWalkAnimation(regions);
        loadVoidWraithWalkAnimation(regions);
        loadChronoStalkerWalkAnimation(regions);
        loadInfernalJuggernautWalkAnimation(regions);
        loadTemporalShadeWalkAnimation(regions);
        loadAbyssalMatronWalkAnimation(regions);
        loadLesserAbyssalMatronWalkAnimation(regions);
    }

    private static void LoadSkeletonWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> skeletonWalkFrames = new Array<>();
        skeletonWalkFrames.addAll(
            regions[0][0], // Frame 1
            regions[0][1], // Frame 2
            regions[0][2], // Frame 3
            regions[0][3], // Frame 4
            regions[0][4], // Frame 5
            regions[0][5]  // Frame 6
        );
        Animation<TextureRegion> skeletonWalk = new Animation<>(
            SKELETON_WALK_FRAME_TIME, skeletonWalkFrames, Animation.PlayMode.LOOP
        );
        animations.put(SKELETON_WALK_ANIMATION_NAME, skeletonWalk);
    }

    private static void LoadZombieWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> zombieWalkFrames = new Array<>();
        zombieWalkFrames.addAll(
            regions[1][0], // Frame 1
            regions[1][1], // Frame 2
            regions[1][2], // Frame 3
            regions[1][3], // Frame 4
            regions[1][4], // Frame 5
            regions[1][5]  // Frame 6
        );
        Animation<TextureRegion> zombieWalk = new Animation<>(
            ZOMBIE_WALK_FRAME_TIME, zombieWalkFrames, Animation.PlayMode.LOOP
        );
        animations.put(ZOMBIE_WALK_ANIMATION_NAME, zombieWalk);
    }

    private static void loadDemonicImpWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> demonicImpWalkFrames = new Array<>();
        demonicImpWalkFrames.addAll(
            regions[2][0] // Frame 1
        );
        Animation<TextureRegion> demonicImpWalk = new Animation<>(
            DEMONIC_IMP_WALK_FRAME_TIME, demonicImpWalkFrames, Animation.PlayMode.LOOP
        );
        animations.put(DEMONIC_IMP_WALK_ANIMATION_NAME, demonicImpWalk);
    }

    private static void loadHellfireBruteWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> hellfireBruteWalkFrames = new Array<>();
        hellfireBruteWalkFrames.addAll(
            regions[3][0] // Frame 1
        );
        Animation<TextureRegion> hellfireBruteWalk = new Animation<>(
            HELLFIRE_BRUTE_WALK_FRAME_TIME, hellfireBruteWalkFrames, Animation.PlayMode.LOOP
        );
        animations.put(HELLFIRE_BRUTE_WALK_ANIMATION_NAME, hellfireBruteWalk);
    }

    private static void loadSoulReaperWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[4][0] // Frame 1
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            SOUL_REAPER_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(SOUL_REAPER_WALK_ANIMATION_NAME, walkAnimation);
    }

    private static void loadDoomHeraldWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[5][0] // Frame 1
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            DOOM_HERALD_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(DOOM_HERALD_WALK_ANIMATION_NAME, walkAnimation);
    }

    private static void loadVoidWraithWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[6][0] // Frame 1
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            VOID_WRAITH_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(VOID_WRAITH_WALK_ANIMATION_NAME, walkAnimation);
    }

    private static void loadChronoStalkerWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[7][0] // Frame 1
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            CHRONO_STALKER_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(CHRONO_STALKER_WALK_ANIMATION_NAME, walkAnimation);
    }

    private static void loadInfernalJuggernautWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[8][0] // Frame 1
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            INFERNAL_JUGGERNAUT_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(INFERNAL_JUGGERNAUT_WALK_ANIMATION_NAME, walkAnimation);
    }

    private static void loadTemporalShadeWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[9][0] // Frame 1
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            TEMPORAL_SHADE_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(TEMPORAL_SHADE_WALK_ANIMATION_NAME, walkAnimation);
    }

    private static void loadLesserAbyssalMatronWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[10][0] // Frame 1
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            LESSER_ABYSSAL_MATRON_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(LESSER_ABYSSAL_MATRON_WALK_ANIMATION_NAME, walkAnimation);

    }

    private static void loadAbyssalMatronWalkAnimation(TextureRegion[][] regions) {
        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[10][0] // Frame 1
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            ABYSSAL_MATRON_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(ABYSSAL_MATRON_WALK_ANIMATION_NAME, walkAnimation);
    }

    private static void loadPlayerWalkAnimation(AssetManager assetManager) {
        Texture atlasTexture = assetManager.get(PLAYER_WALK_ANIMATION_ATLAS, Texture.class);

        TextureRegion[][] regions = TextureRegion.split(atlasTexture, 64, 64);

        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[0][0], // Frame 1
            regions[0][1], // Frame 2
            regions[0][2], // Frame 3
            regions[0][3], // Frame 4
            regions[0][4], // Frame 5
            regions[0][5], // Frame 6
            regions[0][6], // Frame 7
            regions[0][7], // Frame 8
            regions[0][8], // Frame 9
            regions[0][9], // Frame 10
            regions[0][10], // Frame 11
            regions[0][11], // Frame 12
            regions[0][12], // Frame 13
            regions[0][13], // Frame 14
            regions[0][14], // Frame 15
            regions[0][15]  // Frame 16
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            PLAYER_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(PLAYER_WALK_ANIMATION_NAME, walkAnimation);
    }

    private static void ProcessUIAtlases(AssetManager assetManager) {
        ProcessButtonsTextures(assetManager);
        ProcessTowerUpgradesTextures(assetManager);
    }

    private static void ProcessTowerUpgradesTextures(AssetManager assetManager) {
        Texture atlasTexture = assetManager.get(TOWER_UPGRADE_ATLAS, Texture.class);

        TextureRegion[][] regions = TextureRegion.split(atlasTexture, 64, 64);
        /*
        [tower]_[path]_[tier]_[texture]
        at_p1_t1_bt:
            -> archer tower
            -> path 1
            -> tier 1
            -> buy texture.
         */
        UITextures.put(AT_P1_T1_BUY_TEXTURE_NAME, regions[0][0]);  // Archer upgrades buy textures.
        UITextures.put(AT_P1_T2_BUY_TEXTURE_NAME, regions[0][1]);  // Archer upgrades buy textures.
        UITextures.put(AT_P1_T3_BUY_TEXTURE_NAME, regions[0][2]);  // Archer upgrades buy textures.
        UITextures.put(AT_P1_T4_BUY_TEXTURE_NAME, regions[0][3]);  // Archer upgrades buy textures.
//        UITextures.put(AT_P2_T1_BUY_TEXTURE_NAME, regions[0][4]);  // Archer upgrades buy textures.
//        UITextures.put(AT_P2_T2_BUY_TEXTURE_NAME, regions[0][5]);  // Archer upgrades buy textures.
//        UITextures.put(AT_P2_T3_BUY_TEXTURE_NAME, regions[0][6]);  // Archer upgrades buy textures.
//        UITextures.put(AT_P2_T4_BUY_TEXTURE_NAME, regions[0][7]);  // Archer upgrades buy textures.
//        UITextures.put(AT_P3_T1_BUY_TEXTURE_NAME, regions[0][8]);  // Archer upgrades buy textures.
//        UITextures.put(AT_P3_T2_BUY_TEXTURE_NAME, regions[0][9]);  // Archer upgrades buy textures.
//        UITextures.put(AT_P3_T3_BUY_TEXTURE_NAME, regions[0][10]); // Archer upgrades buy textures.
//        UITextures.put(AT_P3_T4_BUY_TEXTURE_NAME, regions[0][11]); // Archer upgrades buy textures.

        TextureRegion placeHolder = new TextureRegion(assetManager.get(PLACE_HOLDER_TEXTURE_PATH, Texture.class));

        UITextures.put(AT_P2_T1_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures.
        UITextures.put(AT_P2_T2_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures.
        UITextures.put(AT_P2_T3_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures.
        UITextures.put(AT_P2_T4_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures.
        UITextures.put(AT_P3_T1_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures.
        UITextures.put(AT_P3_T2_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures.
        UITextures.put(AT_P3_T3_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures.
        UITextures.put(AT_P3_T4_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures.

        UITextures.put(CT_P1_T1_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][0]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P1_T2_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][1]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P1_T3_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][2]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P1_T4_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][3]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P2_T1_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][4]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P2_T2_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][5]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P2_T3_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][6]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P2_T4_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][7]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P3_T1_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][8]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P3_T2_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][9]);  // Cannon upgrades buy textures.
        UITextures.put(CT_P3_T3_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][10]); // Cannon upgrades buy textures.
        UITextures.put(CT_P3_T4_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[1][11]); // Cannon upgrades buy textures.

        UITextures.put(LT_P1_T1_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][0]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P1_T2_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][1]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P1_T3_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][2]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P1_T4_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][3]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P2_T1_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][4]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P2_T2_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][5]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P2_T3_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][6]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P2_T4_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][7]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P3_T1_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][8]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P3_T2_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][9]);  // Lightning upgrades buy textures.
        UITextures.put(LT_P3_T3_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][10]); // Lightning upgrades buy textures.
        UITextures.put(LT_P3_T4_BUY_TEXTURE_NAME, placeHolder); // Using placeHolder while I don't create new textures. regions[2][11]); // Lightning upgrades buy textures.
    }

    private static void ProcessButtonsTextures(AssetManager assetManager) {
        Texture atlasTexture = assetManager.get(UI_ATLAS, Texture.class);

        TextureRegion[][] regions = TextureRegion.split(atlasTexture, UI_ATLAS_REGION_LENGTH, UI_ATLAS_REGION_LENGTH);

        UITextures.put("btn_speech_bubble_TLC", regions[0][0]);
        UITextures.put("btn_speech_bubble_TRC", regions[0][1]);
        UITextures.put("btn_speech_bubble_UB",  regions[0][2]);
        UITextures.put("btn_speech_bubble_RB",  regions[0][3]);
        UITextures.put("btn_speech_bubble_M",   regions[0][4]);

        UITextures.put("btn_speech_bubble_BLC", regions[1][0]);
        UITextures.put("btn_speech_bubble_BRC", regions[1][1]);
        UITextures.put("btn_speech_bubble_DB",  regions[1][2]);
        UITextures.put("btn_speech_bubble_LB",  regions[1][3]);
    }

    public static Animation<TextureRegion> getAnimation(String animationName) {
        if (!animations.containsKey(animationName)) throw new IllegalArgumentException("Unregistered animation texture");
        return animations.get(animationName);
    }

    public static TextureRegion getUITexture(String UIName){
        if (!UITextures.containsKey(UIName)) return null; //throw new IllegalArgumentException("Unregistered UI texture");
        return UITextures.get(UIName);
    }
}
