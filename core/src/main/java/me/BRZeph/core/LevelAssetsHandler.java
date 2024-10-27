package me.BRZeph.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.utils.Constants;

public class LevelAssetsHandler {

    public static void loadCommonAssets(com.badlogic.gdx.assets.AssetManager assetManager){





        assetManager.finishLoading();
    }

    //** Use assetManager.unload() instead of using [texture].dispose() **

    public static void loadSpecificAssets(int levelNumber, AssetManager assetManager){
        switch (levelNumber){
            case 1:
                assetManager.load(Constants.ScreensAssets.LEVEL_1_BACKGROUND, Texture.class);
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
