package me.BRZeph.utils;

import me.BRZeph.entities.WaveSystem.SpawnBehavior;
import me.BRZeph.entities.WaveSystem.SpawnRule;
import me.BRZeph.entities.WaveSystem.SpawnRuleEndWave;
import me.BRZeph.entities.WaveSystem.Wave;
import me.BRZeph.utils.enums.MonsterType;
import me.BRZeph.utils.pathFinding.Node;

import java.util.List;
import java.util.Map;

public class Constants {

    // Screen Constants
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 980;
    public static final boolean FULLSCREEN = false;

    // Camera Constants
    public static class CameraValues {
        public static final float CAMERA_INITIAL_ZOOM = 1.0f;
        public static final float CAMERA_MIN_ZOOM = 0.5f;
        public static final float CAMERA_MAX_ZOOM = 2.0f;
        public static final float CAMERA_ZOOM_SPEED = 0.1f;
    }

    public static class Paths {

        public static class ScreensTexturesPath {
            public static final String LEVEL_1_BACKGROUND = "Textures\\Screens\\LevelsScreen\\SpecificLevelAssets\\Level1\\level1_background_specific.png";
            public static final String MAIN_MENU_BACKGROUND = "Textures\\Screens\\MainMenuScreen\\MainMenuScreen_bg.png";
            public static final String LEVEL_SELECTOR_BACKGROUND = "Textures\\Screens\\LevelsScreen\\LevelSelectorScreen_bg.png";
        }

        public static class TowersTexturesPath {
            public static final String ARCHER_TOWER = "Textures\\Towers\\Archer_tower.png";
        }

        public static class MonstersTexturesPath {
            public static final String ZOMBIE_TEXTURE = "Textures\\Enemies\\Zombie.png";
            public static final String SKELETON_TEXTURE = "Textures\\Enemies\\Skeleton.png";
        }

        public static class TilesTexturesPath {
            public static final String GRASS_TILE = "Textures\\Tiles\\Grass.png";
            public static final String WATER_TILE = "Textures\\Tiles\\Water.png";
            public static final String STARTING_POINT = "Textures\\Tiles\\Starting_Tile.png";
            public static final String ENDING_POINT = "Textures\\Tiles\\Ending_Tile.png";
        }

        public static class MapsPath {
            public static final String LEVEL_1_MAP = "Map\\level_1_map.txt";
        }

        public static class UIPath {
            public static final String TOWER_MENU_UI = "Textures\\UI\\tower list for placement UI 001.png";
            public static final String HEART_UI = "Textures\\UI\\Heart 001.png";
            public static final String TOWER_SHOP_BACKGROUND_UI = "Textures\\UI\\towerShop\\tower shop ui back 001.png";
            public static final String TOWER_SHOP_FRONT_UI = "Textures\\UI\\towerShop\\tower shop ui front 001.png";
        }
    }

    public static class Values {

        public static class PlayerValues {
            public static final float LEVEL_1_PLAYER_HEALTH = 10;
        }

//        public static class CurrencyTypes {
//            //this is just a basic template, change it later.
//
//            public static final String FIRST_CURRENCY  = " 0 -> [material 0]";
//            public static final String SECOND_CURRENCY = " 1 -> [material 1]";
//            public static final String THIRD_CURRENCY  = " 2 -> [material 2]";
//            public static final String FORTH_CURRENCY  = " 3 -> [material 3]";
//        }

        public static class TowerValues {
            public static final float ARCHER_TOWER_PRICE = 5;
            public static final int ARCHER_TOWER_PRICE_COIN = 0; // read currencyTypes
            public static final float ARCHER_TOWER_RANGE = 500;
            public static final float ARCHER_TOWER_DAMAGE = 3;
            public static final float ARCHER_TOWER_ATTACK_RATE = 0.4f; //0.4 attacks per second
        }

        public static class UIValues {
            public static final int TOWER_MENU_WIDTH = 240;
            public static final int TOWER_MENU_HEIGHT = 980;
            public static final int FPS_X_POS = 20;
            public static final int FPS_Y_POS_DECREASE = 20;
            public static final int FPS_WIDTH = 50;

