package me.BRZeph.Screens.MiscelaneousScreens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.BRZeph.Main;
import me.BRZeph.core.ScreenManager;
import me.BRZeph.utils.GlobalUtils;

public class LevelSelectorScreen implements Screen, InputProcessor {
    private final ScreenManager screenManager;
    private final AssetManager assetManager;


    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;

    public LevelSelectorScreen(ScreenManager screenManager, AssetManager assetManager){
        this.screenManager = screenManager;
        this.assetManager = assetManager;

        spriteBatch = Main.getSpriteBatch();
        loadAssets();
    }

    private void loadAssets(){
        GlobalUtils.consoleLog("Calling loadAssets method of LevelSelectorScreen");

        assetManager.load("Screens/LevelsScreen/LevelSelectorScreen_bg.png", Texture.class);
        assetManager.finishLoading();
        backgroundTexture = assetManager.get("Screens/LevelsScreen/LevelSelectorScreen_bg.png", Texture.class);
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
        assetManager.unload("Screens/LevelsScreen/LevelSelectorScreen_bg.png");
    }

    private void drawBackground() {
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture,
            (float) -com.badlogic.gdx.Gdx.graphics.getWidth() /2, (float) -com.badlogic.gdx.Gdx.graphics.getHeight() /2,
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
