package me.BRZeph.entities.Towers.PlacedTower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.core.Managers.WaveManager;
import me.BRZeph.entities.Projectile;
import me.BRZeph.entities.Towers.TargetingSystem.*;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.monster.Monster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static me.BRZeph.utils.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Paths.TileValues.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Paths.Values.TowerValues.CannonTowerValues.*;
import static me.BRZeph.utils.GlobalUtils.drawTowerAttackCooldown;

public class CannonTower implements Tower{
    protected float attackRange;
    protected float damage;
    protected float attackCooldown;
    protected float cooldownClock;
    protected int xPos;
    protected int yPos;
    protected int kills;
    protected int shotsFired;
    protected int wavePlaced;
    protected TowerType type;
    protected Texture projectileTexture;
    protected TargetingStrategy targetingStrategy;
    protected List<Projectile> activeProjectiles;
    protected int currentStrategyIndex;
    protected final List<TargetingStrategy> strategies;
    protected float damageDealt;

    public CannonTower(TowerType type, int xPos, int yPos) {
        this.xPos = xPos; // Tile position.
        this.yPos = yPos; // Tile position.
        this.type = type;
        this.damageDealt = 0;
        this.kills = 0;
        this.shotsFired = 0;

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
        return "CannonTower{" +
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
                List<Monster> aoeAffectedMonsters = waveManager.getCurrentWave().getMonsterList().stream()
                    .filter(monster -> {
                        float targetX = projectile.getTarget().getX();
                        float targetY = projectile.getTarget().getY();
                        return monster.getDistanceToPoint((int)targetX, (int)targetY) <= CANNON_TOWER_AOE_RANGE;
                    })
                    .collect(Collectors.toList());
                for (Monster monster : aoeAffectedMonsters){
                    monster.takeDamage(CANNON_TOWER_AOE_DAMAGE);
                    damageDealt += CANNON_TOWER_AOE_DAMAGE;
                }
                projectile.getTarget().takeDamage(damage);
                projectile.getTarget().subIncomingDamage(damage);
                projectileIterator.remove();
                damageDealt += damage;
                if (projectile.getTarget().getCurrentHealth() <= 0){
                    addKills();
                }
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
            CANNON_TOWER_PROJECTILE_SPEED,
            damage, target, projectileTexture
        );
        activeProjectiles.add(projectile);
        shotsFired++;
    }

    @Override
    public void renderProjectiles(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Projectile projectile : activeProjectiles) {
            spriteBatch.draw(
                projectile.getTexture(),
                projectile.getX(), projectile.getY(),
                CANNON_TOWER_PROJECTILE_WIDTH, CANNON_TOWER_PROJECTILE_HEIGHT);
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
    public int getShotsFired() {
        return shotsFired;
    }

    @Override
    public int getWavePlaced() {
        if (wavePlaced != -1) return wavePlaced;
        throw new IllegalArgumentException("ERROR: Unregistered parameter. After placing the tower, use .setWavePlaced()");
    }

    @Override
    public void setWavePlaced(int wave) {
        this.wavePlaced = wave;
    }
    @Override
    public void addKills() {
        this.kills += 1;
    }

    @Override
    public void addDamageDealt(float damage) {
        this.damageDealt += damage;
    }

    @Override
    public float getDamageDealt() {
        return damageDealt;
    }

    @Override
    public int getKills() {
        return kills;
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
