package me.BRZeph.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.utils.Constants;

public class LevelAssetsManager {

    public static void loadCommonAssets(com.badlogic.gdx.assets.AssetManager assetManager){
        assetManager.load(Constants.Paths.TilesTexturesPath.GRASS_TILE, Texture.class);
        assetManager.load(Constants.Paths.TilesTexturesPath.WATER_TILE, Texture.class);
        assetManager.load(Constants.Paths.TilesTexturesPath.STARTING_POINT, Texture.class);
        assetManager.load(Constants.Paths.TilesTexturesPath.ENDING_POINT, Texture.class);
        assetManager.load(Constants.Paths.MonstersTexturesPath.ZOMBIE_TEXTURE, Texture.class);
        assetManager.load(Constants.Paths.MonstersTexturesPath.SKELETON_TEXTURE, Texture.class);
        assetManager.load(Constants.Paths.TowersTexturesPath.ARCHER_TOWER, Texture.class);
        assetManager.load(Constants.Paths.UIPath.TOWER_MENU_UI, Texture.class);
        assetManager.load(Constants.Paths.UIPath.HEART_UI, Texture.class);
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
}
