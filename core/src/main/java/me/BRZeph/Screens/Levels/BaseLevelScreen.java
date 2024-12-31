package me.BRZeph.Screens.Levels;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import me.BRZeph.core.Managers.ScreenManager;
import me.BRZeph.core.Managers.WaveManager;

public abstract class BaseLevelScreen implements Screen {
    protected final AssetManager assetManager;
    protected final ScreenManager screenManager;

    public BaseLevelScreen(ScreenManager screenManager, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
    }

    public abstract void setAI(boolean value);

    protected abstract void loadAssets();

    public abstract boolean isPlayerDead();

    public abstract OrthographicCamera getCamera();

    public abstract WaveManager getWaveManager();

    @Override
    public abstract void show();

    @Override
    public abstract void render(float v);

    @Override
    public abstract void resize(int i, int i1);

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public abstract void hide();

    @Override
    public abstract void dispose();
}
