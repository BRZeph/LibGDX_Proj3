package me.BRZeph.utils.Constants;

public class Constants {

    public static final int WINDOW_NUMBER = 1;
    public static final float TIME_MULTIPLIER = 1f;
    /*
    moving TIME_MULTIPLIER over 6.25 makes each frame take over 0.1 seconds which may cause problems
    recommended max value -> 5.
     */

    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 980;
    public static final boolean FULLSCREEN = false;

    public static class PlaceHolderValues{
        public static final String PLACE_HOLDER_TEXTURE_PATH = "Textures\\Place holder texture.png";
    }

    public static class LocalPaths {
        public static final String QTABLE_EXPORT_PATH = "C:\\Users\\Ricardo\\Desktop\\External data\\QTables";
        public static final String QTABLE_EXPORT_NAME = "QTable";
        public static final String GAME_DATA_EXPORT_PATH = "C:\\Users\\Ricardo\\Desktop\\External data\\GameData";
        public static final String GAME_DATA_EXPORT_NAME = "GameData";
    }

    public static class TowerUpgradeValues{
        public static final int MAIN_PATH_UPGRADE_LIMIT = 4;
        public static final int SECONDARY_PATH_UPGRADE_LIMIT = 2;
        public static final String BUTTON_BACKGROUND0 = "Textures\\UI\\SelectedTower\\tower upgrade ui 0.png";
        public static final String BUTTON_BACKGROUND1 = "Textures\\UI\\SelectedTower\\tower upgrade ui 1.png";
        public static final String BUTTON_BACKGROUND2 = "Textures\\UI\\SelectedTower\\tower upgrade ui 2.png";
        public static final String BUTTON_BACKGROUND3 = "Textures\\UI\\SelectedTower\\tower upgrade ui 3.png";
        public static final String BUTTON_BACKGROUND4 = "Textures\\UI\\SelectedTower\\tower upgrade ui 4.png";
        public static final String BUTTON_BACKGROUND100 = "Textures\\UI\\SelectedTower\\tower upgrade ui 100.png"; // After finalizing all upgrades main path.
        public static final String BUTTON_BACKGROUND101 = "Textures\\UI\\SelectedTower\\tower upgrade ui 101.png"; // After finalizing all upgrades sec path.
        public static final String BUTTON_BACKGROUND102 = "Textures\\UI\\SelectedTower\\tower upgrade ui 102.png"; // After finalizing all upgrades sec path.
    }

    public static class AIValues{
        public static final double STARTING_LEARNING_RATE = 0.1;
        public static final double STARTING_DISCOUNT_FACTOR = 0.95;
        public static final double STARTING_EXPLORATION_RATE = 1;
        public static final int MAX_EPISODES = 51; // episodes start at 1, so 11 max episodes mean 10 episodes.
        public static final float SUCCESS_REWARD = 100;
        public static final float LOSE_REWARD = -100f;
        public static final float ACTIONS_LIMIT_PER_WAVE = 10f;
        public static final float START_WAVE_ACTION_REWARD = 0f;
        public static final float PLACE_ARCHER_TOWER_REWARD = 1f;
        public static final float PLACE_CANNON_TOWER_REWARD = 1f;
        public static final float PLACE_LIGHTNING_TOWER_REWARD = 1f;
        public static final float SAVED_GOLD_MULTIPLIER = 1f;
        public static final float SAVED_ESSENCE_MULTIPLIER = 1f;
        public static final float SAVED_MOMENTUM_MULTIPLIER = 1f;
        public static final float FITNESS_DAMAGE_WEIGHT = 0.5f;
        public static final float FITNESS_KILLS_WEIGHT = 0.4f;
        public static final float FITNESS_COST_WEIGHT = 2f;
        public static final float FITNESS_DPS_WEIGHT = 1f;
        public static final float FITNESS_WAVE_WEIGHT = 0f;
    }

    public static class CameraValues {
        public static final float CAMERA_MIN_ZOOM = 0.5f;
        public static final float CAMERA_MAX_ZOOM = 1.0f;
        public static final float CAMERA_ZOOM_SPEED = 0.1f;
    }

    public static class Paths {
        public static final String WAVE_LIST_FILE_PATH = "assets\\waves.json";

