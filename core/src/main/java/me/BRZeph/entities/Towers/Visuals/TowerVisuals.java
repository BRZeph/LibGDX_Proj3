package me.BRZeph.entities.Towers.Visuals;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.BRZeph.entities.Towers.TowerType;

import java.util.HashMap;
import java.util.Map;

import static me.BRZeph.entities.Towers.Visuals.TowerVisualsFactory.keyBuilder;

public class TowerVisuals {
    private final Map<String, Animation<TextureRegion>> animations;

    public TowerVisuals() {
        this.animations = new HashMap<>();
    }

    public void addAnimation(String key, Animation<TextureRegion> animation) {
        animations.put(key, animation);
    }

    public Animation<TextureRegion> getAnimation(TowerType towerType, int path, int tier, String state) {
        String key = keyBuilder(towerType, path, tier, state);
        return animations.getOrDefault(key, null);
    }
}

