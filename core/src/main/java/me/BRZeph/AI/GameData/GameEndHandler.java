package me.BRZeph.AI.GameData;


import me.BRZeph.AI.GameData.Data.TowerData;
import me.BRZeph.AI.GameData.Data.WaveData;
import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.WaveSystem.Wave;
import me.BRZeph.entities.monster.MonsterType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEndHandler {
    private List<Tower> towersList;
    private List<Wave> waveList;

    public GameEndHandler(List<Tower> towersList, List<Wave> wavesList) {
        this.towersList = towersList;
        this.waveList = wavesList;
    }

    public List<TowerData> collectTowerData() {
        List<TowerData> towerDataList = new ArrayList<>();

        for (Tower tower : towersList) {
            TowerData towerData = new TowerData(
                tower.getType(), tower.getxPos(), tower.getyPos(), tower.getDamageDealt(), tower.getKills(), tower.getShotsFired(), tower.getWavePlaced()
            );
            towerData.recordDamage(tower.getDamageDealt());
            towerData.recordKill(tower.getKills());

            towerDataList.add(towerData);
        }

        return towerDataList;
    }

    public List<WaveData> collectWaveData() {
        List<WaveData> waveDataList = new ArrayList<>();

        for (Wave wave : waveList) {
            WaveData waveData = new WaveData(waveList.indexOf(wave) + 1);

            HashMap<MonsterType, Integer> monstersCount = wave.getMonstersCount();

            for (Map.Entry<MonsterType, Integer> entry : monstersCount.entrySet()) {
                waveData.addMonstersSpawned(entry.getKey(), entry.getValue());
            }

            waveDataList.add(waveData);
        }

        return waveDataList;
    }
}