            public static final int HEALTH_X_POS = SCREEN_WIDTH/2;
            public static final int HEALTH_Y_POS_DECREASE = 50;
            public static final int HEALTH_WIDTH = 45;
            public static final int HEALTH_FONT_SIZE = 2;
            public static final int HEALTH_HEART_GAP = 8;

            public static final int HEART_WIDTH = 32;
            public static final int HEART_HEIGHT = 32;
            public static final float HEALTH_BAR_WIDTH = 64;
            public static final float HEALTH_BAR_HEIGHT = 8;

            public static final int CURRENT_WAVE_WIDTH = 200;
            public static final int CURRENT_WAVE_HEIGHT_DECREASE = 40;
            public static final int CURRENT_WAVE_CLOCK_HEIGHT_DECREASE = 40;

            public static final int CURRENCY_X_POS_DECREASE = 400;

            public static class ButtonsValues {
                public static final String TEST_BUTTON_TEXTURE_PATH = "Textures\\UI\\Buttons\\test button 001.png";
            }
        }
    }

    // Monster Assets Paths
    public static class AssetsMonsters {
        public static final float ZOMBIE_HEALTH = 8;
        public static final int ZOMBIE_WIDTH = 32;
        public static final int ZOMBIE_HEIGHT = 32;
        public static final int ZOMBIE_SPEED = 100;
        public static final int ZOMBIE_NEXUS_DMG = 1;
        public static final float ZOMBIE_GOLD_LOOT = 3;
        public static final float ZOMBIE_ESSENCE_LOOT = 3;
        public static final float ZOMBIE_MOMENTUM_LOOT = 3;

        public static final float SKELETON_HEALTH = 5;
        public static final int SKELETON_WIDTH = 32;
        public static final int SKELETON_HEIGHT = 32;
        public static final int SKELETON_SPEED = 200;
        public static final int SKELETON_NEXUS_DMG = 2;
        public static final float SKELETON_GOLD_LOOT = 3;
        public static final float SKELETON_ESSENCE_LOOT = 3;
        public static final float SKELETON_MOMENTUM_LOOT = 3;
    }

    // Player Assets Paths and Constants
    public static class AssetsPlayer {
        public static final int PLAYER_WIDTH = 32;
        public static final int PLAYER_HEIGHT = 32;
        public static final int PLAYER_SPEED = 300;
    }

    // Tile Assets Paths and Constants
    public static class AssetsTiles {
        public static final int TILE_WIDTH = 64;
        public static final int TILE_HEIGHT = 64;
    }


    public static class WaveValues {
        public static final int WAVE_1_MAX_WAVE = 10;

        // rules -> basic initial pattern of the wave.
        // behavior -> variance of the rule.
        // every wave need a rule and ruleEndWave.
        public static List<Wave> getLevel1Waves(List<Node> path) {
            return List.of(
                new Wave(getWave1Rules() , getWave1Behavior(), getWave1EndWaveBehavior(), path),
                new Wave(getWave2Rules() , getWave2Behavior(), getWave2EndWaveBehavior(), path),
                new Wave(getWave3Rules() , getWave3Behavior(), getWave3EndWaveBehavior(), path),
                new Wave(getWave4Rules() , getWave4Behavior(), getWave4EndWaveBehavior(), path),
                new Wave(getWave5Rules() , getWave5Behavior(), getWave5EndWaveBehavior(), path),
                new Wave(getWave6Rules() , getWave6Behavior(), getWave6EndWaveBehavior(), path),
                new Wave(getWave7Rules() , getWave7Behavior(), getWave7EndWaveBehavior(), path),
                new Wave(getWave8Rules() , getWave8Behavior(), getWave8EndWaveBehavior(), path),
                new Wave(getWave9Rules() , getWave9Behavior(), getWave9EndWaveBehavior(), path),
                new Wave(getWave10Rules(), getWave10Behavior(), getWave10EndWaveBehavior(), path)
            );
        }

