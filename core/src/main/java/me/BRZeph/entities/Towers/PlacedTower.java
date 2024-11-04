package me.BRZeph.entities.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.BRZeph.core.Projectile;
import me.BRZeph.core.WaveManager;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Towers.TargetingSystem.FirstMonsterStrategy;
import me.BRZeph.entities.Towers.TargetingSystem.TargetingStrategy;
import me.BRZeph.entities.monster.Monster;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static me.BRZeph.utils.Constants.Values.TowerValues.PROJECTILE_HEIGHT;
import static me.BRZeph.utils.Constants.Values.TowerValues.PROJECTILE_WIDTH;

public class PlacedTower {
    private float attackRange;
    private float damage;
    private float attackCooldown;
    private float cooldownClock;
    private int xPos;
    private int yPos;
    private TowerType type;
    private TargetingStrategy targetingStrategy;
    private List<Projectile> activeProjectiles;
    private Texture projectileTexture;

    public PlacedTower(TowerType type, int xPos, int yPos) { //towers are rendered in the tiles.
        this.xPos = xPos; // Tile position.
        this.yPos = yPos; // Tile position.
        this.type = type;

        this.attackRange = getTowerRange(type);
        this.damage = getTowerDamage(type);
        this.attackCooldown = getTowerCooldown(type);
        this.cooldownClock = 0;
        this.targetingStrategy = new FirstMonsterStrategy();
        this.activeProjectiles = new ArrayList<>();
        projectileTexture = type.getProjectileTexture();
    }

    private boolean isInRange(Monster monster) {
        float dx = monster.getX() - xPos*64;
        float dy = monster.getY() - yPos*64;
        float distance = (float) Math.sqrt(Math.abs(dx * dx + dy * dy));
        return distance <= attackRange;
    }


    public void update(float delta, WaveManager waveManager) {
        cooldownClock -= delta;
        if (cooldownClock <= 0) {
            List<Monster> monstersInRange = findMonstersInRange(waveManager.getCurrentWave().getMonsterList());
            Monster target = targetingStrategy.selectTarget(monstersInRange);
            if (target != null) {
                launchProjectile(target);
                cooldownClock = attackCooldown;
            }
        }

        // Update projectiles
        Iterator<Projectile> projectileIterator = activeProjectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            projectile.update(delta);

            if (projectile.hasReachedTarget()) {
                projectile.getTarget().takeDamage(projectile.getDamage());

                if (projectile.getTarget().getCurrentHealth() <= 0) {
                    waveManager.getCurrentWave().getMonsterDied().add(projectile.getTarget());
                    waveManager.getCurrentWave().getMonsterList().remove(projectile.getTarget());
                }

                projectileIterator.remove();
            }
        }
    }


    private List<Monster> findMonstersInRange(List<Monster> allMonsters) {
        return allMonsters.stream()
            .filter(this::isInRange)
            .collect(Collectors.toList());
    }

    private void launchProjectile(Monster target) {
        Projectile projectile = new Projectile(
            yPos * 64 + 32,
            xPos * 64 + 32,
            Constants.Values.TowerValues.ARCHER_TOWER_PROJECTILE_SPEED,
            damage,
            target,
            projectileTexture
        );
        activeProjectiles.add(projectile);
    }

    public void renderProjectiles(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Projectile projectile : activeProjectiles) {
            spriteBatch.draw(projectile.getTexture(), projectile.getX(), projectile.getY(), PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        }
        spriteBatch.end();
    }

    public List<Projectile> getActiveProjectiles() {
        return activeProjectiles;
    }

    public static float getTowerDamage(TowerType type) {
        return type.getDamage();
    }

    public static float getTowerRange(TowerType type) {
        return type.getRange();
    }

    public static float getTowerCooldown(TowerType type) {
        return type.getAttackCooldown();
    }

    public TowerType getType() {
        return type;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public TargetingStrategy getTargetingStrategy() {
        return targetingStrategy;
    }

    public void setTargetingStrategy(TargetingStrategy targetingStrategy) {
        this.targetingStrategy = targetingStrategy;
    }
}
