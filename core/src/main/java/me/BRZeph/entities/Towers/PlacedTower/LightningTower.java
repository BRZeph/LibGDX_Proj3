package me.BRZeph.entities.Towers.PlacedTower;

import com.badlogic.gdx.Gdx;
import me.BRZeph.entities.Projectile;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.monster.Monster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.LightningTowerValues.*;

public class LightningTower extends Tower {

    protected int chainHitAmount;
    protected float chainHitMaxDistance;
    protected float chainHitDamage;

    public LightningTower(TowerType type, int xPos, int yPos) {
        super(type, xPos, yPos);
        this.chainHitAmount = LIGHTNING_TOWER_CHAIN_HIT_TARGET_AMOUNT;
        this.chainHitMaxDistance = LIGHTNING_TOWER_CHAIN_HIT_MAX_DISTANCE;
        this.chainHitDamage = LIGHTNING_TOWER_CHAIN_HIT_DAMAGE;
    }

    @Override
    public String toString() {
        return "LightningTower{" +
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
    public void initializeProjectileModifier() {
        this.projectileModifier = (tower, waveManager) -> {
            float delta = Gdx.graphics.getDeltaTime();
            Iterator<Projectile> projectileIterator = activeProjectiles.iterator();
            while (projectileIterator.hasNext()) {
                Projectile projectile = projectileIterator.next();
                projectile.update(delta);
                if (projectile.hasReachedTarget()) {
                    List<Monster> chainAffectedMonster = new ArrayList<>();
                    Monster currentMonster = projectile.getTarget();
                    Monster nextMonster;

                    for (int i = 0; i < chainHitAmount; i++){
                        final Monster finalCurrentMonster = currentMonster;
                        nextMonster = waveManager.getCurrentWave().getMonsterList().stream()
                            .filter(monster -> !chainAffectedMonster.contains(monster))
                            .filter(monster -> {
                                int targetX = (int) finalCurrentMonster.getX();
                                int targetY = (int) finalCurrentMonster.getY();
                                return monster != null &&
                                    monster.getDistanceToPoint(targetX, targetY) <= chainHitMaxDistance;
                            })
                            .min((monster1, monster2) -> {
                                float distance1 = finalCurrentMonster.getDistanceToPoint((int) monster1.getX(), (int) monster1.getY());
                                float distance2 = finalCurrentMonster.getDistanceToPoint((int) monster2.getX(), (int) monster2.getY());
                                return Float.compare(distance1, distance2);
                            })
                            .orElse(null);

                        if (nextMonster != null) {
                            chainAffectedMonster.add(nextMonster);
                            currentMonster = nextMonster;
                        } else {
                            break;
                        }
                    }

                    for (Monster monster : chainAffectedMonster){
                        if (monster != null) {
                            monster.takeDamage(chainHitDamage);
                            damageDealt += chainHitDamage;
                        }
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
        };
    }

    @Override
    public void initializeProjectileData() {
        this.projectileSpeed  = LIGHTNING_TOWER_PROJECTILE_SPEED;
        this.projectileWidth  = LIGHTNING_TOWER_PROJECTILE_WIDTH;
        this.projectileHeight = LIGHTNING_TOWER_PROJECTILE_HEIGHT;
    }
}
