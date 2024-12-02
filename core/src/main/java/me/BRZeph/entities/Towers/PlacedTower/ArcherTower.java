package me.BRZeph.entities.Towers.PlacedTower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.core.Projectile;
import me.BRZeph.core.Managers.WaveManager;
import me.BRZeph.entities.Towers.TargetingSystem.*;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.monster.Monster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static me.BRZeph.utils.Constants.AssetsTiles.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.AssetsTiles.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Values.TowerValues.ArcherTowerValues.*;
import static me.BRZeph.utils.GlobalUtils.drawTowerAttackCooldown;

public class ArcherTower implements Tower{
    protected float attackRange;
    protected float damage;
    protected float attackCooldown;
    protected float cooldownClock;
    protected int xPos;
    protected int yPos;
    protected TowerType type;
    protected Texture projectileTexture;
    protected TargetingStrategy targetingStrategy;
    protected List<Projectile> activeProjectiles;
    protected final List<TargetingStrategy> strategies;
    protected int currentStrategyIndex;

    public ArcherTower(TowerType type, int xPos, int yPos) {
        this.xPos = xPos; // Tile position.
        this.yPos = yPos; // Tile position.
        this.type = type;

        this.attackRange = type.getRange();
        this.damage = type.getDamage();
        this.attackCooldown = type.getAttackCooldown();
        this.cooldownClock = 0;
        this.activeProjectiles = new ArrayList<>();
        this.projectileTexture = type.getProjectileTexture();
        this.strategies = List.of(
            new FirstMonsterStrategy(),
            new LastMonsterStrategy(),
            ClosestMonsterStrategy.instantiateClosestMonsterStrategy(this),
            new TankMonsterStrategy(),
            new SquishMonsterStrategy()
        );
        this.currentStrategyIndex = 0;
        this.targetingStrategy = strategies.get(currentStrategyIndex);
    }

    @Override
    public String toString() {
        return "ArcherTower{" +
            "attackRange=" + attackRange +
            ", damage=" + damage +
            ", attackCooldown=" + attackCooldown +
            ", cooldownClock=" + cooldownClock +
            ", xPos=" + xPos +
            ", yPos=" + yPos +
            ", type=" + type +
            ", targetingStrategy=" + targetingStrategy +
            ", currentStrategyIndex=" + currentStrategyIndex +
            ", activeProjectiles=" + activeProjectiles.size() +
            '}';
    }


    @Override
    public void update(float delta, WaveManager waveManager) {
        cooldownClock -= delta;
        if (cooldownClock <= 0) cooldownClock = 0;

        if (cooldownClock <= 0) {
            List<Monster> monstersInRange = findMonstersInRange(waveManager.getCurrentWave().getMonsterList());
            Monster target = targetingStrategy.selectTarget(monstersInRange);
            if (target != null) {
                launchProjectile(target);
                target.addIncomingDamage(damage);
                cooldownClock = attackCooldown;
            }
        }
        updateProjectiles(delta, waveManager);
    }

    @Override
    public void updateProjectiles(float delta, WaveManager waveManager) {
        Iterator<Projectile> projectileIterator = activeProjectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            projectile.update(delta);
            if (projectile.hasReachedTarget()) {
                projectile.getTarget().takeDamage(damage);
                projectile.getTarget().subIncomingDamage(damage);
                projectileIterator.remove();
            }
        }
    }

    @Override
    public void renderAttackCooldown(ShapeRenderer shapeRenderer) {
        drawTowerAttackCooldown(shapeRenderer, cooldownClock, attackCooldown, xPos, yPos);
    }

    @Override
    public List<Monster> findMonstersInRange(List<Monster> allMonsters) {
        return allMonsters.stream().filter(this::isInRange).collect(Collectors.toList());
    }

    @Override
    public void launchProjectile(Monster target) {
        Projectile projectile = new Projectile(
            (xPos + 0.5f)*TILE_WIDTH,
            (yPos + 0.5f)*TILE_HEIGHT,
            ARCHER_TOWER_PROJECTILE_SPEED,
            damage, target, projectileTexture
        );
        activeProjectiles.add(projectile);
    }

    @Override
    public void renderProjectiles(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Projectile projectile : activeProjectiles) {
            spriteBatch.draw(
                projectile.getTexture(),
                projectile.getX(), projectile.getY(),
                ARCHER_TOWER_PROJECTILE_WIDTH, ARCHER_TOWER_PROJECTILE_HEIGHT);
        }
        spriteBatch.end();
    }

    @Override
    public boolean isInRange(Monster monster) {
        float dx = monster.getX() - xPos * 64;
        float dy = monster.getY() - yPos * 64;
        return Math.sqrt(dx * dx + dy * dy) <= attackRange;
    }

    @Override
    public void setNextTargetingStrategy() {
        currentStrategyIndex = (currentStrategyIndex + 1) % strategies.size();
        this.targetingStrategy = strategies.get(currentStrategyIndex);
    }

    @Override
    public void setPreviousTargetingStrategy() {
        currentStrategyIndex = (currentStrategyIndex - 1 + strategies.size()) % strategies.size();
        this.targetingStrategy = strategies.get(currentStrategyIndex);
    }

    @Override
    public float getAttackRange() {
        return attackRange;
    }

    @Override
    public float getDamage() {
        return damage;
    }

    @Override
    public float getAttackCooldown() {
        return attackCooldown;
    }

    @Override
    public float getCooldownClock() {
        return cooldownClock;
    }

    @Override
    public int getxPos() {
        return xPos;
    }

    @Override
    public int getyPos() {
        return yPos;
    }

    @Override
    public Texture getProjectileTexture() {
        return projectileTexture;
    }

    @Override
    public TargetingStrategy getTargetingStrategy() {
        return targetingStrategy;
    }

    @Override
    public List<Projectile> getActiveProjectiles() {
        return activeProjectiles;
    }

    @Override
    public List<TargetingStrategy> getStrategies() {
        return strategies;
    }

    @Override
    public int getCurrentStrategyIndex() {
        return currentStrategyIndex;
    }

    @Override
    public TowerType getType() {
        return type;
    }
}
