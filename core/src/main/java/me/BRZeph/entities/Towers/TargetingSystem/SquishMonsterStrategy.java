package me.BRZeph.entities.Towers.TargetingSystem;

import me.BRZeph.entities.monster.Monster;

import java.util.Comparator;
import java.util.List;

public class SquishMonsterStrategy implements TargetingStrategy {
    @Override
    public Monster selectTarget(List<Monster> monstersInRange) {
        return monstersInRange.stream()
            .min(Comparator.comparingDouble(Monster::getCurrentHealth))
            .orElse(null);
    }
}
