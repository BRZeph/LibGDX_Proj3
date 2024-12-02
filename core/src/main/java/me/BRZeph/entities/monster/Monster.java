package me.BRZeph.entities.monster;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.core.Assets.AdvancedAssetsManager;
import me.BRZeph.utils.GlobalUtils;
import me.BRZeph.utils.pathFinding.Node;

import java.text.DecimalFormat;
import java.util.List;

import static me.BRZeph.utils.Constants.AssetsTiles.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.AssetsTiles.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Values.UIValues.HEALTH_BAR_HEIGHT;
import static me.BRZeph.utils.Constants.Values.UIValues.HEALTH_BAR_WIDTH;
import static me.BRZeph.utils.GlobalUtils.df;

public class Monster {
    private float x, y;
    private MonsterType type;
    private Texture monsterTexture;

    private List<Node> path; // Store the path
    private int currentNodeIndex; // Track the current node
    private boolean finishedPath;
    public float maxHealth;
    public float currentHealth;
    private float incomingDamage;
    private float distanceToEnd;
    private float animationTime = 0;
    private Animation<TextureRegion> walkAnimation;

    public Monster(List<Node> path, MonsterType type) {
        this.x = path.get(0).x * TILE_WIDTH;
        this.y = path.get(0).y * TILE_HEIGHT;
        this.type = type;
        finishedPath = false;
        maxHealth = type.getMaxHealth();
        currentHealth = maxHealth;
        incomingDamage = 0;
        distanceToEnd = path.size()*TILE_WIDTH;
        this.walkAnimation = AdvancedAssetsManager.getAnimation(type.getWalkAnimationName());
    }

    @Override
    public String toString() {
        return "Monster{" +
            "Position=(" + x + ", " + y + ")" +
            ", Type=" + type +
            ", MaxHealth=" + maxHealth +
            ", CurrentHealth=" + currentHealth +
            ", FinishedPath=" + finishedPath +
            ", CurrentNodeIndex=" + currentNodeIndex +
            '}';
    }

    public void update(float delta, List<Node> path) {
        this.path = path;
        if (path == null || path.isEmpty()) return;

        Node targetNode = path.get(currentNodeIndex);

        float targetX = (targetNode.x + 0.5f) * TILE_WIDTH - type.width/2;
        float targetY = (targetNode.y + 0.5f) * TILE_HEIGHT - type.height/2;

        float dx = targetX - x;
        float dy = targetY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 4) {
            float speed = type.getSpeed();
            x += (dx / distance) * speed * delta;
            y += (dy / distance) * speed * delta;
            distanceToEnd -= speed*delta;
        } else {
            currentNodeIndex++;
            if (currentNodeIndex >= path.size()) {
                finishedPath = true;
                currentNodeIndex = path.size() - 1;
            }
        }
    }

    public void render(SpriteBatch batch, BitmapFont font, ShapeRenderer shapeRenderer, float delta) {
        animationTime += delta;

        float barWidth = HEALTH_BAR_WIDTH;
        float barHeight = HEALTH_BAR_HEIGHT;
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

        // Health Bar.
        batch.begin();
        String healthText = df.format(getCurrentHealth()) + " / " + getMaxHealth();

        GlyphLayout layout = new GlyphLayout(font, healthText); // For centering text
        float textX = barX + (barWidth - layout.width) / 2;  // Centering text horizontally over the bar
        float textY = barY + barHeight + layout.height + 5;  // Slightly above the bar

        font.draw(batch, healthText, textX, textY);
        batch.end();

        TextureRegion currentFrame = walkAnimation.getKeyFrame(animationTime, true);

        batch.begin();
        batch.draw(
            currentFrame,
            x , y,
            type.getWidth(), type.getHeight()
        ); // Render using texture from enum
        batch.end();
    }

    public float getDistanceToPoint(int xPos, int yPos) {
        float dx = xPos - x;
        float dy = yPos - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public float getIncomingDamage() {
        return incomingDamage;
    }

    public void addIncomingDamage(float addIncomingDamage){
        this.incomingDamage += addIncomingDamage;
    }

    public void subIncomingDamage(float subtractIncomingDamage){
        this.incomingDamage -= subtractIncomingDamage;
    }

    public void setIncomingDamage(float incomingDamage) {
        this.incomingDamage = incomingDamage;
    }

    public int getNodesLeft() {
        return path.size() - currentNodeIndex; // Return the remaining nodes in the path
    }

    public float getX() {
        return x;
    }

    public List<Node> getPath() {
        return path;
    }

    public int getCurrentNodeIndex() {
        return currentNodeIndex;
    }

    public void setCurrentNodeIndex(int currentNodeIndex) {
        this.currentNodeIndex = currentNodeIndex;
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

    public void setCurrentHealth(float newCurrentHealth) {
        this.currentHealth = newCurrentHealth;
    }

    public void takeDamage(float dmg){
        this.currentHealth -= dmg;
    }

    public float getDistanceToEnd() {
        return distanceToEnd;
    }
}
