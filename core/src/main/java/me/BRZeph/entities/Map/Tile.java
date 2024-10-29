package me.BRZeph.entities.Map;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
    private TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public boolean isWalkable() {
        return type.isWalkable();
    }

    public boolean isCollidable() {
        return type.isCollidable();
    }

    public Texture getTexture() {
        return type.texture; // Get texture from enum
    }

    public boolean isStartingPoint(){
        return type.isStartingPoint();
    }

    public boolean isEndingPoint(){
        return type.isEndingPoint();
    }
}