        public static class ScreensTexturesPath {
            public static final String LEVEL_1_BACKGROUND = "Textures\\Screens\\LevelsScreen\\SpecificLevelAssets\\Level1\\level1_background_specific.png";
            public static final String MAIN_MENU_BACKGROUND = "Textures\\Screens\\MainMenuScreen\\MainMenuScreen_bg.png";
            public static final String LEVEL_SELECTOR_BACKGROUND = "Textures\\Screens\\LevelsScreen\\LevelSelectorScreen_bg.png";
        }

        public static class TowersTexturesPath {
            public static final String ARCHER_TOWER_PLACED = "Textures\\Towers\\ArcherTower\\Archer_tower.png";
            public static final String ARCHER_TOWER_ITEM = "Textures\\Towers\\ArcherTower\\Archer_tower_item.png";
            public static final String ARCHER_TOWER_PROJECTILE = "Textures\\Towers\\ArcherTower\\Archer_tower_projectile.png";
            public static final String CANNON_TOWER_PLACED = "Textures\\Towers\\CannonTower\\Cannon_tower.png";
            public static final String CANNON_TOWER_ITEM = "Textures\\Towers\\CannonTower\\Cannon_tower_item.png";
            public static final String CANNON_TOWER_PROJECTILE = "Textures\\Towers\\CannonTower\\Cannon_tower_projectile.png";
            public static final String LIGHTNING_TOWER_PLACED = "Textures\\Towers\\LightningTower\\Lightning_tower.png";
            public static final String LIGHTNING_TOWER_ITEM = "Textures\\Towers\\LightningTower\\Lightning_tower_item.png";
            public static final String LIGHTNING_TOWER_PROJECTILE = "Textures\\Towers\\LightningTower\\Lightning_tower_projectile.png";
        }

        public static class MonstersTexturesPath {
            // All of these textures are for the normal variant, other variants follow the pattern:
            // [normal name -4*]_[variant].png
            // Zombie normal texture -> Textures\Enemies\Zombie.png
            // Zombie elite texture ->  Textures\Enemies\Zombie_elite.png
            public static final String ZOMBIE_TEXTURE = "Textures\\Enemies\\Zombie.png";
            public static final String SKELETON_TEXTURE = "Textures\\Enemies\\Skeleton.png";
            public static final String DEMONIC_IMP_TEXTURE = "Textures\\Enemies\\Demonic_imp.png";
            public static final String HELLFIRE_BRUTE_TEXTURE = "Textures\\Enemies\\Hellfire_brute.png";
            public static final String SOUL_REAPER_TEXTURE = "Textures\\Enemies\\Soul_reaper.png";
            public static final String DOOM_HERALD_TEXTURE = "Textures\\Enemies\\Doom_herald.png";
            public static final String VOID_WRAITH_TEXTURE = "Textures\\Enemies\\Void_wraith.png";
            public static final String CHRONO_STALKER_TEXTURE = "Textures\\Enemies\\Chrono_stalker.png";
            public static final String INFERNAL_JUGGERNAUT_TEXTURE = "Textures\\Enemies\\Infernal_juggernaut.png";
            public static final String TEMPORAL_SHADE_TEXTURE = "Textures\\Enemies\\Temporal_shade.png";
            public static final String ABYSSAL_MATRON_TEXTURE = "Textures\\Enemies\\Abyssal_matron.png";
        }

        public static class TilesTexturesPath {

            public static final String GRASS_TILE_IDLE = "Textures\\Tiles\\Grass_idle.png";
            public static final String GRASS_TILE_FIRE = "Textures\\Tiles\\Grass_fire.png";
            public static final String WATER_TILE_IDLE = "Textures\\Tiles\\Water_idle.png";
            public static final String WATER_TILE_FIRE = "Textures\\Tiles\\Water_fire.png";
            public static final String STARTING_POINT_TILE_IDLE = "Textures\\Tiles\\Starting_Tile_idle.png";
            public static final String STARTING_POINT_TILE_FIRE = "Textures\\Tiles\\Starting_Tile_fire.png";
            public static final String ENDING_POINT_TILE_IDLE = "Textures\\Tiles\\Ending_Tile_idle.png";
            public static final String ENDING_POINT_TILE_FIRE = "Textures\\Tiles\\Ending_Tile_fire.png";
            public static final String HERO_WALL_TILE_IDLE = "Textures\\Tiles\\HeroRoom\\Hero_Wall_idle.png";
            public static final String HERO_WALL_TILE_FIRE = "Textures\\Tiles\\HeroRoom\\Hero_Wall_fire.png";
            public static final String HERO_CORNER_TILE_IDLE = "Textures\\Tiles\\HeroRoom\\Hero_BLC_idle.png";
            public static final String HERO_CORNER_TILE_FIRE = "Textures\\Tiles\\HeroRoom\\Hero_BLC_fire.png";
            public static final String HERO_FLOOR_TILE_IDLE = "Textures\\Tiles\\HeroRoom\\Hero_Floor_idle.png";
            public static final String HERO_FLOOR_TILE_FIRE = "Textures\\Tiles\\HeroRoom\\Hero_Floor_fire.png";
        }

