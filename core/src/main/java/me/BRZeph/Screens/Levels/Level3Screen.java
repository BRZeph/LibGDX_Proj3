package me.BRZeph.Screens.Levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import me.BRZeph.Screens.ScreenManager;
import me.BRZeph.core.WaveSystem.WaveManager;

public class Level3Screen extends BaseLevelScreen{
    private float playerCurrentHealth;
    private WaveManager waveManager;
    public Level3Screen(ScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
    }

    @Override
    protected void loadAssets() {

    }
    @Override
    public boolean isPlayerDead() {
        return getPlayerCurrentHealth() <= 0;
    }

    public float getPlayerCurrentHealth() {
        return playerCurrentHealth;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }
    @Override
    public OrthographicCamera getCamera() {
        return null;
    }

    @Override
    public void show() {

    }

    @Override
    public void setAI(boolean value) {

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
}
