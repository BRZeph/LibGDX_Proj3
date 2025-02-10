package me.BRZeph.core.WaveSystem;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

import static me.BRZeph.utils.GlobalUtils.consoleLog;

public class WaveManager {
    private List<Wave> waves;
    private int currentWaveIndex;
    private boolean canStartNewWave;
    private boolean firstWave;
    private boolean finishedLevel;

    public WaveManager(List<Wave> waves) {
        this.waves = waves;
        this.currentWaveIndex = 0;
        this.canStartNewWave = true;
        this.firstWave = true;
        this.finishedLevel = false;
    }

    public void update(float delta) {
        if (currentWaveIndex == waves.size()){
            finishedLevel = true;
            // Beat the level.
            return;
        }

        if (!finishedLevel) {
            Wave currentWave = waves.get(currentWaveIndex);
            currentWave.update(delta, (currentWaveIndex + 1));

            if (!currentWave.isActive()) {
                canStartNewWave = true;
            }
        } else {
            consoleLog("[INFO]: Finished level :D");
        }
    }

    public void render(SpriteBatch batch, BitmapFont font, ShapeRenderer shapeRenderer, float delta){
        waves.get(currentWaveIndex).render(batch, font, shapeRenderer, delta);
    }

    public void startNextWave() {
        if (!canStartNewWave || finishedLevel) {
            return;
        }

        if (currentWaveIndex == waves.size() - 1){
            consoleLog("[INFO]: Waves size -> " + waves.size() + "\n" +
                "currentWaveIndex -> " + currentWaveIndex);
            finishedLevel = true;
            return;
        }

        if (!firstWave) {
            currentWaveIndex++;
        } else {
            firstWave = false;
        }

        waves.get(currentWaveIndex).start();
        canStartNewWave = false;
        consoleLog("[INFO]: starting next wave " + (currentWaveIndex + 1));
    }

    public List<Wave> getWaves() {
        return waves;
    }

    public Wave getCurrentWave() {
        return waves.get(currentWaveIndex);
    }

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }

    public boolean isCanStartNewWave() {
        return canStartNewWave;
    }

    public boolean isFirstWave() {
        return firstWave;
    }

    public boolean isFinishedLevel() {
        return finishedLevel;
    }
}
