package me.BRZeph.entities.Towers.TargetingSystem;

import me.BRZeph.entities.monster.Monster;
import me.BRZeph.utils.pathFinding.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FirstMonsterStrategy implements TargetingStrategy {
    @Override
    public Monster selectTarget(List<Monster> monstersInRange) {
        if (monstersInRange.isEmpty()) {
            return null; // No monsters in range
        }

        int minNodesLeft = getMinNodesLeft(monstersInRange);
        List<Monster> candidates = new ArrayList<>();

        // Gather candidates that have the minimum nodes left
        for (Monster monster : monstersInRange) {
            if (monster.getNodesLeft() == minNodesLeft && monster.getIncomingDamage() < monster.getCurrentHealth()) {
                candidates.add(monster);
            }
        }

        // If multiple candidates exist, choose the one closest to the next node
        if (candidates.isEmpty()) {
            return null; // Safety check
        }

        return getClosestMonsterToNextNode(candidates);
    }

    private int getMinNodesLeft(List<Monster> monsters) {
        int minNodesLeft = Integer.MAX_VALUE;

        for (Monster monster : monsters) {
            int nodesLeft = monster.getNodesLeft(); // Method to get nodes left for the monster
            if (nodesLeft < minNodesLeft && monster.getIncomingDamage() <= monster.getCurrentHealth()) {
                minNodesLeft = nodesLeft; // Update to the new minimum
            }
        }

        return minNodesLeft; // Return the minimum nodes left found
    }

    private Monster getClosestMonsterToNextNode(List<Monster> candidates) {
        Monster closestMonster = null;
        float closestDistance = Float.MAX_VALUE;

        for (Monster candidate : candidates) {
            float distanceToNextNode = candidate.calculateDistanceToNextNode(); // Implement this in your Monster class

            if (distanceToNextNode < closestDistance) {
                closestDistance = distanceToNextNode; // Update the closest distance
                closestMonster = candidate; // Update the closest monster
            }
        }

        return closestMonster; // Return the closest monster to the next node
    }
}
