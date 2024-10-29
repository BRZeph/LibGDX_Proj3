package me.BRZeph;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import me.BRZeph.core.ScreenManager;

/** {@link com.badlogic.gdx.ApplicationListener}*/
public class Main extends Game {
    private static SpriteBatch batch;
    private static AssetManager assetManager;
    private static ScreenManager screenManager;

    private static SpriteBatch spriteBatch;
    private static BitmapFont bitmapFont;
    private static ShapeRenderer shapeRenderer;
    //    private static PerspectiveCamera perspectiveCamera;
    private static TextureAtlas textureAtlas;
    private static Sprite sprite;

    private static float runTime = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        spriteBatch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        textureAtlas = new TextureAtlas();
        sprite = new Sprite();

        assetManager = new AssetManager();
        screenManager = new ScreenManager(this, assetManager);
        screenManager.showMainMenu();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // This will be executed when the JVM is shutting down
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
        spriteBatch.dispose();
        bitmapFont.dispose();
        shapeRenderer.dispose();
        textureAtlas.dispose();
    }
    public static void listLoadedAssets(AssetManager assetManager) {
        System.out.println("\n");
        // Get all asset file names
        Array<String> assetNames = assetManager.getAssetNames();

        // Iterate through all the asset names
        for (String assetName : assetNames) {
            // Get the type of asset using its class
            Class<?> assetType = assetManager.getAssetType(assetName);

            // Print the asset name and type
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
