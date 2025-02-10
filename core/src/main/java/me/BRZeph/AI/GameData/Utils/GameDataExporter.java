package me.BRZeph.AI.GameData.Utils;


import me.BRZeph.AI.GameData.Data.TowerData;
import me.BRZeph.AI.GameData.Data.WaveData;
import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.monster.MonsterType;
import me.BRZeph.utils.GlobalUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static me.BRZeph.utils.Constants.Constants.AIValues.*;
import static me.BRZeph.utils.Constants.Constants.LocalPaths.GAME_DATA_EXPORT_PATH;

public class GameDataExporter {

    public void exportGameData(List<TowerData> towerDataList, List<WaveData> waveDataList, String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement("GameData");
            document.appendChild(rootElement);

            exportTowerData(towerDataList, document, rootElement);

            exportWaveData(waveDataList, document, rootElement);

            File file = getFile(fileName);

            try (FileWriter writer = new FileWriter(file)) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(new DOMSource(document), new StreamResult(writer));
            }

            GlobalUtils.consoleLog("[INFO]: Game data exported to: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportTowerData(List<TowerData> towerDataList, Document document, Element rootElement) {
        Element towersElement = document.createElement("Towers");
        rootElement.appendChild(towersElement);

        String towerDPS;

        for (TowerData towerData : towerDataList) {
            Element towerElement = document.createElement("Tower");
            towersElement.appendChild(towerElement);
            /*
            towerType node already has the information of cost and attackCooldown, consider removing it.
             */

            if (towerData.getShotsFired() != 0){
               towerDPS = String.valueOf(towerData.getDamageDealt() / ( towerData.getShotsFired() * towerData.getAttackCooldown()));
            } else {
                towerDPS = "----";
            }

            appendNodeChild(document, "Wave", String.valueOf(towerData.getWavePlaced() + 1), towerElement);

            appendNodeChild(document, "towerType", towerData.getTowerType().toString().split(" ")[0], towerElement);

            appendNodeChild(document, "position", towerData.getPositionX() + "," + towerData.getPositionY(), towerElement);

            appendNodeChild(document, "damageDealt", String.valueOf(towerData.getDamageDealt()), towerElement);

            appendNodeChild(document, "kills", String.valueOf(towerData.getKills()), towerElement);

            appendNodeChild(document, "Cost",
                "Gold: " + towerData.getGoldCost() + ", Essence: " + towerData.getEssenceCost() + ", Momentum: " + towerData.getMomentumCost()
                , towerElement);

            appendNodeChild(document, "ShotsFired", String.valueOf(towerData.getShotsFired()), towerElement);

            appendNodeChild(document, "AttackCooldown", String.valueOf(towerData.getAttackCooldown()), towerElement);

            appendNodeChild(document, "DPS", towerDPS, towerElement);  // DO NOT CHANGE THE DPS NAME, IT'LL CAUSE ISSUES WITH EXCEL EXPORTER.

            appendNodeChild(document, "Fitness", getFitness(towerData), towerElement);
        }
    }

    public static double getFitness(Tower tower){
        double damageDealt = tower.getDamageDealt();
        int kills = tower.getKills();
        double cost = tower.getType().getGoldCost() + tower.getType().getEssenceCost() + tower.getType().getMomentumCost();
        double dps = (tower.getShotsFired() != 0) ? tower.getDamageDealt() / ( tower.getShotsFired() * tower.getAttackCooldown()) : 0;
        int wavePlaced = tower.getWavePlaced();

        double fitness
            = (FITNESS_DAMAGE_WEIGHT * damageDealt)
            + (FITNESS_KILLS_WEIGHT * kills)
            + (FITNESS_DPS_WEIGHT * dps)
            + (FITNESS_WAVE_WEIGHT * wavePlaced)
            - (FITNESS_COST_WEIGHT * cost);

        return fitness;

    }

