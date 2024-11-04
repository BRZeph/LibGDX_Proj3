package me.BRZeph.entities.WaveSystem;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.entities.monster.Monster;
import me.BRZeph.entities.monster.MonsterType;
import me.BRZeph.utils.pathFinding.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wave {
    private List<SpawnRule> spawnRules;
    private Map<MonsterType, List<SpawnBehavior>> behaviors;
    private List<Monster> monsterList;
    private List<Monster> monsterReachedEnd;
    private List<Monster> monsterDied;
    private List<Node> path;
    private List<SpawnRuleEndWave> endBehavior;
    private float waveClock;
    private boolean active;

    public Wave(List<SpawnRule> spawnRules, Map<MonsterType, List<SpawnBehavior>> behaviors,
                List<SpawnRuleEndWave> endBehavior, List<Node> path) {
        this.spawnRules = spawnRules;
        this.behaviors = behaviors;
        this.waveClock = 0;
        this.active = false;
        this.monsterList = new ArrayList<>();
        this.monsterReachedEnd = new ArrayList<>();
        this.monsterDied = new ArrayList<>();
        this.path = path;
        this.endBehavior = endBehavior;
    }

    public void update(float delta) {
        if (active) {
            waveClock += delta;
            spawnMonsters(delta);
            handleSpawnBehaviors();

            if (monsterList.isEmpty()) {
                return;
            }
            for (Monster monster : monsterList) {
                monster.update(delta, path);
                if (monster.isFinishedPath()){
                    monsterReachedEnd.add(monster);
                } else if (monster.getCurrentHealth() <= 0){
                    monsterDied.add(monster);
                }
            }
        }
    }

    public void render(SpriteBatch batch, BitmapFont font, ShapeRenderer shapeRenderer){
        for (Monster monster : monsterList) {
            monster.render(batch, font, shapeRenderer);
        }
    }

    private void handleSpawnBehaviors() {
        for (SpawnRule rule : spawnRules) {
            MonsterType type = rule.getMonsterType();

            if (!behaviors.containsKey(type)) {
                continue;
            }
            for (int i = 0; i < behaviors.get(type).size(); i++){
                SpawnBehavior behavior = behaviors.get(type).get(i);

                if (waveClock < behavior.getTriggerTime()) {
                    continue;
                }
                if (!behavior.isBehaviorApplied()) {
                    rule.setSpawnInterval(behavior.getNewInterval());
                    rule.setAmountPerSpawn(behavior.getAmountPerSpawn());
                    behavior.setBehaviorApplied(true);
                }
                if (i != behaviors.get(type).size() - 1) {
                    continue;
                }// this is here to make sure that all behaviors have been applied



                // ruleEndWave.
                for (SpawnRuleEndWave ruleEndWave : endBehavior){
                    if (ruleEndWave.getMonsterType() != type) {
                        continue;
                    }

                    if (!ruleEndWave.isAppliedBehavior() && waveClock > ruleEndWave.getStartTime()) {
                        rule.setSpawnInterval(ruleEndWave.getSpawnInterval());
                        rule.setAmountPerSpawn(ruleEndWave.getAmountPerSpawn());
                        ruleEndWave.setAppliedBehavior(true);
                        break;
                    }

                    if (waveClock > ruleEndWave.getTimeLimit()){
                        ruleEndWave.setEndBehavior(true);
                        rule.setSpawnInterval(100000);
                    }
                }
            }
        }
        boolean endWave = true;
        for (SpawnRuleEndWave ruleEndWave : endBehavior){
            if (!ruleEndWave.isEndBehavior()) {
                endWave = false;
                break;
            }
        }
        if (endWave && monsterList.isEmpty()) {
            active = false;
        }
    }

    private void spawnMonsters(float delta) {
        for (SpawnRule rule : spawnRules) {
            rule.setRuleClock(rule.getRuleClock() + delta);
            MonsterType type = rule.getMonsterType();

            if (rule.isFirstSpawn()){
                if (waveClock > rule.getStartTime()){
                    rule.setRuleClock(0);
                    rule.setFirstSpawn(false);
                } else {
                    break;
                }
            }
            if (rule.getRuleClock() - rule.getSpawnInterval() > 0 && !rule.isFirstSpawn()) {
                rule.setRuleClock(0);
                for (int i = 0; i < rule.getAmountPerSpawn(); i++) {
                    monsterList.add(new Monster(path.get(0).x, path.get(0).y, type));
                }
            }
        }
    }

    public void end() {
        this.active = false;
    }

    public void start() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public float getWaveClock() {
        return waveClock;
    }

    public List<SpawnRule> getSpawnRules() {
        return spawnRules;
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }

    public List<Monster> getMonsterReachedEnd() {
        return monsterReachedEnd;
    }

    public List<Monster> getMonsterDied() {
        return monsterDied;
    }
}
