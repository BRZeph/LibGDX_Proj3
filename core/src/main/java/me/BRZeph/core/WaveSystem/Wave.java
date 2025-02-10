package me.BRZeph.core.WaveSystem;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.entities.monster.Monster;
import me.BRZeph.entities.monster.MonsterType;
import me.BRZeph.core.Map.Node;
import me.BRZeph.entities.monster.Variant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.BRZeph.utils.Constants.Constants.TIME_MULTIPLIER;
import static me.BRZeph.utils.GlobalUtils.consoleLog;

public class Wave {
    private Map<MonsterType, SpawnRule> spawnRules;
    private Map<MonsterType, List<SpawnBehavior>> behaviors;
    private List<Monster> monsterList;
    private List<Monster> monsterReachedEnd;
    private List<Monster> monsterDied;
    private List<Node> path;
    private List<SpawnRuleEndWave> endBehavior;
    private float waveClock;
    private boolean active;
    private HashMap<MonsterType, Integer> monstersCount;

    public Wave(Map<MonsterType, List<SpawnBehavior>> behaviors, List<SpawnRuleEndWave> endBehavior, List<Node> path) {
        this.behaviors = behaviors;
        this.endBehavior = endBehavior;
        this.waveClock = 0;
        this.active = false;
        this.monsterList = new ArrayList<>();
        this.monsterReachedEnd = new ArrayList<>();
        this.monsterDied = new ArrayList<>();
        this.path = path;
        this.spawnRules = new HashMap<>();
        initializeMonstersCount();
        initializeSpawnRules();
    }

    private void initializeMonstersCount() {
        this.monstersCount = new HashMap<>();
        for (MonsterType type : MonsterType.values()) {
            monstersCount.put(type, 0);
        }
    }

    private void initializeSpawnRules() {
        for (SpawnRuleEndWave ruleEndWave : endBehavior){
            MonsterType type = ruleEndWave.getMonsterType();
            SpawnRule defaultRule = new SpawnRule(type, 0, 0, 10000, Variant.NORMAL);
            spawnRules.put(type, defaultRule);
        }
    }

    public void update(float delta, int waveIndex) {
        if (active) {
            waveClock += delta * TIME_MULTIPLIER;
            updateSpawnRules();
            spawnMonsters(delta, waveIndex);
            updateMonsters(delta);
        }
    }

    private void updateSpawnRules() {
        for (Map.Entry<MonsterType, SpawnRule> entry : spawnRules.entrySet()) {
            MonsterType type = entry.getKey();
            SpawnRule rule = entry.getValue();

            if (behaviors.containsKey(type)) {
                for (SpawnBehavior behavior : behaviors.get(type)) {
                    if (waveClock >= behavior.getTriggerTime() && !behavior.isBehaviorApplied()) {
                        rule.setSpawnInterval(behavior.getNewInterval());
                        rule.setAmountPerSpawn(behavior.getAmountPerSpawn());
                        rule.setVariant(behavior.getVariant());
                        behavior.setBehaviorApplied(true);
                    }
                }
            }

            for (SpawnRuleEndWave endRule : endBehavior) {
                if (endRule.getMonsterType() != type) {
                    continue;
                }
                if (waveClock >= endRule.getStartTime() && !endRule.isAppliedBehavior()) {
                    rule.setSpawnInterval(endRule.getSpawnInterval());
                    rule.setAmountPerSpawn(endRule.getAmountPerSpawn());
                    rule.setVariant(endRule.getVariant());
                    endRule.setAppliedBehavior(true);
                }
                if (waveClock >= endRule.getTimeLimit()) {
                    endRule.setEndBehavior(true);
                    rule.setSpawnInterval(100000);
                }
            }
        }
        checkEndWaveConditions();
    }

    private void checkEndWaveConditions() {
        boolean endWave = true;
        for (SpawnRuleEndWave ruleEndWave : endBehavior) {
            if (waveClock > ruleEndWave.getTimeLimit()){
                ruleEndWave.setEndBehavior(true);
                ruleEndWave.setSpawnInterval(100000);
            }
            if (!ruleEndWave.isEndBehavior()) {
                endWave = false;
                break;
            }
        }
        if (endWave && monsterList.isEmpty()) {
            finishedWave();
        }
    }

    private void spawnMonsters(float delta, int waveIndex) {
        for (Map.Entry<MonsterType, SpawnRule> entry : spawnRules.entrySet()) {
            MonsterType type = entry.getKey();
            SpawnRule rule = entry.getValue();

            rule.setRuleClock(rule.getRuleClock() + delta * TIME_MULTIPLIER);

            if (rule.isFirstSpawn() && waveClock > rule.getStartTime()) {
                rule.setRuleClock(10000);
                rule.setFirstSpawn(false);
            }

            if (rule.getRuleClock() >= rule.getSpawnInterval() && !rule.isFirstSpawn()) {
                rule.setRuleClock(0);
                for (int i = 0; i < rule.getAmountPerSpawn(); i++) {
                    spawnMonster(new Monster(path, type, rule.getVariant(), waveIndex));
                }
            }

//            if (rule.isFirstSpawn()) {
//                if (waveClock > rule.getStartTime()) {
//                    rule.setRuleClock(0);
//                    rule.setFirstSpawn(false);
//                }
//            } else if (rule.getRuleClock() >= rule.getSpawnInterval()) {
//                rule.setRuleClock(0);
//                for (int i = 0; i < rule.getAmountPerSpawn(); i++) {
//                    spawnMonster(new Monster(path, type, rule.getVariant(), waveIndex));
//                }
//            }
        }
    }

    public void spawnMonster(Monster monster){
        monsterList.add(monster);
        monstersCount.put(monster.getType(), monstersCount.get(monster.getType()) + 1);
    }

    private void updateMonsters(float delta) {
        List<Monster> toRemove = new ArrayList<>();
        for (Monster monster : monsterList) {
            monster.update(delta, path);
            if (monster.isFinishedPath()) {
                monsterReachedEnd.add(monster);
                toRemove.add(monster);
            } else if (monster.getCurrentHealth() <= 0) {
                monsterDied.add(monster);
                toRemove.add(monster);
            }
        }
        monsterList.removeAll(toRemove);
    }

    public void render(SpriteBatch batch, BitmapFont font, ShapeRenderer shapeRenderer, float delta) {
        for (Monster monster : monsterList) {
            monster.render(batch, font, shapeRenderer, delta);
        }
    }

    private void finishedWave() {
        active = false;
        consoleLog("[INFO] Finished wave");
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

    public List<Monster> getMonsterList() {
        return monsterList;
    }

    public List<Monster> getMonsterReachedEnd() {
        return monsterReachedEnd;
    }

    public List<Monster> getMonsterDied() {
        return monsterDied;
    }

    public HashMap<MonsterType, Integer> getMonstersCount() {
        return monstersCount;
    }
}
