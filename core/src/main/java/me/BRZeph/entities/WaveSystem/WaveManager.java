package me.BRZeph.entities.WaveSystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.utils.GlobalUtils;
import me.BRZeph.utils.pathFinding.Node;

import java.util.List;

public class WaveManager {
    private int currentWaveIndex;
    private int totalWaves;
    private Wave[] waves;
    private boolean canStartNewWave;

    public WaveManager(int totalWaves) {
        waves = new Wave[totalWaves];
        for (int i = 0; i < totalWaves; i++) {
            waves[i] = new Wave(i + 1);
        }
        currentWaveIndex = 0;
        canStartNewWave = true;
        this.totalWaves = totalWaves;
    }

    public void update(float delta) {
        if (currentWaveIndex < waves.length) {
            waves[currentWaveIndex].update(delta);
            if (!waves[currentWaveIndex].isActive()) {
                canStartNewWave = true; // Allow starting the next wave
            }
        }
    }

    public void startNextWave() {
        if (canStartNewWave && currentWaveIndex < waves.length) {
            currentWaveIndex++;
            waves[currentWaveIndex].start();
            canStartNewWave = false;
        }
    }

    public void render(SpriteBatch batch, float delta, List<Node> path, BitmapFont font, ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        if (currentWaveIndex < waves.length) {
            waves[currentWaveIndex].render(batch, delta, path, font, shapeRenderer, camera);
        }
    }

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }

    public int getTotalWaves() {
        return totalWaves;
    }

    public Wave getCurrentWave(){
        return waves[currentWaveIndex];
    }
}
