package me.BRZeph.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import me.BRZeph.Main;
import me.BRZeph.core.Assets.AdvancedAssetsManager;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.Towers.TowerItem;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;

import static me.BRZeph.utils.Constants.Paths.Animations.PLAYER_WALK_ANIMATION;
import static me.BRZeph.utils.Constants.Paths.Animations.PLAYER_WALK_ANIMATION_NAME;
import static me.BRZeph.utils.Constants.Values.PlayerValues.*;

public class Player {
    private Vector2 position;
    private float range; // Interaction range
    private float speed; // Movement speed
    private TowerItem holdingItem;
    private TileType tileType;
    private boolean isHoldingDownShift, isHoldingDownControl;

    private float animationTime = 0;
    private Animation<TextureRegion> walkAnimation; // Walk animation

    public Player(float x, float y, float range) {
        this.position = new Vector2(x, y);
        this.range = range;
        this.speed = PLAYER_SPEED;
        this.holdingItem = null;
        this.tileType = null;
        this.isHoldingDownShift = false;
        this.isHoldingDownControl = false;

        this.walkAnimation = AdvancedAssetsManager.getAnimation(PLAYER_WALK_ANIMATION_NAME);
    }

    public void handlePlayerMovement(float delta, TileMap tileMap){
        float newX = position.x;
        float newY = position.y;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newY += speed * delta; // Move up
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= speed * delta; // Move down
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= speed * delta; // Move left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += speed * delta; // Move right
        }

        // Boundary checks
        if (newX < 0) {
            newX = 0; // Prevent moving left outside the map
        } else if (newX + getWidth() > tileMap.getWidth()) {
            newX = tileMap.getWidth() - getWidth(); // Prevent moving right outside the map
        }

        if (newY < 0) {
            newY = 0; // Prevent moving down outside the map
        } else if (newY + getHeight() > tileMap.getHeight()) {
            newY = tileMap.getHeight() - getHeight(); // Prevent moving up outside the map
        }

        // Update player's position if within boundaries
        setPosition(newX, newY);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void render(SpriteBatch batch, float delta) {
        animationTime += delta;

        TextureRegion currentFrame = walkAnimation.getKeyFrame(animationTime, true);

        batch.begin();
        batch.draw(
            currentFrame,
            position.x,
            position.y,
            PLAYER_WIDTH,
            PLAYER_HEIGHT
        );
        batch.end();
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getWidth(){
        return PLAYER_WIDTH;
    }

    public float getHeight(){
        return PLAYER_HEIGHT;
    }

    public TowerItem getHoldingItem() {
        return holdingItem;
    }

    public TileType getHoldingItemTileType(){
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public void setHoldingItem(TowerItem holdingItem) {
        this.holdingItem = holdingItem;
    }

    public boolean isHoldingDownShift() {
        return isHoldingDownShift;
    }

    public void setHoldingDownShift(boolean holdingDownShift) {
        isHoldingDownShift = holdingDownShift;
    }

    public boolean isHoldingDownControl() {
        return isHoldingDownControl;
    }

    public void setHoldingDownControl(boolean holdingDownControl) {
        isHoldingDownControl = holdingDownControl;
    }
}