        public static class MapsPath {
            public static final String LEVEL_1_MAP = "Map\\level_1_map.txt";
        }

        public static class UIPath {
            public static final String UI_ATLAS = "Textures\\UI\\Buttons\\UI atlas.png";
            public static final int UI_ATLAS_REGION_LENGTH = 32;
            public static final String TOWER_MENU_UI = "Textures\\UI\\tower list for placement UI 001.png";
            public static final String HEART_UI = "Textures\\UI\\heart 001.png";
            public static final String TOWER_SHOP_BACKGROUND_UI = "Textures\\UI\\towerShop\\tower shop ui back 001.png";
            public static final String TOWER_SHOP_FRONT_UI = "Textures\\UI\\towerShop\\tower shop ui front 001.png";
            public static final String SELECTED_TOWER_BACKGROUND_UI = "Textures\\UI\\SelectedTower\\selected tower background 001.png";
        }

        public static class Animations {
            public static final String PLAYER_WALK_ANIMATION_ATLAS = "Textures\\Animations\\Player\\PlayerWalk.png";
            public static final float PLAYER_WALK_FRAME_TIME = 0.1f;
            public static final String PLAYER_WALK_ANIMATION_NAME = "player_walk";

            public static final String MONSTER_WALK_ANIMATION_ATLAS = "Textures\\Animations\\Monster\\MonsterWalk.png";

            public static final String ZOMBIE_WALK_ANIMATION_NAME = "zombie_walk";
            public static final float ZOMBIE_WALK_FRAME_TIME = 0.1f;
            public static final String SKELETON_WALK_ANIMATION_NAME = "skeleton_walk";
            public static final float SKELETON_WALK_FRAME_TIME = 0.1f;
            public static final String DEMONIC_IMP_WALK_ANIMATION_NAME = "demonic_imp_walk";
            public static final float DEMONIC_IMP_WALK_FRAME_TIME = 0.1f;
            public static final String HELLFIRE_BRUTE_WALK_ANIMATION_NAME = "hellfire_brute_walk";
            public static final float HELLFIRE_BRUTE_WALK_FRAME_TIME = 0.1f;
            public static final String SOUL_REAPER_WALK_ANIMATION_NAME = "soul_reaper_walk";
            public static final float SOUL_REAPER_WALK_FRAME_TIME = 0.1f;
            public static final String DOOM_HERALD_WALK_ANIMATION_NAME = "doom_herald_walk";
            public static final float DOOM_HERALD_WALK_FRAME_TIME = 0.1f;
            public static final String VOID_WRAITH_WALK_ANIMATION_NAME = "void_wraith_walk";
            public static final float VOID_WRAITH_WALK_FRAME_TIME = 0.1f;
            public static final String CHRONO_STALKER_WALK_ANIMATION_NAME = "chrono_stalker_walk";
            public static final float CHRONO_STALKER_WALK_FRAME_TIME = 0.1f;
            public static final String INFERNAL_JUGGERNAUT_WALK_ANIMATION_NAME = "infernal_juggernaut_walk";
            public static final float INFERNAL_JUGGERNAUT_WALK_FRAME_TIME = 0.1f;
            public static final String TEMPORAL_SHADE_WALK_ANIMATION_NAME = "temporal_shade_walk";
            public static final float TEMPORAL_SHADE_WALK_FRAME_TIME = 0.1f;
            public static final String ABYSSAL_MATRON_WALK_ANIMATION_NAME = "abyssal_matron_walk";
            public static final float ABYSSAL_MATRON_WALK_FRAME_TIME = 0.1f;
            public static final String LESSER_ABYSSAL_MATRON_WALK_ANIMATION_NAME = "lesser_abyssal_matron_walk";
            public static final float LESSER_ABYSSAL_MATRON_WALK_FRAME_TIME = 0.1f;
        }

        public static class Values {

