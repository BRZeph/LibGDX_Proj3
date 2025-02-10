package me.BRZeph.core.WaveSystem;

import me.BRZeph.core.Map.Node;
import me.BRZeph.entities.monster.MonsterType;
import me.BRZeph.entities.monster.Variant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.BRZeph.utils.Constants.Constants.Paths.WAVE_LIST_FILE_PATH;
import static me.BRZeph.utils.GlobalUtils.consoleLog;

public class WaveImport {

    public static List<Wave> getWaves(List<Node> path) {
        List<Wave> waves = new ArrayList<>();
        StringBuilder jsonContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(WAVE_LIST_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();
            jsonString = jsonString.trim();
            jsonString = removeCommentFields(jsonString);
            if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
                String[] waveDataArray = splitJsonArray(jsonString);

                for (String waveData : waveDataArray) {
                    waveData = waveData.trim();

                    Map<MonsterType, List<SpawnBehavior>> spawnBehaviors = parseSpawnBehaviors(waveData);
                    List<SpawnRuleEndWave> endWaveBehaviors = parseEndWaveBehaviors(waveData);

                    waves.add(new Wave(spawnBehaviors, endWaveBehaviors, path));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        consoleLog("Waves size -> " + waves.size());
        return waves;
    }

    private static String removeCommentFields(String json) {
        return json.replaceAll("\"comment\"\\s*:\\s*\"[^\"]*\"\\s*,?", "");
    }

    private static String[] splitJsonArray(String jsonArray) {
        List<String> items = new ArrayList<>();
        int braceCount = 0;
        int startIndex = 0;

        for (int i = 0; i < jsonArray.length(); i++) {
            char currentChar = jsonArray.charAt(i);

            if (currentChar == '{') {
                if (braceCount == 0) {
                    startIndex = i;
                }
                braceCount++;
            } else if (currentChar == '}') {
                braceCount--;
                if (braceCount == 0) {
                    items.add(jsonArray.substring(startIndex, i + 1));
                }
            }
        }

        return items.toArray(new String[0]);
    }

    private static Map<MonsterType, List<SpawnBehavior>> parseSpawnBehaviors(String waveData) {
        Map<MonsterType, List<SpawnBehavior>> spawnBehaviors = new HashMap<>();
        String spawnBehaviorsData = extractJsonArray(waveData, "spawnBehaviors");

        if (spawnBehaviorsData != null) {
            String[] spawnItems = splitJsonArray(spawnBehaviorsData);
            for (String spawnItem : spawnItems) {
                spawnItem = spawnItem.trim();
                String[] parts = spawnItem.split(",");
                String monsterTypeStr = parts[0].split(":")[1].trim().replace("\"", "");
                MonsterType monsterType = MonsterType.valueOf(monsterTypeStr);
                int amountPerSpawn = Integer.parseInt(parts[1].split(":")[1].trim());
                float startTime = Float.parseFloat(parts[2].split(":")[1].trim());
                float spawnInterval = Float.parseFloat(parts[3].split(":")[1].trim());
                String variantStr = parts[4].split(":")[1].trim().replace("\"", "");
                variantStr = variantStr.replaceAll("[\\s\\]}]", "").toUpperCase().trim();
                Variant variant = Variant.valueOf(variantStr);
                SpawnBehavior behavior = new SpawnBehavior(amountPerSpawn, startTime, spawnInterval, variant);
                spawnBehaviors.computeIfAbsent(monsterType, k -> new ArrayList<>()).add(behavior);
            }
        }

        return spawnBehaviors;
    }

    private static List<SpawnRuleEndWave> parseEndWaveBehaviors(String waveData) {
        List<SpawnRuleEndWave> endWaveBehaviors = new ArrayList<>();
        String endWaveBehaviorsData = extractJsonArray(waveData, "endWaveBehaviors");

        if (endWaveBehaviorsData != null) {
            String[] endWaveItems = splitJsonArray(endWaveBehaviorsData);
            for (String endWaveItem : endWaveItems) {
                endWaveItem = endWaveItem.trim();
                String[] parts = endWaveItem.split(",");
                String monsterTypeStr = parts[0].split(":")[1].trim().replace("\"", "");
                MonsterType monsterType = MonsterType.valueOf(monsterTypeStr);
                int amountPerSpawn = Integer.parseInt(parts[1].split(":")[1].trim());
                float startTime = Float.parseFloat(parts[2].split(":")[1].trim());
                float spawnInterval = Float.parseFloat(parts[3].split(":")[1].trim());
                float timeLimit = Float.parseFloat(parts[4].split(":")[1].trim());
                String variantStr = parts[5].split(":")[1].trim().replace("\"", "");
                variantStr = variantStr.replaceAll("[\\s\\]}]", "").toUpperCase().trim();
                Variant variant = Variant.valueOf(variantStr);
                endWaveBehaviors.add(new SpawnRuleEndWave(monsterType, amountPerSpawn, startTime, spawnInterval, timeLimit, variant));
            }
        }

        return endWaveBehaviors;
    }


    private static String extractJsonArray(String data, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = data.indexOf(searchKey);
        if (startIndex != -1) {
            int endIndex = data.indexOf("]", startIndex);
            if (endIndex != -1) {
                return data.substring(startIndex + searchKey.length(), endIndex + 1).trim();
            }
        }
        return null;
    }
}
