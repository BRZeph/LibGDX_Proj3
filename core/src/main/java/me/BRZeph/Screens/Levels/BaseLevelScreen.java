package me.BRZeph.Screens.Levels;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import me.BRZeph.core.ScreenManager;

public abstract class BaseLevelScreen implements Screen {
    protected final AssetManager assetManager;
    protected final ScreenManager screenManager;

    public BaseLevelScreen(ScreenManager screenManager, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
    }

    protected abstract void loadAssets(); // Each level implements this

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