            public static class PlayerValues {
                public static final float PLAYER_HEALTH = 500;
                public static final int PLAYER_SPEED = 300;
                public static final int STARTING_GOLD = 18000;
                public static final int STARTING_MOMENTUM = 18000;
                public static final int STARTING_ESSENCE = 18000;

                public static final float HOLDING_ITEM_WIDTH = 48;
                public static final float HOLDING_ITEM_HEIGHT = 48;
                public static final float PLAYER_WIDTH = 64;
                public static final float PLAYER_HEIGHT = 64;
            }

            public static class TowerValues {
                public static final int TOWER_ATTACK_BAR_WIDTH = 48;
                public static class ArcherTowerValues {
                    public static final float ARCHER_TOWER_PRICE_GOLD = 80;
                    public static final float ARCHER_TOWER_PRICE_ESSENCE = 0;
                    public static final float ARCHER_TOWER_PRICE_MOMENTUM = 0;
                    public static final float ARCHER_TOWER_DAMAGE = 4.5f;
                    public static final float ARCHER_TOWER_RANGE = 832;
                    public static final float ARCHER_TOWER_ATTACK_COOLDOWN = 3f / TIME_MULTIPLIER;
                    public static final float ARCHER_TOWER_PROJECTILE_SPEED = 900 * TIME_MULTIPLIER;
                    public static final boolean ARCHER_TOWER_IS_AOE = false;
                    public static final float ARCHER_TOWER_AOE_RANGE = -1;
                    public static final float ARCHER_TOWER_AOE_DAMAGE = -1;
                    public static final int ARCHER_TOWER_PROJECTILE_WIDTH = 32;
                    public static final int ARCHER_TOWER_PROJECTILE_HEIGHT = 32;
                }

                public static class CannonTowerValues {
                    public static final float CANNON_TOWER_PRICE_GOLD = 90;
                    public static final float CANNON_TOWER_PRICE_ESSENCE = 0;
                    public static final float CANNON_TOWER_PRICE_MOMENTUM = 0;
                    public static final float CANNON_TOWER_DAMAGE = 3;
                    public static final float CANNON_TOWER_RANGE = 384;
                    public static final float CANNON_TOWER_ATTACK_COOLDOWN = 5f / TIME_MULTIPLIER;
                    public static final float CANNON_TOWER_PROJECTILE_SPEED = 1600 * TIME_MULTIPLIER;
                    public static final boolean CANNON_TOWER_IS_AOE = false;
                    public static final float CANNON_TOWER_AOE_RANGE = 128;
                    public static final float CANNON_TOWER_AOE_DAMAGE = 1.5f;
                    public static final int CANNON_TOWER_PROJECTILE_WIDTH = 32;
                    public static final int CANNON_TOWER_PROJECTILE_HEIGHT = 32;
                }

                public static class LightningTowerValues {
                    public static final float LIGHTNING_TOWER_PRICE_GOLD = 100;
                    public static final float LIGHTNING_TOWER_PRICE_ESSENCE = 0;
                    public static final float LIGHTNING_TOWER_PRICE_MOMENTUM = 0;
                    public static final float LIGHTNING_TOWER_DAMAGE = 2;
                    public static final float LIGHTNING_TOWER_RANGE = 512;
                    public static final float LIGHTNING_TOWER_ATTACK_COOLDOWN = 2f / TIME_MULTIPLIER;
                    public static final float LIGHTNING_TOWER_PROJECTILE_SPEED = 1600 * TIME_MULTIPLIER;
                    public static final boolean LIGHTNING_TOWER_IS_AOE = false;
                    public static final float LIGHTNING_TOWER_AOE_RANGE = -1;
                    public static final float LIGHTNING_TOWER_AOE_DAMAGE = -1;
                    public static final int LIGHTNING_TOWER_PROJECTILE_WIDTH = 32;
                    public static final int LIGHTNING_TOWER_PROJECTILE_HEIGHT = 32;
                    public static final int LIGHTNING_TOWER_CHAIN_HIT_TARGET_AMOUNT = 3;
                    public static final int LIGHTNING_TOWER_CHAIN_HIT_MAX_DISTANCE = 256;
                    public static final float LIGHTNING_TOWER_CHAIN_HIT_DAMAGE = 1f;
                }
            }

            public static class UIValues {
                public static final int TOWER_SHOP_WIDTH = 250;
                public static final int TOWER_SHOP_HEIGHT = 980;

                public static final int FPS_X_POS = 20;
                public static final int FPS_Y_POS_DECREASE = 20;

