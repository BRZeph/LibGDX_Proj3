package me.BRZeph;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import me.BRZeph.Screens.ScreenManager;

import java.awt.*;

import static me.BRZeph.core.Assets.FontManager.disposeFonts;
import static me.BRZeph.core.Assets.FontManager.loadFonts;

/** {@link com.badlogic.gdx.ApplicationListener}*/
public class TowerDefenseGame extends Game {
    private static AssetManager assetManager;
    private static ScreenManager screenManager;
    private static ShapeRenderer shapeRenderer;

    private static float runTime = 0;

    @Override
    public void create() {
        assetManager = new AssetManager();
        screenManager = new ScreenManager(this, assetManager);
        loadFonts();
        shapeRenderer = new ShapeRenderer();
        screenManager.showMainMenu();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            listLoadedAssets(assetManager);
        }));
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        runTime += delta;

        com.badlogic.gdx.Gdx.gl.glClear(com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        screenManager.dispose();
        shapeRenderer.dispose();
        disposeFonts();
    }

    public static void listLoadedAssets(AssetManager assetManager) {
        System.out.println("\n");
        Array<String> assetNames = assetManager.getAssetNames();

        for (String assetName : assetNames) {
            Class<?> assetType = assetManager.getAssetType(assetName);

            System.out.println("Asset Name: " + assetName + ", Type: " + assetType.getSimpleName());
        }
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }

    public static ScreenManager getScreenManager() {
        return screenManager;
    }
}
