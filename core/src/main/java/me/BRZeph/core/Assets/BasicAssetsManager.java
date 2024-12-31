package me.BRZeph.core.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;

import java.util.HashMap;

import static me.BRZeph.entities.Map.TileType.*;
import static me.BRZeph.utils.Constants.Paths.MonstersTexturesPath.*;
import static me.BRZeph.utils.Constants.Paths.TilesTexturesPath.*;
import static me.BRZeph.utils.Constants.Paths.TowersTexturesPath.*;
import static me.BRZeph.utils.Constants.Paths.UIPath.*;
import static me.BRZeph.utils.Constants.Paths.Values.UIValues.ButtonsValues.TEST_BUTTON_TEXTURE_PATH;

public class BasicAssetsManager {

    private static final HashMap<TileType, HashMap<String, Texture>> tileStates = new HashMap<>();

    public static void loadCommonAssets(AssetManager assetManager){
        loadTileTextures(assetManager);
        loadMonsterTexture(assetManager);
        loadUITextures(assetManager);
        loadTowerTextures(assetManager);
        loadMapTextureStates(assetManager);
    }

    private static void loadTileTextures(AssetManager assetManager){
        assetManager.load(GRASS_TILE_IDLE, Texture.class);
        assetManager.load(GRASS_TILE_FIRE, Texture.class);
        assetManager.load(WATER_TILE_IDLE, Texture.class);
        assetManager.load(WATER_TILE_FIRE, Texture.class);
        assetManager.load(STARTING_POINT_TILE_IDLE, Texture.class);
        assetManager.load(STARTING_POINT_TILE_FIRE, Texture.class);
        assetManager.load(ENDING_POINT_TILE_IDLE, Texture.class);
        assetManager.load(ENDING_POINT_TILE_FIRE, Texture.class);
        assetManager.load(HERO_WALL_TILE_IDLE, Texture.class);
        assetManager.load(HERO_WALL_TILE_FIRE, Texture.class);
        assetManager.load(HERO_CORNER_TILE_IDLE, Texture.class);
        assetManager.load(HERO_CORNER_TILE_FIRE, Texture.class);
        assetManager.load(HERO_FLOOR_TILE_IDLE, Texture.class);
        assetManager.load(HERO_FLOOR_TILE_FIRE, Texture.class);
        assetManager.finishLoading();
    }

    private static void loadMonsterTexture(AssetManager assetManager){
        assetManager.load(ZOMBIE_TEXTURE, Texture.class);
        assetManager.load(SKELETON_TEXTURE, Texture.class);
        assetManager.load(DEMONIC_IMP_TEXTURE, Texture.class);
        assetManager.load(HELLFIRE_BRUTE_TEXTURE, Texture.class);
        assetManager.load(SOUL_REAPER_TEXTURE, Texture.class);
        assetManager.load(DOOM_HERALD_TEXTURE, Texture.class);
        assetManager.load(VOID_WRAITH_TEXTURE, Texture.class);
        assetManager.load(CHRONO_STALKER_TEXTURE, Texture.class);
        assetManager.load(INFERNAL_JUGGERNAUT_TEXTURE, Texture.class);
        assetManager.load(TEMPORAL_SHADE_TEXTURE, Texture.class);
        assetManager.load(ABYSSAL_MATRON_TEXTURE, Texture.class);
        assetManager.finishLoading();
    }

    private static void loadUITextures(AssetManager assetManager){
        assetManager.load(TEST_BUTTON_TEXTURE_PATH, Texture.class);
        assetManager.load(TOWER_SHOP_BACKGROUND_UI, Texture.class);
        assetManager.load(TOWER_SHOP_FRONT_UI, Texture.class);
        assetManager.load(TOWER_MENU_UI, Texture.class);
        assetManager.load(HEART_UI, Texture.class);
        assetManager.load(SELECTED_TOWER_BACKGROUND_UI, Texture.class);
        assetManager.finishLoading();
    }