                public static final int HEALTH_X_POS = SCREEN_WIDTH / 2;
                public static final int HEALTH_Y_POS_DECREASE = 25;
                public static final int TEXTURE_MESSAGE_PADDING = 5;
                public static final int POPUP_MESSAGE_PADDING = 10;

                public static final int HEART_WIDTH = 32;
                public static final int HEART_HEIGHT = 32;
                public static final float HEALTH_BAR_WIDTH = 64;
                public static final float HEALTH_BAR_HEIGHT = 8;

                public static final int CURRENT_WAVE_WIDTH = 200;
                public static final int CURRENT_WAVE_HEIGHT_DECREASE = 40;
                public static final int CURRENT_WAVE_CLOCK_HEIGHT_DECREASE = 40;

                public static final int CURRENCY_X_POS_DECREASE = 300;

                public static final int TOWER_SHOP_GRIDX = 2;
                public static final int TOWER_SHOP_GRIDY = 8;
                public static final int TOWER_SHOP_ITEM_LENGTH = 64;

                public static class ButtonsValues {
                    public static final String TEST_BUTTON_TEXTURE_PATH = "Textures\\UI\\Buttons\\test button 001.png";
                    public static final String START_WAVE_BUTTON_TEXTURE_PATH = "Textures\\UI\\Buttons\\start wave button.png";
                    public static final String PLACEHOLDER_BUTTON_TEXTURE_PATH = "Textures\\UI\\Buttons\\placeholder.png";

                    public static final int START_WAVE_BUTTON_X_POS = 100;
                    public static final int START_WAVE_BUTTON_Y_POS = 100;
                    public static final int START_WAVE_BUTTON_WIDTH = 200;
                    public static final int START_WAVE_BUTTON_HEIGHT = 100;
                }

                public static class SelectedTowerValues {
                    public static final int Y_AXIS_PADDING = 20;

                    public static final int SELECTED_TOWER_UI_WIDTH = 350;
                    public static final int SELECTED_TOWER_UI_HEIGHT = 750;
                    public static final int SELECTED_TOWER_X_POS = SCREEN_WIDTH - TOWER_SHOP_WIDTH - SELECTED_TOWER_UI_WIDTH;
                    public static final int SELECTED_TOWER_Y_POS = (SCREEN_HEIGHT - SELECTED_TOWER_UI_HEIGHT) / 2;

                    public static final int TOWER_TEXTURE_X_POS = SELECTED_TOWER_X_POS + 61;
                    public static final int TOWER_TEXTURE_Y_POS = SELECTED_TOWER_Y_POS + SELECTED_TOWER_UI_HEIGHT - 138;
                    public static final int TOWER_TEXTURE_WIDTH = 78;
                    public static final int TOWER_TEXTURE_HEIGHT = 78;

                    public static final int TOWER_AGGRO_LEFT_BUTTON_X_POS = SELECTED_TOWER_X_POS + 30;
                    public static final int TOWER_AGGRO_LEFT_BUTTON_HEIGHT = 40;
                    public static final int TOWER_AGGRO_LEFT_BUTTON_Y_POS = TOWER_TEXTURE_Y_POS - TOWER_AGGRO_LEFT_BUTTON_HEIGHT - Y_AXIS_PADDING;
                    public static final int TOWER_AGGRO_LEFT_BUTTON_WIDTH = SELECTED_TOWER_UI_WIDTH / 2 - 30;

                    public static final int TOWER_AGGRO_RIGHT_BUTTON_X_POS = SELECTED_TOWER_X_POS + 30 + SELECTED_TOWER_UI_WIDTH / 2;
                    public static final int TOWER_AGGRO_RIGHT_BUTTON_HEIGHT = 40;
                    public static final int TOWER_AGGRO_RIGHT_BUTTON_Y_POS = TOWER_TEXTURE_Y_POS - TOWER_AGGRO_RIGHT_BUTTON_HEIGHT - Y_AXIS_PADDING;
                    public static final int TOWER_AGGRO_RIGHT_BUTTON_WIDTH = SELECTED_TOWER_UI_WIDTH / 2 - 30;

                    public static final int TOWER_INFO_BUTTON_X_POS = SELECTED_TOWER_X_POS + 30;
                    public static final int TOWER_INFO_BUTTON_HEIGHT = 60;
                    public static final int TOWER_INFO_BUTTON_Y_POS = TOWER_AGGRO_RIGHT_BUTTON_Y_POS - TOWER_INFO_BUTTON_HEIGHT - Y_AXIS_PADDING;
                    public static final int TOWER_INFO_BUTTON_WIDTH = SELECTED_TOWER_UI_WIDTH - 60;

