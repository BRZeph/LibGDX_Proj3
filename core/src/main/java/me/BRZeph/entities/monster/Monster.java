package me.BRZeph.entities.monster;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.core.Assets.AdvancedAssetsManager;
import me.BRZeph.core.CurrencyManager;
import me.BRZeph.core.WaveSystem.Wave;
import me.BRZeph.core.Map.Node;

import java.util.List;

import static me.BRZeph.entities.monster.MonsterType.LESSER_ABYSSAL_MATRON;
import static me.BRZeph.entities.monster.Variant.applyVariant;
import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.AbyssalMatronValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.UIValues.HEALTH_BAR_HEIGHT;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.UIValues.HEALTH_BAR_WIDTH;
import static me.BRZeph.utils.GlobalUtils.*;

public class Monster {
    private float x, y;
    private MonsterType type;
    private Variant variant;

    private List<Node> path; // Store the path
    private int currentNodeIndex; // Track the current node
    private int waveSpawnIndex;
    private boolean finishedPath;
    private float incomingDamage;
    private float distanceToEnd;
    private float animationTime = 0;
    private Animation<TextureRegion> walkAnimation;

    private float maxHealth;
    private float currentHealth;
    private float width;
    private float height;
    private float speed;
    private float nexusDmg;
    private float goldLoot;
    private float essenceLoot;
    private float momentumLoot;

    public Monster(List<Node> path, MonsterType type, Variant variant, int wave) {
        this.x = (path.get(0).x + 0.5f) * TILE_WIDTH - type.getWidth()/2;
        this.y = (path.get(0).y + 0.5f) * TILE_HEIGHT - type.getHeight()/2;
        this.type = type;
        this.variant = variant;
        this.speed = type.getSpeed();
        this.maxHealth = type.getMaxHealth();
        this.width = type.getWidth();
        this.height = type.getHeight();
        this.nexusDmg = type.getNexusDmg();
        this.goldLoot = type.getGoldLoot();
        this.essenceLoot = type.getEssenceLoot();
        this.momentumLoot = type.getMomentumLoot();
        this.walkAnimation = AdvancedAssetsManager.getAnimation(type.getWalkAnimationName());
        applyVariant(this, variant, wave);
        this.currentHealth = maxHealth;
        this.waveSpawnIndex = wave;

        finishedPath = false;
        incomingDamage = 0;
        distanceToEnd = path.size()*TILE_WIDTH;
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
        applyWalkEffect();
        this.path = path;
        if (path == null || path.isEmpty()) return;

        Node targetNode = path.get(currentNodeIndex);

        float targetX = (targetNode.x + 0.5f) * TILE_WIDTH - width / 2;
        float targetY = (targetNode.y + 0.5f) * TILE_HEIGHT - height / 2;

        float dx = targetX - x;
        float dy = targetY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 4) {
            float speed = getSpeed();
            float moveDistance = speed * delta;

            if (moveDistance >= distance) {
                x = targetX;
                y = targetY;
                distanceToEnd -= distance;
                currentNodeIndex++;
                if (currentNodeIndex >= path.size()) {
                    finishedPath = true;
                    currentNodeIndex = path.size() - 1;
                }
            } else {
                x += (dx / distance) * moveDistance;
                y += (dy / distance) * moveDistance;
                distanceToEnd -= moveDistance;
            }
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
        float barX = getX() + width/2 - barWidth/2;
        float barY = getY() + height + 10;

        float healthPercentage = getCurrentHealth() / getMaxHealth();

        float currentHealthWidth = barWidth * healthPercentage;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, currentHealthWidth, barHeight);
        shapeRenderer.end();

        batch.begin();
        String healthText = df.format(getCurrentHealth()) + " / " + df.format(getMaxHealth());

        GlyphLayout layout = new GlyphLayout(font, healthText);
        float textX = barX + (barWidth - layout.width) / 2;
        float textY = barY + barHeight + layout.height + 5;

        font.draw(batch, healthText, textX, textY);
        batch.end();

        TextureRegion currentFrame = walkAnimation.getKeyFrame(animationTime, true);

