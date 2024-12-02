package me.BRZeph.entities.Map;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.Main;

import java.util.Objects;

import static me.BRZeph.core.Assets.BasicAssetsManager.getTileStateTexture;
import static me.BRZeph.utils.Constants.Paths.TilesTexturesPath.*;
import static me.BRZeph.utils.Constants.Paths.TowersTexturesPath.*;

public class TileType {
    public static final TileType GRASS = new TileType(GRASS_TILE_IDLE, true, false, false);
    public static final TileType WATER = new TileType(WATER_TILE_IDLE, false, true, false);
    public static final TileType STARTING_POINT = new TileType(STARTING_POINT_TILE_IDLE, true, false, false);
    public static final TileType ENDING_POINT = new TileType(ENDING_POINT_TILE_IDLE, true, false, false);
    public static final TileType HERO_ROOM_WALL = new TileType(HERO_WALL_TILE_IDLE,false, false, false);
    public static final TileType HERO_ROOM_CORNER = new TileType(HERO_CORNER_TILE_IDLE,false, false, false);
    public static final TileType HERO_ROOM_FLOOR = new TileType(HERO_FLOOR_TILE_IDLE,false, false, false);

    public static final TileType ARCHER_TOWER = new TileType(ARCHER_TOWER_PLACED, false, false, true);
    public static final TileType CANNON_TOWER = new TileType(CANNON_TOWER_PLACED, false, false, true);
    public static final TileType LIGHTNING_TOWER = new TileType(LIGHTNING_TOWER_PLACED, false, false, true);

    /*
    WHEN CREATING A NEW TILE TYPE:
    -> put the conversion in the getTileByNumber method.
    -> put the textures in the AdvancedAssetsManager class.
     */

    private Texture texture;
    public final boolean isWalkable;
    public final boolean isBuildable;
    public final boolean isTurret;
    private final String textureFilePath;
    private String state;
    private TileType originalType;
    private String facingDirection;
    private static final String standardFacingDirectionStr = "up";
    private static final int standardFacingDirectionInt = 0;
    private final Texture facingUpTexture;

    private TileType(String textureFilePath, boolean isWalkable, boolean isBuildable, boolean isTurret) {
        this.facingUpTexture = Main.getAssetManager().get(textureFilePath);
        this.texture = Main.getAssetManager().get(textureFilePath);
        this.isWalkable = isWalkable;
        this.isBuildable = isBuildable;
        this.isTurret = isTurret;
        this.textureFilePath = textureFilePath;
        this.state = "idle";
        this.facingDirection = "up"; // Standard direction = up.
    }
    /*
                /// HOW TO USE state VARIABLE \\\

    if (Objects.equals(tile.getType().getState(), "idle")) { // REMOVE LATER
        tile.getType().changeTileState("fire"); // REMOVE LATER
    } else if (Objects.equals(tile.getType().getState(), "fire")) { // REMOVE LATER
        tile.getType().changeTileState("idle"); // REMOVE LATER
    }
    by changing the state with changeTileState() it automatically changes the texture based off the AdvancedAssetsManager hmap.



                /// HOW TO USE facingDirection VARIABLE \\\

    |-  up left corner -> faces right
    -| up right corner -> faces down
    _| down right corner -> faces left
    |_ down left corner -> faces up

    by standard all facingDirections are initialized as up.
    facing up -> rotate 0 degrees
    facing right -> rotate 90 degrees
    facing down -> rotate 180 degrees
    facing left -> rotate 270 degrees

    when changing the facing direction, getStandardFacingDirection() -> rotate from there.


                /// HOW TO USE Tile numbers \\\

    -> 0 ~ 100: regular ground tiles
    -> 900 ~ 999: hero room.
    -> 1000 ~ 1500: towers (make upgraded towers different tiles? maybe).
     */

    public static TileType getTileByNumber(int tile){
        switch (tile){
            case 0:
                return GRASS;
            case 1:
                return WATER;
            case 2:
                return STARTING_POINT;
            case 3:
                return ENDING_POINT;
            case 900:
                return HERO_ROOM_WALL;
            case 901:
                return HERO_ROOM_CORNER;
            case 902:
                return HERO_ROOM_FLOOR;
            case 1000:
                return ARCHER_TOWER;
            case 1001:
                return LIGHTNING_TOWER;
            case 1002:
                return CANNON_TOWER;
            default:
                throw new IllegalArgumentException("Illegal tile value -> " + tile);
        }
    }