                    public static final int TOWER_PATH_UPGRADE_BUTTON_HEIGHT = 120;
                    public static final int TOWER_PATH_UPGRADE_BUTTON_WIDTH = SELECTED_TOWER_UI_WIDTH - 60;

                    public static final int TOWER_PATH1_UPGRADE_BUTTON_X_POS = SELECTED_TOWER_X_POS + 30;
                    public static final int TOWER_PATH1_UPGRADE_BUTTON_Y_POS = TOWER_INFO_BUTTON_Y_POS - TOWER_PATH_UPGRADE_BUTTON_HEIGHT - Y_AXIS_PADDING;

                    public static final int TOWER_PATH2_UPGRADE_BUTTON_X_POS = SELECTED_TOWER_X_POS + 30;
                    public static final int TOWER_PATH2_UPGRADE_BUTTON_Y_POS = TOWER_PATH1_UPGRADE_BUTTON_Y_POS - TOWER_PATH_UPGRADE_BUTTON_HEIGHT - Y_AXIS_PADDING;

                    public static final int TOWER_PATH3_UPGRADE_BUTTON_X_POS = SELECTED_TOWER_X_POS + 30;
                    public static final int TOWER_PATH3_UPGRADE_BUTTON_Y_POS = TOWER_PATH2_UPGRADE_BUTTON_Y_POS - TOWER_PATH_UPGRADE_BUTTON_HEIGHT - Y_AXIS_PADDING;
                }
            }
        }

        public static class MonstersValues {
            public static class VariantValues {
                public static final float NORMAL_HP_MOD = 1;
                public static final float NORMAL_SPD_MOD = 1;
                public static final float NORMAL_NEXUS_DMG_MOD = 1;

                public static final float ARMORED_HP_MOD = 2.5f;
                public static final float ARMORED_SPD_MOD = 0.9f;
                public static final float ARMORED_NEXUS_DMG_MOD = 2f;

                public static final float CORRUPTED_HP_MOD = 3.5f;
                public static final float CORRUPTED_SPD_MOD = 2f;
                public static final float CORRUPTED_NEXUS_DMG_MOD = 5f;

                public static final float ELITE_HP_MOD = 8;
                public static final float ELITE_SPD_MOD = 1.5f;
                public static final float ELITE_NEXUS_DMG_MOD = 10f;

                public static final float CORRUPTION_SCALING_HP = 0.03f; // + 3% hp per wave
                public static final float CORRUPTION_SCALING_SPEED = 0.01f; // + 1% speed per wave
                public static final float CORRUPTION_SCALING_NEXUS_DMG = 0.01f; // + 1% nexus dmg per wave
            }
            /*
            monsters ideas and why they exist:

            -> Zombie:
                -> zombie is tankier skeleton is faster,
                -> purpose: basic monsters to teach the core of the game.
            -> Skeleton:
                -> skeleton is faster zombie is tankier,
                -> purpose: basic monsters to teach the core of the game.
            -> imp (wave 5+):
                -> spawns in groups (imp swarm),
                -> grants a small 10% speed buff (non-stackable) to all nearby monsters for X seconds,
                -> purpose: Introduces swarming behavior early on, teaching players to balance single-target and
                 area-of-effect (AoE) strategies.
            -> Abyssal matron (wave 10+):
                -> resistant to slowing effects (assuming im taking the momentum route),
                -> upon death, splits into two "Lesser Abyssal Guards" with halved stats,
                -> purpose: Adds a tankier enemy that requires sustained damage and introduces splitting mechanics.
            -> Infernal juggernaut (wave15+):
                -> immune to all status effects (slows, stuns, debuffs etc),
                -> nearby monsters have increased armor status (take less damage from physical hits),
                -> purpose: A walking tank that pressures the player's ability to deal sustained damage.
            -> Temporal shade (wave25+):
                -> alternate between "physical" and "ethereal" forms every X seconds, on ethereal form it does not take physical damage,
                -> that's a bad idea, rework it later.
            -> Doom herald (wave40+, boss):
                -> super tanky, super slow,
                -> periodically spawns "shadowLings" (tiny, fast and squishy monsters),
                -> aura of oppression: reduces the range, damage and fire rate of nearby towers by 10%,
                -> every 30% it loses, revive 10% of the monsters that dies since the last cast or since it spawned where
                the boss is,
                -> purpose: tests the player's ability to deal with both a tanky boss and distractions from spawned minions.
            -> Void wraith (wave60+):
                -> no ideas here yet.
            -> Chrono Stalker (wave80+, boss):
                -> no ideas here yet.
             */

