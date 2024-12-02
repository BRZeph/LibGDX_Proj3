package me.BRZeph.entities.Towers.PlacedTower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.core.Projectile;
import me.BRZeph.core.Managers.WaveManager;
import me.BRZeph.entities.Towers.TargetingSystem.TargetingStrategy;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.monster.Monster;

import java.util.List;

import static me.BRZeph.utils.Constants.AssetsTiles.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.AssetsTiles.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Values.TowerValues.ArcherTowerValues.ARCHER_TOWER_ATTACK_BAR_WIDTH;

public interface Tower {
    void update(float delta, WaveManager waveManager);
    void renderAttackCooldown(ShapeRenderer shapeRenderer);
    List<Monster> findMonstersInRange(List<Monster> allMonsters);
    void launchProjectile(Monster target);
    void renderProjectiles(SpriteBatch spriteBatch);
    boolean isInRange(Monster monster);
    void setNextTargetingStrategy();
    void setPreviousTargetingStrategy();
    int getxPos();
    int getyPos();
    TowerType getType();
    TargetingStrategy getTargetingStrategy();
    float getAttackRange();
    float getDamage();
    float getAttackCooldown();
    float getCooldownClock();
    Texture getProjectileTexture();
    List<Projectile> getActiveProjectiles();
    List<TargetingStrategy> getStrategies();
    int getCurrentStrategyIndex();
    void updateProjectiles(float delta, WaveManager waveManager);
    String toString();
}
