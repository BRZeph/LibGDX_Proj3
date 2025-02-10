package me.BRZeph.Screens.MiscelaneousScreens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import me.BRZeph.Screens.Levels.BaseLevelScreen;
import me.BRZeph.Screens.ScreenManager;
import me.BRZeph.core.WaveSystem.WaveManager;

public class UIDevelopment extends BaseLevelScreen implements InputProcessor {
    public UIDevelopment(ScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

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

    @Override
    protected void loadAssets() {

    }

    @Override
    public void setAI(boolean value) {

    }

    @Override
    public boolean isPlayerDead() {
        return false;
    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }

    @Override
    public WaveManager getWaveManager() {
        return null;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
