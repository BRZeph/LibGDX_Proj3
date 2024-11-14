package me.BRZeph.entities.Towers.TargetingSystem;

import me.BRZeph.entities.monster.Monster;

import java.util.Comparator;
import java.util.List;

public class FirstMonsterStrategy implements TargetingStrategy {
    @Override
    public Monster selectTarget(List<Monster> monstersInRange) {
        return monstersInRange.stream()
            .filter(monster -> monster.getIncomingDamage() <= monster.getCurrentHealth())
            .min(Comparator.comparing(Monster::getDistanceToEnd))
            .orElse(null);
    }
    @Override
    public String toString() {
        return "Targetting system -> FirstMonsterStrategy";
    }
}
