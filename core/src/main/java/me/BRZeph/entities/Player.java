package me.BRZeph.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.Towers.TowerItem;
import me.BRZeph.utils.Constants;

public class Player {
    private Vector2 position;
    private float range; // Interaction range
    private float speed; // Movement speed
    private TowerItem holdingItem;
    private TileType tileType;
    private boolean isHoldingDownShift, isHoldingDownControl;

    public Player(float x, float y, float range) {
        this.position = new Vector2(x, y);
        this.range = range;
        this.speed = Constants.AssetsPlayer.PLAYER_SPEED;
        this.holdingItem = null;
        this.tileType = null;
        this.isHoldingDownShift = false;
        this.isHoldingDownControl = false;
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

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public float getRange() {
        return range;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(position.x, position.y, Constants.AssetsPlayer.PLAYER_WIDTH, Constants.AssetsPlayer.PLAYER_HEIGHT); // Drawing a 32x32 grey square
        shapeRenderer.end();
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getWidth(){
        return Constants.AssetsPlayer.PLAYER_WIDTH;
    }

    public float getHeight(){
        return Constants.AssetsPlayer.PLAYER_HEIGHT;
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
