package me.BRZeph.entities.Map;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
    private TileType type;
    private int x, y;

    public Tile(TileType type){
        this.type = type.cloneType(type);
    }

    public boolean isWalkable() {
        return type.isWalkable();
    }

    public Texture getTexture() {
        return type.getTexture();
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
