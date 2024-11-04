package me.BRZeph.entities.Towers.TargetingSystem;

import me.BRZeph.entities.monster.Monster;

import java.util.List;

public interface TargetingStrategy {
    Monster selectTarget(List<Monster> monstersInRange);
}