        // Wave 1 Rules
        private static List<SpawnRule> getWave1Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 1, -5f, 5f),
                new SpawnRule(MonsterType.SKELETON, 1, 3f, 5f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave1Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(
                    new SpawnBehavior(5, 10.2f, 21f),
                    new SpawnBehavior(1, 15f, 1f)
                ),
                MonsterType.SKELETON, List.of(
                    new SpawnBehavior(1, 13f, 1f)
                )
            );
        }

        private static List<SpawnRuleEndWave> getWave1EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 20f, 0.1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 20f, 0.1f, 35f)
            );
        }

        // Wave 2 Rules
        private static List<SpawnRule> getWave2Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 1, 0f, 3f),
                new SpawnRule(MonsterType.SKELETON, 1, 2.46f, 5f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave2Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 23f, 2f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 13f, 2.3f))
            );
        }

        private static List<SpawnRuleEndWave> getWave2EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }

        // Wave 3 Rules
        private static List<SpawnRule> getWave3Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 2, 0f, 4f),
                new SpawnRule(MonsterType.SKELETON, 2, 5f, 7f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave3Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 15f, 2.5f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 25f, 3f))
            );
        }

        private static List<SpawnRuleEndWave> getWave3EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }

        // Wave 4 Rules
        private static List<SpawnRule> getWave4Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 3, 0f, 3f),
                new SpawnRule(MonsterType.SKELETON, 2, 2f, 6f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave4Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 18f, 1.5f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 22f, 2.5f))
            );
        }

        private static List<SpawnRuleEndWave> getWave4EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }

        // Wave 5 Rules
        private static List<SpawnRule> getWave5Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 2, 1f, 4f),
                new SpawnRule(MonsterType.SKELETON, 3, 3f, 8f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave5Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 20f, 2f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 27f, 4f))
            );
        }

        private static List<SpawnRuleEndWave> getWave5EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }

        // Wave 6 Rules
        private static List<SpawnRule> getWave6Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 4, 0f, 2f),
                new SpawnRule(MonsterType.SKELETON, 2, 4f, 5f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave6Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 12f, 1f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 18f, 3f))
            );
        }

        private static List<SpawnRuleEndWave> getWave6EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }

        // Wave 7 Rules
        private static List<SpawnRule> getWave7Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 5, 0f, 3f),
                new SpawnRule(MonsterType.SKELETON, 3, 6f, 7f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave7Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 25f, 1.5f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 30f, 2f))
            );
        }

        private static List<SpawnRuleEndWave> getWave7EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }

        // Wave 8 Rules
        private static List<SpawnRule> getWave8Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 6, 1f, 2f),
                new SpawnRule(MonsterType.SKELETON, 4, 7f, 5f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave8Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 10f, 1f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 15f, 2f))
            );
        }

        private static List<SpawnRuleEndWave> getWave8EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }

        // Wave 9 Rules
        private static List<SpawnRule> getWave9Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 7, 0f, 4f),
                new SpawnRule(MonsterType.SKELETON, 5, 3f, 6f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave9Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 20f, 1.5f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 25f, 3f))
            );
        }

        private static List<SpawnRuleEndWave> getWave9EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }

        // Wave 10 Rules
        private static List<SpawnRule> getWave10Rules() {
            return List.of(
                new SpawnRule(MonsterType.ZOMBIE, 8, 0f, 3f),
                new SpawnRule(MonsterType.SKELETON, 6, 5f, 7f)
            );
        }

        private static Map<MonsterType, List<SpawnBehavior>> getWave10Behavior() {
            return Map.of(
                MonsterType.ZOMBIE, List.of(new SpawnBehavior(1, 15f, 2f)),
                MonsterType.SKELETON, List.of(new SpawnBehavior(1, 20f, 2.5f))
            );
        }

        private static List<SpawnRuleEndWave> getWave10EndWaveBehavior(){
            return List.of(
                new SpawnRuleEndWave(MonsterType.ZOMBIE, 3, 30f, 1f, 35f),
                new SpawnRuleEndWave(MonsterType.SKELETON, 3, 30f, 1f, 35f)
            );
        }
    }
}
