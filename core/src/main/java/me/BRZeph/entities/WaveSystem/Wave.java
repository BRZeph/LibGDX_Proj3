package me.BRZeph.entities.WaveSystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.entities.Monster;
import me.BRZeph.utils.GlobalUtils;
import me.BRZeph.utils.enums.MonsterType;
import me.BRZeph.utils.pathFinding.Node;

import java.util.ArrayList;
import java.util.List;

public class Wave {
    private int waveNumber;
    private int warriorCount;
    private int skeletonCount;
    private float warriorSpawnInterval; // in seconds
    private float warriorClock;
    private float skeletonSpawnInterval; // in seconds
    private float skeletonClock;
    private float waveClock; // to keep track of spawn time
    private boolean isActive;
    private List<Monster> monsters;
    private List<Monster> monsterReachedEndList;
    private List<Monster> monsterDied;

    public Wave(int waveNumber) {
        this.waveNumber = waveNumber;
        this.warriorCount = waveNumber * 2;
        this.skeletonCount = waveNumber;
        this.warriorSpawnInterval = 4f;
        this.skeletonSpawnInterval = 2f;
        this.waveClock = 0f;
        this.warriorClock = 4;
        this.skeletonClock = 4;
        this.isActive = false;
        this.monsters = new ArrayList<>();
        this.monsterReachedEndList = new ArrayList<>();
        this.monsterDied = new ArrayList<>();
    }

    public void start() {
        isActive = true;
    }

    public void update(float delta) {
//        GlobalUtils.consoleLog("Updating wave " + waveNumber);
        if (!isActive) return;

        waveClock += delta;
        warriorClock += delta;
        skeletonClock += delta;
        GlobalUtils.consoleLog("active wave" + waveNumber);
        // Spawn warriors
        if (warriorClock >= warriorSpawnInterval && waveClock < 16) {
            GlobalUtils.consoleLog("spawning monster");
            monsters.add(new Monster(0, 0, MonsterType.ZOMBIE)); // Adjust position as necessary
            warriorClock -= warriorSpawnInterval; // Reset timer
        }

        // Spawn skeletons
        if (skeletonClock >= skeletonSpawnInterval && waveClock < 16) {
            monsters.add(new Monster(0, 0, MonsterType.SKELETON)); // Adjust position as necessary
            skeletonClock -= skeletonSpawnInterval; // Reset timer
        }

        // Check if all monsters are defeated
        if (monsters.isEmpty() && waveClock > 30) {
            isActive = false; // Wave is complete
        }
    }

    public void render(SpriteBatch batch, float delta, List<Node> path, BitmapFont font, ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        for (Monster monster : monsters) {
            monster.update(delta, path);
            monster.render(batch, font, shapeRenderer, camera);
            if (monster.isFinishedPath()){
                monsterReachedEndList.add(monster);
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public float getWaveClock() {
        return waveClock;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Monster> getMonsterDied() {
        return monsterDied;
    }

    public List<Monster> getMonsterReachedEndList() {
        return monsterReachedEndList;
    }
}
