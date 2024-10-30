package me.BRZeph.entities.Towers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.BRZeph.entities.Monster;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.enums.TowerType;

import java.util.List;

public class PlacedTower {
    private float attackRange;
    private float damage;
    private float attackCooldown;
    private float cooldownClock;
    private float xPos;
    private float yPos;

    public PlacedTower(TowerType type, float xPos, float yPos) { //towers are rendered in the tiles.
        this.xPos = xPos;
        this.yPos = yPos;

        this.attackRange = TowerManager.getTowerRange(type);
        this.damage = TowerManager.getTowerDamage(type);
        this.attackCooldown = TowerManager.getTowerCooldown(type);
        this.cooldownClock = 0;
    }

    public void update(float delta) {

    }

    public void shoot(List<Monster> monsters) {

    }
}
