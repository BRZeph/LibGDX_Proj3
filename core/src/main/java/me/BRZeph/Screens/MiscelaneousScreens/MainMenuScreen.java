package me.BRZeph.Screens.MiscelaneousScreens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.BRZeph.core.Managers.ScreenManager;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;

public class MainMenuScreen implements Screen, InputProcessor {
    private final ScreenManager screenManager;
    private final AssetManager assetManager;

    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;

    private float elapsedTime;

    public MainMenuScreen(ScreenManager screenManager, AssetManager assetManager) {
        this.screenManager = screenManager;
        this.assetManager = assetManager;

        spriteBatch = new SpriteBatch();
        loadAssets();
    }
    private void loadAssets(){
        assetManager.load(Constants.Paths.ScreensTexturesPath.MAIN_MENU_BACKGROUND, Texture.class);
        assetManager.finishLoading();
        backgroundTexture = assetManager.get(Constants.Paths.ScreensTexturesPath.MAIN_MENU_BACKGROUND, Texture.class);
    }

    @Override
    public void show() {
        GlobalUtils.consoleLog("Calling show method in main menu screen");

        elapsedTime = 0;
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        // Clear the screen
        drawBackground();
    }

    @Override
    public void dispose() {
        GlobalUtils.consoleLog("Calling dispose method of MainMenuScreen");
        assetManager.unload(Constants.Paths.ScreensTexturesPath.MAIN_MENU_BACKGROUND);
    }

    @Override
    public boolean keyDown(int keycode) {
        // Check for input and switch screens accordingly
        switch (keycode) {
            case Input.Keys.NUM_2:
                screenManager.showConfiguration();
                break;
            case Input.Keys.NUM_3:
                screenManager.showLevelSelector();
                break;
            default:
                return false;
        }
        return true;
    }

    private void drawBackground() {
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture,
             0,0,
            com.badlogic.gdx.Gdx.graphics.getWidth(), com.badlogic.gdx.Gdx.graphics.getHeight());
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Handle resizing if necessary
    }

    @Override
    public void pause() {
        // Handle game pause if necessary
    }

    @Override
    public void resume() {
        // Handle game resume if necessary
    }

    @Override
    public void hide() {

    }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }
}