            public static class ZombieValues {
                public static final float ZOMBIE_HEALTH = 10;
                public static final int ZOMBIE_WIDTH = 32;
                public static final int ZOMBIE_HEIGHT = 32;
                public static final int ZOMBIE_SPEED = (int) (100 * TIME_MULTIPLIER);
                public static final int ZOMBIE_NEXUS_DMG = 1;
                public static final float ZOMBIE_GOLD_LOOT = 20;
                public static final float ZOMBIE_ESSENCE_LOOT = 0;
                public static final float ZOMBIE_MOMENTUM_LOOT = 0;
            }

            public static class SkeletonValues {
                public static final float SKELETON_HEALTH = 5;
                public static final int SKELETON_WIDTH = 32;
                public static final int SKELETON_HEIGHT = 32;
                public static final int SKELETON_SPEED = (int) (200 * TIME_MULTIPLIER);
                public static final int SKELETON_NEXUS_DMG = 2;
                public static final float SKELETON_GOLD_LOOT = 10;
                public static final float SKELETON_ESSENCE_LOOT = 1;
                public static final float SKELETON_MOMENTUM_LOOT = 0;
            }

            public static class DemonicImpValues {
                public static final float DEMONIC_IMP_HEALTH = 3;
                public static final int DEMONIC_IMP_WIDTH = 16;
                public static final int DEMONIC_IMP_HEIGHT = 16;
                public static final int DEMONIC_IMP_SPEED = (int) (400 * TIME_MULTIPLIER);
                public static final int DEMONIC_IMP_NEXUS_DMG = 1;
                public static final float DEMONIC_IMP_GOLD_LOOT = 5;
                public static final float DEMONIC_IMP_ESSENCE_LOOT = 0.0f;
                public static final float DEMONIC_IMP_MOMENTUM_LOOT = 0.0f;
            }

            public static class HellfireBruteValues {
                public static final float HELLFIRE_BRUTE_HEALTH = 50;
                public static final int HELLFIRE_BRUTE_WIDTH = 64;
                public static final int HELLFIRE_BRUTE_HEIGHT = 64;
                public static final int HELLFIRE_BRUTE_SPEED = (int) (50 * TIME_MULTIPLIER);
                public static final int HELLFIRE_BRUTE_NEXUS_DMG = 10;
                public static final float HELLFIRE_BRUTE_GOLD_LOOT = 40;
                public static final float HELLFIRE_BRUTE_ESSENCE_LOOT = 5;
                public static final float HELLFIRE_BRUTE_MOMENTUM_LOOT = 2;
            }

            public static class SoulReaperValues {
                public static final float SOUL_REAPER_HEALTH = 15;
                public static final int SOUL_REAPER_WIDTH = 32;
                public static final int SOUL_REAPER_HEIGHT = 32;
                public static final int SOUL_REAPER_SPEED = (int) (150 * TIME_MULTIPLIER);
                public static final int SOUL_REAPER_NEXUS_DMG = 3;
                public static final float SOUL_REAPER_GOLD_LOOT = 30;
                public static final float SOUL_REAPER_ESSENCE_LOOT = 3;
                public static final float SOUL_REAPER_MOMENTUM_LOOT = 1;
            }

            public static class DoomHeraldValues {
                public static final float DOOM_HERALD_HEALTH = 10;
                public static final int DOOM_HERALD_WIDTH = 32;
                public static final int DOOM_HERALD_HEIGHT = 32;
                public static final int DOOM_HERALD_SPEED = (int) (150 * TIME_MULTIPLIER);
                public static final int DOOM_HERALD_NEXUS_DMG = 2;
                public static final float DOOM_HERALD_GOLD_LOOT = 20;
                public static final float DOOM_HERALD_ESSENCE_LOOT = 2;
                public static final float DOOM_HERALD_MOMENTUM_LOOT = 1;
            }

            public static class VoidWraithValues {
                public static final float VOID_WRAITH_HEALTH = 8;
                public static final int VOID_WRAITH_WIDTH = 32;
                public static final int VOID_WRAITH_HEIGHT = 32;
                public static final int VOID_WRAITH_SPEED = (int) (200 * TIME_MULTIPLIER);
                public static final int VOID_WRAITH_NEXUS_DMG = 3;
                public static final float VOID_WRAITH_GOLD_LOOT = 20;
                public static final float VOID_WRAITH_ESSENCE_LOOT = 2;
                public static final float VOID_WRAITH_MOMENTUM_LOOT = 1;
            }

