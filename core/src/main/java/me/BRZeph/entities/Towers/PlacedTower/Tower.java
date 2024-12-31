package me.BRZeph.entities.Towers.PlacedTower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.core.Managers.WaveManager;
import me.BRZeph.entities.Projectile;
import me.BRZeph.entities.Towers.TargetingSystem.TargetingStrategy;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.monster.Monster;

import java.util.List;

public interface Tower {
    void update(float delta, WaveManager waveManager);
    void renderAttackCooldown(ShapeRenderer shapeRenderer);
    void updateProjectiles(float delta, WaveManager waveManager);
    void launchProjectile(Monster target);
    void renderProjectiles(SpriteBatch spriteBatch);
    void setNextTargetingStrategy();
    void setPreviousTargetingStrategy();
    void addKills();
    void addDamageDealt(float damage);
    void setWavePlaced(int wave);
    int getKills();
    int getxPos();
    int getyPos();
    int getCurrentStrategyIndex();
    int getShotsFired();
    int getWavePlaced();
    boolean isInRange(Monster monster);
    float getDamageDealt();
    float getAttackRange();
    float getDamage();
    float getAttackCooldown();
    float getCooldownClock();
    List<Monster> findMonstersInRange(List<Monster> allMonsters);
    List<Projectile> getActiveProjectiles();
    List<TargetingStrategy> getStrategies();
    TowerType getType();
    TargetingStrategy getTargetingStrategy();
    Texture getProjectileTexture();
    String toString();
}