        batch.begin();
        batch.draw(
            currentFrame,
            x , y,
            width, height
        );
        batch.end();
    }

    private void applyWalkEffect() { // Use the same logic as die() here.
        // Check only ever 0.1s and apply effect during 0.1s, that way it does not cause too much lag.
        // Consider moving this and die() to Variant.class.
        // Example:
        switch (type){
            case ZOMBIE:
                switch (variant){
                    case NORMAL:
                        break;
                    case ELITE:
                        break;
                }
            case SKELETON:
                switch (variant){
                    case NORMAL:
                        break;
                    case ELITE:
                        break;
                }
        }
    }

    public void die(CurrencyManager currencyManager, Wave wave) {
        currencyManager.addMonsterLoot(getType());
        switch (type){
            case ZOMBIE:
                zombieDeath();                        //implemented
                break;
            case SKELETON:
                skeletonDeath();                      //implemented
                break;
            case DEMONIC_IMP:
                impDeath();
                break;
            case DOOM_HERALD:
                doomHeraldDeath();
                break;
            case SOUL_REAPER:
                soulReaperDeath();
                break;
            case VOID_WRAITH:
                voidWraithDeath();
                break;
            case ABYSSAL_MATRON:
                abyssalMatronDeath(wave);            //implemented
                break;
            case LESSER_ABYSSAL_MATRON:
                lesserAbyssalMatronDeath();
                break;
            case CHRONO_STALKER:
                chronoStalkerDeath();
                break;
            case HELLFIRE_BRUTE:
                hellfireBruteDeath();
                break;
            case TEMPORAL_SHADE:
                temporalShadeDeath();
                break;
            case INFERNAL_JUGGERNAUT:
                infernalJuggernautDeath();
                break;
        }
    }

    private void infernalJuggernautDeath() {
        // Start death animation here??

    }

    private void temporalShadeDeath() {
        // Start death animation here??

    }

    private void hellfireBruteDeath() {
        // Start death animation here??

    }

    private void chronoStalkerDeath() {
        // Start death animation here??

    }

    private void lesserAbyssalMatronDeath() {
        // Start death animation here??

    }

    private void abyssalMatronDeath(Wave wave) {
        // Start death animation here??
        switch (variant){
            case NORMAL:
                for (int i = 0; i < ABYSSAL_MATRON_DEATH_SPAWNS; i++) {
                    Monster monster = new Monster(this.getPath(), LESSER_ABYSSAL_MATRON, variant, getWaveSpawnIndex());
                    monster.setX(this.getX() + getRandom(-ABYSSAL_MATRON_SPAWNS_XOFFSET,ABYSSAL_MATRON_SPAWNS_XOFFSET));
                    monster.setY(this.getY() + getRandom(-ABYSSAL_MATRON_SPAWNS_YOFFSET,ABYSSAL_MATRON_SPAWNS_YOFFSET));
                    monster.setCurrentNodeIndex(getClosestNodeIndex(monster));
                    monster.setDistanceToEnd(updateDistanceToEnd(monster));
                    wave.spawnMonster(monster);
                }
                break;
        }
    }

    private float updateDistanceToEnd(Monster monster) {
        return (float) ((path.size() - monster.getCurrentNodeIndex()) * TILE_WIDTH);
    }

    private int getClosestNodeIndex(Monster monster) {
        int nodeIndex = -1;
        float smallestDistance = 50000;
        float dst;
        for (int i = 0; i < path.size(); i++){
            Node node = path.get(i);
            float targetX = (node.x + 0.5f) * TILE_WIDTH - width / 2;
            float targetY = (node.y + 0.5f) * TILE_HEIGHT - height / 2;
            float dx = targetX - monster.getX();
            float dy = targetY - monster.getY();
            dst = (float) Math.sqrt(dx * dx + dy * dy);
            if (dst <= smallestDistance){
                smallestDistance = dst;
                nodeIndex = i;
            }
        }
        if (nodeIndex == -1) throw new RuntimeException("Illegal value for nodeIndex");
        return nodeIndex;
    }

    private void voidWraithDeath() {
        // Start death animation here??

    }

    private void soulReaperDeath() {
        // Start death animation here??

    }

    private void doomHeraldDeath() {
        // Start death animation here??

    }

    private void impDeath() {
        // Start death animation here??

    }

    private void skeletonDeath() {
        // Start death animation here??

    }

    private void zombieDeath() {
        // Start death animation here??
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

    public void setDistanceToEnd(float distanceToEnd) {
        this.distanceToEnd = distanceToEnd;
    }

    public int getNodesLeft() {
        return path.size() - currentNodeIndex;
    }

    public float getX() {
        return x;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public void setWalkAnimation(Animation<TextureRegion> walkAnimation) {
        this.walkAnimation = walkAnimation;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
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

    public int getWaveSpawnIndex() {
        return waveSpawnIndex;
    }

    public float getNexusDmg() {
        return nexusDmg;
    }

    public float getGoldLoot() {
        return goldLoot;
    }

    public float getEssenceLoot() {
        return essenceLoot;
    }

    public float getMomentumLoot() {
        return momentumLoot;
    }

    public void setNexusDmg(float nexusDmg) {
        this.nexusDmg = nexusDmg;
    }

    public void setGoldLoot(float goldLoot) {
        this.goldLoot = goldLoot;
    }

    public void setEssenceLoot(float essenceLoot) {
        this.essenceLoot = essenceLoot;
    }

    public void setMomentumLoot(float momentumLoot) {
        this.momentumLoot = momentumLoot;
    }
}
