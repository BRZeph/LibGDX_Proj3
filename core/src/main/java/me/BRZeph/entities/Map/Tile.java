package me.BRZeph.entities.Map;

import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.entities.Towers.PlacedTower;

public class Tile {
    private TileType type;
    private final TileType originalTileType; //use this to replace the tile when a tower is removed from the ground.
    private int x, y;

    public Tile(TileType type){
        this.type = type;
        this.originalTileType = type;
    }

    public boolean isWalkable() {
        return type.isWalkable();
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

    public boolean isBuildable(){
        return type.isBuildable();
    }

    public TileType getType() {
        return type;
    }

    public TileType getOriginalTileType() {
        return originalTileType;
    }

    public void setOriginalTileType(){
        type = originalTileType;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
