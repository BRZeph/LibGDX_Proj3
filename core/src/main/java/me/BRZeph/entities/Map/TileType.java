package me.BRZeph.entities.Map;

import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.Main;
import me.BRZeph.utils.Constants;

public enum TileType {
    GRASS(Main.getAssetManager().get(Constants.Paths.TilesTexturesPath.GRASS_TILE)),
    WATER(Main.getAssetManager().get(Constants.Paths.TilesTexturesPath.WATER_TILE)),
    STARTING_POINT(Main.getAssetManager().get(Constants.Paths.TilesTexturesPath.STARTING_POINT)),
    ENDING_POINT(Main.getAssetManager().get(Constants.Paths.TilesTexturesPath.ENDING_POINT)),
    ARCHER_TOWER(Main.getAssetManager().get(Constants.Paths.TowersTexturesPath.ARCHER_TOWER_PLACED));

    public final Texture texture;

    TileType(Texture texture) {
        this.texture = texture;
    }

    public boolean isWalkable() {
        if (this == GRASS || this == STARTING_POINT || this == ENDING_POINT){
            return true;
        }
        return false; // Example: only grass is walkable
    }

    public boolean isBuildable() {
        if (this == WATER){
            return true;
        }
        return false;
    }

    public boolean isStartingPoint(){
        return this == STARTING_POINT;
    }

    public boolean isEndingPoint(){
        return this == ENDING_POINT;
    }

    public boolean isTurret() {
        return this == ARCHER_TOWER;
    }
}