    private static void loadTowerTextures(AssetManager assetManager) {
        assetManager.load(ARCHER_TOWER_PLACED, Texture.class);
        assetManager.load(ARCHER_TOWER_ITEM, Texture.class);
        assetManager.load(ARCHER_TOWER_PROJECTILE, Texture.class);
        assetManager.load(CANNON_TOWER_PLACED, Texture.class);
        assetManager.load(CANNON_TOWER_ITEM, Texture.class);
        assetManager.load(CANNON_TOWER_PROJECTILE, Texture.class);
        assetManager.load(LIGHTNING_TOWER_PLACED, Texture.class);
        assetManager.load(LIGHTNING_TOWER_ITEM, Texture.class);
        assetManager.load(LIGHTNING_TOWER_PROJECTILE, Texture.class);
        assetManager.finishLoading();
    }

    //** Use assetManager.unload() instead of using [texture].dispose() **

    public static void loadSpecificAssets(int levelNumber, AssetManager assetManager){
        switch (levelNumber){
            case 1:
                assetManager.load(Constants.Paths.ScreensTexturesPath.LEVEL_1_BACKGROUND, Texture.class);
                //load specific assets from level 1
                break;

            case 2:
                //load specific assets from level 2
                break;

            default:
                throw new IllegalArgumentException("Invalid level");
        }

        assetManager.finishLoading();
    }

    public static void loadMapTextureStates(AssetManager assetManager){

        HashMap<String, Texture> grassStateTextures = new HashMap<>();
        grassStateTextures.put("idle", assetManager.get(GRASS_TILE_IDLE));
        grassStateTextures.put("fire", assetManager.get(GRASS_TILE_FIRE));
        tileStates.put(GRASS, grassStateTextures);

        HashMap<String, Texture> waterStateTextures = new HashMap<>();
        waterStateTextures.put("idle", assetManager.get(WATER_TILE_IDLE));
        waterStateTextures.put("fire", assetManager.get(WATER_TILE_FIRE));
        tileStates.put(WATER, waterStateTextures);

        HashMap<String, Texture> startingStateTextures = new HashMap<>();
        startingStateTextures.put("idle", assetManager.get(STARTING_POINT_TILE_IDLE));
        startingStateTextures.put("fire", assetManager.get(STARTING_POINT_TILE_FIRE));
        tileStates.put(STARTING_POINT, startingStateTextures);

        HashMap<String, Texture> endingStateTextures = new HashMap<>();
        endingStateTextures.put("idle", assetManager.get(ENDING_POINT_TILE_IDLE));
        endingStateTextures.put("fire", assetManager.get(ENDING_POINT_TILE_FIRE));
        tileStates.put(ENDING_POINT, endingStateTextures);

        HashMap<String, Texture> heroRoomWall = new HashMap<>();
        heroRoomWall.put("idle", assetManager.get(HERO_WALL_TILE_IDLE));
        heroRoomWall.put("fire", assetManager.get(HERO_WALL_TILE_FIRE));
        tileStates.put(HERO_ROOM_WALL, heroRoomWall);

        HashMap<String, Texture> heroRoomCorner = new HashMap<>();
        heroRoomCorner.put("idle", assetManager.get(HERO_CORNER_TILE_IDLE));
        heroRoomCorner.put("fire", assetManager.get(HERO_CORNER_TILE_FIRE));
        tileStates.put(HERO_ROOM_CORNER, heroRoomCorner);

        HashMap<String, Texture> heroRoomFloor = new HashMap<>();
        heroRoomFloor.put("idle", assetManager.get(HERO_FLOOR_TILE_IDLE));
        heroRoomFloor.put("fire", assetManager.get(HERO_FLOOR_TILE_FIRE));
        tileStates.put(HERO_ROOM_FLOOR, heroRoomFloor);
    }

    public static Texture getTileStateTexture(TileType tileType, String state) {
        return tileStates.get(tileType).get(state);
    }
}
