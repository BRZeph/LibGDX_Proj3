package me.BRZeph.utils;

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
        }
    }

    public static class Values {

        public static class PlayerValues {
            public static final float LEVEL_1_PLAYER_HEALTH = 10;
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
        }
    }

    // Monster Assets Paths
    public static class AssetsMonsters {
        public static final float ZOMBIE_HEALTH = 8;
        public static final int ZOMBIE_WIDTH = 32;
        public static final int ZOMBIE_HEIGHT = 32;
        public static final int ZOMBIE_SPEED = 100;
        public static final int ZOMBIE_NEXUS_DMG = 1;

        public static final float SKELETON_HEALTH = 5;
        public static final int SKELETON_WIDTH = 32;
        public static final int SKELETON_HEIGHT = 32;
        public static final int SKELETON_SPEED = 200;
        public static final int SKELETON_NEXUS_DMG = 2;
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
}
