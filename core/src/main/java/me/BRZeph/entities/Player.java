package me.BRZeph.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.utils.Constants;

public class Player {
    private Vector2 position;
    private float range; // Interaction range
    private float speed; // Movement speed

    public Player(float x, float y, float range) {
        this.position = new Vector2(x, y);
        this.range = range;
        this.speed = Constants.AssetsPlayer.PLAYER_SPEED;
    }

    public void handleInput(float delta, TileMap tileMap){
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

    public void move(float deltaX, float deltaY) {
        position.add(deltaX * speed, deltaY * speed);
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
}
