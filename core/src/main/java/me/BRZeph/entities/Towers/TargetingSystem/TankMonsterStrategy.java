package me.BRZeph.entities.Towers.TargetingSystem;

import me.BRZeph.entities.monster.Monster;

import java.util.Comparator;
import java.util.List;

public class TankMonsterStrategy implements TargetingStrategy {
    @Override
    public Monster selectTarget(List<Monster> monstersInRange) {
        return monstersInRange.stream()
            .filter(monster -> monster.getIncomingDamage() <= monster.getCurrentHealth())
            .max(Comparator.comparingDouble(Monster::getCurrentHealth))
            .orElse(null);
    }
    @Override
    public String toString() {
        return "Tank";
    }
}
