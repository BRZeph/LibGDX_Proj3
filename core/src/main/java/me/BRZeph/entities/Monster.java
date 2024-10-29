package me.BRZeph.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.Main;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;
import me.BRZeph.utils.enums.MonsterType;
import me.BRZeph.utils.pathFinding.Node;

import java.util.List;

public class Monster {
    private float x, y;
    private MonsterType type;
    private Texture monsterTexture;

    private List<Node> path; // Store the path
    private int currentNodeIndex; // Track the current node
    private boolean finishedPath;
    public float maxHealth;
    public float currentHealth;

    public Monster(float x, float y, MonsterType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        finishedPath = false;
        maxHealth = type.getMaxHealth();
        currentHealth = maxHealth;
    }

    public void update(float delta, List<Node> path) {
        this.path = path; // Update the path
        if (path == null || path.isEmpty()) return; // Check if path is valid

        // Get the current target node
        Node targetNode = path.get(currentNodeIndex);

        // Calculate the target position
        float targetX = (targetNode.x + 0.5f) * Constants.AssetsTiles.TILE_WIDTH - type.width/2;
        float targetY = (targetNode.y + 0.5f) * Constants.AssetsTiles.TILE_HEIGHT - type.height/2;

        // Calculate the distance to the target node
        float dx = targetX - x;
        float dy = targetY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Move towards the target node
        if (distance > 4) { // Move if not close enough
            float speed = type.getSpeed(); // Speed of the monster
            x += (dx / distance) * speed * delta; // Move x
            y += (dy / distance) * speed * delta; // Move y
        } else {
            // Reached the target node
            currentNodeIndex++;
            if (currentNodeIndex >= path.size()) {
                GlobalUtils.consoleLog("Monster reached the end of the path.");
                finishedPath = true;
                currentNodeIndex = path.size() - 1; // Keep it at the last node
            }
        }
    }

    public void render(SpriteBatch batch, BitmapFont font, ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        float barWidth = Constants.Values.UIValues.HEALTH_BAR_WIDTH;
        float barHeight = Constants.Values.UIValues.HEALTH_BAR_HEIGHT;
        float barX = getX() + type.getWidth()/2 - barWidth/2;
        float barY = getY() + type.getHeight() + 10;

        float healthPercentage = getCurrentHealth() / getMaxHealth();

        // Calculate the width of the green bar based on health percentage
        float currentHealthWidth = barWidth * healthPercentage;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        // Draw the green (current health) part
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, currentHealthWidth, barHeight);
        shapeRenderer.end();

        batch.begin();
        String healthText = getCurrentHealth() + " / " + getMaxHealth();

        GlyphLayout layout = new GlyphLayout(font, healthText); // For centering text
        float textX = barX + (barWidth - layout.width) / 2;  // Centering text horizontally over the bar
        float textY = barY + barHeight + layout.height + 5;  // Slightly above the bar

        font.draw(batch, healthText, textX, textY);
        batch.end();

        batch.begin();
        batch.draw(type.getTexture(),x , y, type.getWidth(), type.getHeight()); // Render using texture from enum
        batch.end();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isFinishedPath() {
        return finishedPath;
    }

    public void setFinishedPath(boolean finishedPath) {
        this.finishedPath = finishedPath;
    }

    public MonsterType getType() {
        return type;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }
}