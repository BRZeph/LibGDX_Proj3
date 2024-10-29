package me.BRZeph.Screens.MiscelaneousScreens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.BRZeph.Main;
import me.BRZeph.core.ScreenManager;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;

public class LevelSelectorScreen implements Screen, InputProcessor {
    private final ScreenManager screenManager;
    private final AssetManager assetManager;

    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;

    public LevelSelectorScreen(ScreenManager screenManager, AssetManager assetManager){
        this.screenManager = screenManager;
        this.assetManager = assetManager;

        spriteBatch = new SpriteBatch();
        loadAssets();
    }

    private void loadAssets(){
        GlobalUtils.consoleLog("Calling loadAssets method of LevelSelectorScreen");

        assetManager.load(Constants.Paths.ScreensTexturesPath.LEVEL_SELECTOR_BACKGROUND, Texture.class);
        assetManager.finishLoading();
        //only 1 asset to be loaded, so I decided to load it in here and not on the asset manager.

        backgroundTexture = assetManager.get(Constants.Paths.ScreensTexturesPath.LEVEL_SELECTOR_BACKGROUND, Texture.class);
    }

    @Override
    public void show() {
        GlobalUtils.consoleLog("Calling show method of LevelSelectorScreen");

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
        GlobalUtils.consoleLog("Calling dispose method of LevelSelectorScreen");
        assetManager.unload(Constants.Paths.ScreensTexturesPath.LEVEL_SELECTOR_BACKGROUND);
    }

    private void drawBackground() {
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture,
            0,0,
            com.badlogic.gdx.Gdx.graphics.getWidth(), com.badlogic.gdx.Gdx.graphics.getHeight());
        spriteBatch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_5){

            //the keycode count starts at 8, not 0, so it's necessary to remove NUM_1 and add 1 to the keycode to get the levelNumber
            int levelNumber = keycode - Input.Keys.NUM_1 + 1;

            screenManager.showLevel(levelNumber);
        }

        return true;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
