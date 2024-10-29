package me.BRZeph.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.BRZeph.utils.enums.TowerType;

import java.util.List;

public class Tower {
    private float x, y;
    private TowerType type;

    public Tower(float x, float y, TowerType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void shoot(List<Monster> monsters) {
        // Logic to shoot at monsters
    }

    public void render(SpriteBatch batch) {
        batch.draw(type.texture, x, y); // Render using texture from enum
    }
}