    public static String getFitness(TowerData towerData) {
        double damageDealt = towerData.getDamageDealt();
        int kills = towerData.getKills();
        double cost = towerData.getGoldCost() + towerData.getEssenceCost() + towerData.getMomentumCost();
        double dps = (towerData.getShotsFired() != 0) ? towerData.getDamageDealt() / ( towerData.getShotsFired() * towerData.getAttackCooldown()) : 0;
        int wavePlaced = towerData.getWavePlaced();

        double fitness
            = (FITNESS_DAMAGE_WEIGHT * damageDealt)
            + (FITNESS_KILLS_WEIGHT * kills)
            + (FITNESS_DPS_WEIGHT * dps)
            + (FITNESS_WAVE_WEIGHT * wavePlaced)
            - (FITNESS_COST_WEIGHT * cost);

        return String.valueOf(fitness);
    }

    private static void appendNodeChild(Document document, String name, String towerData, Element towerElement) {
        Element element = document.createElement(name);
        element.appendChild(document.createTextNode(towerData));
        towerElement.appendChild(element);
    }

    private void exportWaveData(List<WaveData> waveDataList, Document document, Element rootElement) {
        float gameGold = 0;
        float gameEssence = 0;
        float gameMomentum = 0;

        Element wavesElement = document.createElement("Waves");
        rootElement.appendChild(wavesElement);

        for (WaveData waveData : waveDataList) {
            Element waveElement = document.createElement("Wave");
            wavesElement.appendChild(waveElement);

            // Add wave number
            appendNodeChild(document, "waveNumber", String.valueOf(waveData.getWaveNumber()), waveElement);

            // Add MonstersSpawned
            Element monstersSpawnedElement = document.createElement("MonstersSpawned");
            waveElement.appendChild(monstersSpawnedElement);

            // Iterate through the monstersCount and create a sub-element for each monster type
            for (Map.Entry<MonsterType, Integer> entry : waveData.getMonstersSpawned().entrySet()) {
                if (entry.getValue() == 0) continue;
                Element monsterElement = document.createElement("Monster");
                monstersSpawnedElement.appendChild(monsterElement);

                appendNodeChild(document, "type", entry.getKey().toString(), monsterElement);
                appendNodeChild(document, "count", String.valueOf(entry.getValue()), monsterElement);

                // Add currency rewards for this monster type
                WaveData.RewardData rewardData = waveData.getRewardData().get(entry.getKey());
                if (rewardData != null) {
                    Element rewardElement = document.createElement("Rewards");
                    monsterElement.appendChild(rewardElement);

                    appendNodeChild(document, "gold", String.valueOf(rewardData.getGold()), rewardElement);
                    appendNodeChild(document, "essence", String.valueOf(rewardData.getEssence()), rewardElement);
                    appendNodeChild(document, "momentum", String.valueOf(rewardData.getMomentum()), rewardElement);
                    gameGold += rewardData.getGold();
                    gameEssence += rewardData.getEssence();
                    gameMomentum += rewardData.getMomentum();
                }
            }
            WaveData.RewardData totalRewards = waveData.getTotalRewards();
            Element totalRewardsElement = document.createElement("TotalRewards");
            waveElement.appendChild(totalRewardsElement);

            appendNodeChild(document, "gold", String.valueOf(totalRewards.getGold()), totalRewardsElement);
            appendNodeChild(document, "essence", String.valueOf(totalRewards.getEssence()), totalRewardsElement);
            appendNodeChild(document, "momentum", String.valueOf(totalRewards.getMomentum()), totalRewardsElement);
        }

        Element gameCurrency = document.createElement("GameCurrency");
        wavesElement.appendChild(gameCurrency);

        appendNodeChild(document, "gold", String.valueOf(gameGold), gameCurrency);
        appendNodeChild(document, "essence", String.valueOf(gameEssence), gameCurrency);
        appendNodeChild(document, "momentum", String.valueOf(gameMomentum), gameCurrency);
    }

    private File getFile(String fileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());

        File folder = new File(GAME_DATA_EXPORT_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String newFileName = date + "_" + fileName;
        String firstFileName = newFileName + "_0";
        int counter = 1;
        File file = new File(folder, firstFileName + ".xml");

        while (file.exists()) {
            newFileName = date + "_" + fileName + "_" + counter;
            file = new File(folder, newFileName + ".xml");
            counter++;
        }

        return file;
    }
}
