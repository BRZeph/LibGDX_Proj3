package me.BRZeph.entities.Towers.PlacedTower;

import com.badlogic.gdx.Gdx;
import me.BRZeph.entities.Projectile;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.monster.Monster;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.CannonTowerValues.*;

public class CannonTower extends Tower {

    public CannonTower(TowerType type, int xPos, int yPos) {
        super(type, xPos, yPos);

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
    public void initializeProjectileData() {
        this.projectileSpeed  = CANNON_TOWER_PROJECTILE_SPEED;
        this.projectileWidth  = CANNON_TOWER_PROJECTILE_WIDTH;
        this.projectileHeight = CANNON_TOWER_PROJECTILE_HEIGHT;
    }

    @Override
    public void initializeProjectileModifier(){
        this.projectileModifier = (tower, waveManager) -> {
            float delta = Gdx.graphics.getDeltaTime();
            Iterator<Projectile> projectileIterator = activeProjectiles.iterator();
            while (projectileIterator.hasNext()) {
                Projectile projectile = projectileIterator.next();
                projectile.update(delta);
                if (projectile.hasReachedTarget()) {
                    List<Monster> aoeAffectedMonsters = waveManager.getCurrentWave().getMonsterList().stream()
                        .filter(monster -> {
                            float targetX = projectile.getTarget().getX();
                            float targetY = projectile.getTarget().getY();
                            return monster.getDistanceToPoint((int) targetX, (int) targetY) <= CANNON_TOWER_AOE_RANGE;
                        })
                        .collect(Collectors.toList());
                    for (Monster monster : aoeAffectedMonsters) {
                        monster.takeDamage(this, CANNON_TOWER_AOE_DAMAGE);
                        damageDealt += CANNON_TOWER_AOE_DAMAGE;
                    }
                    projectile.getTarget().takeDamage(this, damage);
                    projectile.getTarget().subIncomingDamage(damage);
                    projectileIterator.remove();
                    damageDealt += damage;
                    if (projectile.getTarget().getCurrentHealth() <= 0) {
                        addKills();
                    }
                }
            }
        };
    }
}
