package me.BRZeph.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.entities.WaveSystem.Wave;
import me.BRZeph.utils.GlobalUtils;

import java.util.List;

public class WaveManager {
    private List<Wave> waves;
    private int currentWaveIndex;
    private boolean canStartNewWave;
    private boolean firstWave;

    public WaveManager(List<Wave> waves) {
        this.waves = waves;
        this.currentWaveIndex = 0;
        this.canStartNewWave = true;
        this.firstWave = true;
    }

    public void update(float delta) {
        if (currentWaveIndex < waves.size()) {
            Wave currentWave = waves.get(currentWaveIndex);
            currentWave.update(delta);

            if (!currentWave.isActive()) {
                canStartNewWave = true;
            }
        }
    }

    public void render(SpriteBatch batch, BitmapFont font, ShapeRenderer shapeRenderer){
        waves.get(currentWaveIndex).render(batch, font, shapeRenderer);
    }

    public void startNextWave() {
        if (canStartNewWave && currentWaveIndex < waves.size()) {
            if (!firstWave) {
                currentWaveIndex++;
            } else {
                firstWave = false;
            }
            waves.get(currentWaveIndex).start();
            canStartNewWave = false;
            GlobalUtils.consoleLog("starting next wave " + (currentWaveIndex + 1));
        }
    }

    public Wave getCurrentWave() {
        return waves.get(currentWaveIndex);
    }

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }
}
