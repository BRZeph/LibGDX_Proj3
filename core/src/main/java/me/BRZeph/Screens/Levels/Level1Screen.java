package me.BRZeph.Screens.Levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.Main;
import me.BRZeph.core.ScreenManager;
import me.BRZeph.core.LevelAssetsHandler;
import me.BRZeph.utils.Constants;

public class Level1Screen extends BaseLevelScreen {
    private SpriteBatch spriteBatch;
    private Music music;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera orthographicCamera;

    private Texture backgroundTexture;

    public Level1Screen(ScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);

        spriteBatch = Main.getSpriteBatch();
        music = Main.getMusic();
        shapeRenderer = Main.getShapeRenderer();
        orthographicCamera = Main.getOrthographicCamera();
        loadAssets();
    }

    @Override
    protected void loadAssets() {
        LevelAssetsHandler.loadCommonAssets(assetManager);
        LevelAssetsHandler.loadSpecificAssets(1, assetManager);

        backgroundTexture = assetManager.get(Constants.ScreensAssets.LEVEL_1_BACKGROUND);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        drawBackground();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void drawBackground() {
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture,
            (float) -Constants.SCREEN_WIDTH /2,
            (float) -Constants.SCREEN_HEIGHT /2,
            Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        spriteBatch.end();
    }
}