    public void updateTileStateTexture(String state){
        setTexture(getTileStateTexture(getOriginalType(), state));
        this.state = state;
    }

    public TileType cloneType(TileType type) {
        TileType clonedType = new TileType(type.textureFilePath, type.isWalkable, type.isBuildable, type.isTurret);
        clonedType.setOriginalType(type);
        return clonedType;
    }

    @Override
    public String toString() {
        return "TileType{" +
            "isWalkable=" + isWalkable +
            ", isBuildable=" + isBuildable +
            ", isTurret=" + isTurret +
            '}';
    }

    /*
    facing up -> rotate 0 degrees (reset texture)
    facing right -> rotate 90 degrees (reset and rotate)
    facing down -> rotate 180 degrees (reset and rotate)
    facing left -> rotate 270 degrees (reset and rotate)
     */

    public void setFacingDirection(String facingDirection) {
//        updateTileStateTexture(state);
        if (Objects.equals(facingDirection, "up")){
            rotateTexture(standardFacingDirectionInt);
        } else if (Objects.equals(facingDirection, "right")){
            rotateTexture(standardFacingDirectionInt);
            rotateTexture(90);
        } else if (Objects.equals(facingDirection, "down")){
            rotateTexture(standardFacingDirectionInt);
            rotateTexture(180);
        } else if (Objects.equals(facingDirection, "left")){
            rotateTexture(standardFacingDirectionInt);
            rotateTexture(270);
        }
        this.facingDirection = facingDirection;
    }

    private void rotateTexture(int i) {
        if (i == 0){
            texture = facingUpTexture;
            updateTileStateTexture(state); // Prevents losing tileState texture such as fire.
            return;
        }

        if (i != 90 && i != 180 && i != 270) {
            throw new IllegalArgumentException("Invalid rotation value for texture\n" +
                "rotation -> " + i + "\n" +
                "texture -> " + texture.toString());
        }

        // Prepare the original Pixmap
        texture.getTextureData().prepare();
        Pixmap originalPixmap = texture.getTextureData().consumePixmap();

        int originalWidth = originalPixmap.getWidth();
        int originalHeight = originalPixmap.getHeight();

        Pixmap rotatedPixmap = null;

        switch (i) {
            case 90:
                rotatedPixmap = new Pixmap(originalHeight, originalWidth, originalPixmap.getFormat());
                for (int x = 0; x < originalWidth; x++) {
                    for (int y = 0; y < originalHeight; y++) {
                        rotatedPixmap.drawPixel(originalHeight - 1 - y, x, originalPixmap.getPixel(x, y));
                    }
                }
                break;
            case 180:
                rotatedPixmap = new Pixmap(originalWidth, originalHeight, originalPixmap.getFormat());
                for (int x = 0; x < originalWidth; x++) {
                    for (int y = 0; y < originalHeight; y++) {
                        rotatedPixmap.drawPixel(originalWidth - 1 - x, originalHeight - 1 - y, originalPixmap.getPixel(x, y));
                    }
                }
                break;
            case 270:
                rotatedPixmap = new Pixmap(originalHeight, originalWidth, originalPixmap.getFormat());
                for (int x = 0; x < originalWidth; x++) {
                    for (int y = 0; y < originalHeight; y++) {
                        rotatedPixmap.drawPixel(y, originalWidth - 1 - x, originalPixmap.getPixel(x, y));
                    }
                }
                break;
        }
        Texture rotatedTexture = new Texture(rotatedPixmap);

        // Dispose of Pixmaps to prevent memory leaks
        originalPixmap.dispose();
        rotatedPixmap.dispose();

        texture = rotatedTexture;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getFacingDirection() {
        return facingDirection;
    }

    public TileType getOriginalType() {
        return originalType;
    }

    private void setOriginalType(TileType originalType) {
        this.originalType = originalType;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public boolean isBuildable() {
        return isBuildable;
    }

    public boolean isTurret() {
        return isTurret;
    }

    public boolean isStartingPoint() {
        return this.getOriginalType() == STARTING_POINT;
    }

    public boolean isEndingPoint() {
        return this.getOriginalType() == ENDING_POINT;
    }

    private void setTexture(Texture texture) {
        this.texture = texture;
    }

    public String getState() {
        return state;
    }

    public Texture getFacingUpTexture() {
        return facingUpTexture;
    }
}