            public static class ChronoStalkerValues {
                public static final float CHRONO_STALKER_HEALTH = 20;
                public static final int CHRONO_STALKER_WIDTH = 48;
                public static final int CHRONO_STALKER_HEIGHT = 48;
                public static final int CHRONO_STALKER_SPEED = (int) (100 * TIME_MULTIPLIER);
                public static final int CHRONO_STALKER_NEXUS_DMG = 4;
                public static final float CHRONO_STALKER_GOLD_LOOT = 50;
                public static final float CHRONO_STALKER_ESSENCE_LOOT = 4;
                public static final float CHRONO_STALKER_MOMENTUM_LOOT = 1.5f;
            }

            public static class InfernalJuggernautValues {
                public static final float INFERNAL_JUGGERNAUT_HEALTH = 100;
                public static final int INFERNAL_JUGGERNAUT_WIDTH = 96;
                public static final int INFERNAL_JUGGERNAUT_HEIGHT = 96;
                public static final int INFERNAL_JUGGERNAUT_SPEED = (int) (50 * TIME_MULTIPLIER);
                public static final int INFERNAL_JUGGERNAUT_NEXUS_DMG = 15;
                public static final float INFERNAL_JUGGERNAUT_GOLD_LOOT = 150;
                public static final float INFERNAL_JUGGERNAUT_ESSENCE_LOOT = 10;
                public static final float INFERNAL_JUGGERNAUT_MOMENTUM_LOOT = 5;
            }

            public static class TemporalShadeValues {
                public static final float TEMPORAL_SHADE_HEALTH = 6;
                public static final int TEMPORAL_SHADE_WIDTH = 32;
                public static final int TEMPORAL_SHADE_HEIGHT = 32;
                public static final int TEMPORAL_SHADE_SPEED = (int) (150 * TIME_MULTIPLIER);
                public static final int TEMPORAL_SHADE_NEXUS_DMG = 2;
                public static final float TEMPORAL_SHADE_GOLD_LOOT = 10;
                public static final float TEMPORAL_SHADE_ESSENCE_LOOT = 1;
                public static final float TEMPORAL_SHADE_MOMENTUM_LOOT = 0.5f;
            }

            public static class AbyssalMatronValues {
                public static final float ABYSSAL_MATRON_HEALTH = 30;
                public static final int ABYSSAL_MATRON_WIDTH = 64;
                public static final int ABYSSAL_MATRON_HEIGHT = 64;
                public static final int ABYSSAL_MATRON_SPEED = (int) (75 * TIME_MULTIPLIER);
                public static final int ABYSSAL_MATRON_NEXUS_DMG = 5;
                public static final float ABYSSAL_MATRON_GOLD_LOOT = 20;
                public static final float ABYSSAL_MATRON_ESSENCE_LOOT = 6;
                public static final float ABYSSAL_MATRON_MOMENTUM_LOOT = 3;
                public static final int ABYSSAL_MATRON_DEATH_SPAWNS = 15;
                public static final float ABYSSAL_MATRON_SPAWNS_XOFFSET = 256;
                public static final float ABYSSAL_MATRON_SPAWNS_YOFFSET = 256;
            }

            public static class LesserAbyssalMatronValues {
                public static final float LESSER_ABYSSAL_MATRON_HEALTH = 15;
                public static final int LESSER_ABYSSAL_MATRON_WIDTH = 64;
                public static final int LESSER_ABYSSAL_MATRON_HEIGHT = 64;
                public static final int LESSER_ABYSSAL_MATRON_SPEED = (int) (75 * TIME_MULTIPLIER);
                public static final int LESSER_ABYSSAL_MATRON_NEXUS_DMG = 5;
                public static final float LESSER_ABYSSAL_MATRON_GOLD_LOOT = 20;
                public static final float LESSER_ABYSSAL_MATRON_ESSENCE_LOOT = 6;
                public static final float LESSER_ABYSSAL_MATRON_MOMENTUM_LOOT = 3;
            }
        }

        // Tile Assets Paths and Constants
        public static class TileValues {
            public static final int TILE_WIDTH = 64;
            public static final int TILE_HEIGHT = 64;
        }
    }
}
