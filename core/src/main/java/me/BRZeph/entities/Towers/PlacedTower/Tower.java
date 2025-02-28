package me.BRZeph.entities.Towers.PlacedTower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.core.WaveSystem.WaveManager;
import me.BRZeph.entities.Projectile;
import me.BRZeph.entities.Towers.TargetingSystem.*;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.Towers.Visuals.TowerVisuals;
import me.BRZeph.entities.Towers.Visuals.TowerVisualsFactory;
import me.BRZeph.entities.monster.Monster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Constants.TowerUpgradeValues.SECONDARY_PATH_UPGRADE_LIMIT;
import static me.BRZeph.utils.GlobalUtils.drawTowerAttackCooldown;

public abstract class Tower {
    protected float attackRange;
    protected float damage;
    protected float attackCooldown;
    protected float cooldownClock;
    protected float projectileSpeed;
    protected int projectileWidth;
    protected int projectileHeight;
    protected int xPos;
    protected int yPos;
    protected int kills;
    protected int shotsFired;
    protected int wavePlaced;
    protected int path1Tier;
    protected int path2Tier;
    protected int path3Tier;
    protected int mainPath; // this points to the path, not the tier. To get the tier use getTier() and getMainPath();
    protected int secondPath; // mainPath or secondPath = -1 means that they haven't been chosen yet.
    protected int[] chosenPaths;
    protected TowerType type;
    protected Texture projectileTexture;
    protected TargetingStrategy targetingStrategy;
    protected List<Projectile> activeProjectiles;
    protected TowerVisuals towerVisuals;
    protected final List<TargetingStrategy> strategies;
    protected int currentStrategyIndex;
    protected float damageDealt;
    protected float criticalHitChance;
    protected float criticalHitDamage;

    protected Consumer<WaveManager> attackModifier;
    protected Consumer<Tower> towerModifier;
    protected BiConsumer<Tower, WaveManager> projectileModifier;

    public Tower(TowerType type, int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
        this.damageDealt = 0;
        this.kills = 0;
        this.shotsFired = 0;
        this.criticalHitChance = 0;
        this.criticalHitDamage = 0;

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
        this.wavePlaced = -1;
//        this.towerVisuals = TowerVisualsFactory.towerVisualsMap.get(type);
//        updateAnimations();
        initializeAttackModifier();
        initializeProjectileModifier();
        initializeProjectileData();
        recalculatePaths();
        path1Tier = 0;
        path2Tier = 0;
        path3Tier = 0;
        chosenPaths = new int[2];
        chosenPaths[0] = -1;
        chosenPaths[1] = -1;
    }

    public abstract void initializeProjectileData();

    public abstract String toString();

    public int getTier(int path){
        /*
        before using this, use recalculatePaths().
        to get main path's tier: use getMainPath() and check if it's -1, then use this method.
         */
        switch (path){
            case 1:
                return path1Tier;
            case 2:
                return path2Tier;
            case 3:
                return path3Tier;
            default:
                throw new IllegalArgumentException("Illegal path value: " + path);
        }
    }

    public void recalculatePaths(){
        int index = 0;
        if (path1Tier != 0){
            chosenPaths[index] = 1;
            index++;
        }
        if (path2Tier != 0){
            chosenPaths[index] = 2;
            index++;
        }
        if (path3Tier != 0){
            chosenPaths[index] = 3;
        }

        int highest = -1;
        int secondHighest;
        int path = -1;
        if (path1Tier > path2Tier && path1Tier > path3Tier){
            highest = path1Tier;
            path = 1;
        } else if (path2Tier > path3Tier){
            highest = path2Tier;
            path = 2;
        } else if (path3Tier != 0) {
            highest = path3Tier;
            path = 3;
        }

        if (highest > SECONDARY_PATH_UPGRADE_LIMIT){
            mainPath = path;

            /*
            calculate secondary path
             */
            if (mainPath == 1) {
                if (path2Tier >= path3Tier) {
                    secondHighest = path2Tier;
                    path = 2;
                } else {
                    secondHighest = path3Tier;
                    path = 3;
                }
            } else if (mainPath == 2) {
                if (path1Tier >= path3Tier) {
                    secondHighest = path1Tier;
                    path = 1;
                } else {
                    secondHighest = path3Tier;
                    path = 3;
                }
            } else {
                if (path1Tier >= path2Tier) {
                    secondHighest = path1Tier;
                    path = 1;
                } else {
                    secondHighest = path2Tier;
                    path = 2;
                }
            }
            if (secondHighest > 0){
                secondPath = path;
            } else {
                secondPath = -1; // Second path not chosen yet.
            }
        } else {
            mainPath = -1; // Main path not chosen yet.
            secondPath = -1; // Second path not chosen yet.
        }
    }

    public void registerTowerUpgrade(int path, int tier) {
        switch (path){
            case 1:
                this.path1Tier = tier;
                break;
            case 2:
                this.path2Tier = tier;
                break;
            case 3:
                this.path3Tier = tier;
                break;
            default:
                throw new IllegalArgumentException("Invalid path value: " + path);
        }
        recalculatePaths();
//        updateAnimations();
    }

    private void updateAnimations() {
        Animation<TextureRegion> idleAnimation = TowerVisualsFactory.getTowerVisuals(type).getAnimation(type, 1, path1Tier, "idle");
    }

    public void update(float delta, WaveManager waveManager){
        cooldownClock -= delta;

        if (towerModifier != null){ // Accept tower modifier once it's changed.
            towerModifier.accept(this);
            towerModifier = null;
        }

        if (cooldownClock <= 0) cooldownClock = 0;

        if (cooldownClock <= 0) {
            attackModifier.accept(waveManager);
        }

        updateProjectiles(waveManager);
    }

    public void updateProjectiles(WaveManager waveManager) {
        projectileModifier.accept(this, waveManager);
    }

    public void initializeProjectileModifier() {
        this.projectileModifier = (tower, waveManager) -> {
            float delta = Gdx.graphics.getDeltaTime();
            Iterator<Projectile> projectileIterator = activeProjectiles.iterator();
            while (projectileIterator.hasNext()) {
                Projectile projectile = projectileIterator.next();
                projectile.update(delta);
                if (projectile.hasReachedTarget()) {
                    projectile.getTarget().takeDamage(this, damage);
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

    public void initializeAttackModifier() {
        this.attackModifier = (waveManager) -> {
            List<Monster> monstersInRange = findMonstersInRange(waveManager.getCurrentWave().getMonsterList());
            Monster target = targetingStrategy.selectTarget(monstersInRange);
            if (target != null) {
                launchProjectile(target);
                target.addIncomingDamage(damage);
                cooldownClock = attackCooldown;
            }
        };
    }

    public void renderAttackCooldown(ShapeRenderer shapeRenderer) {
        drawTowerAttackCooldown(shapeRenderer, cooldownClock, attackCooldown, xPos, yPos);
    }

    public void launchProjectile(Monster target) {
        Projectile projectile = new Projectile(
            (xPos + 0.5f)*TILE_WIDTH,
            (yPos + 0.5f)*TILE_HEIGHT,
            projectileSpeed,
            damage, target, projectileTexture
        );
        activeProjectiles.add(projectile);
        shotsFired++;
    }

    public List<Monster> findMonstersInRange(List<Monster> allMonsters) {
        return allMonsters.stream().filter(this::isInRange).collect(Collectors.toList());
    }

    public boolean isInRange(Monster monster) {
        float dx = monster.getX() - xPos * 64;
        float dy = monster.getY() - yPos * 64;
        return Math.sqrt(dx * dx + dy * dy) <= attackRange;
    }

    public void renderProjectiles(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Projectile projectile : activeProjectiles) {
            spriteBatch.draw(
                projectile.getTexture(),
                projectile.getX(), projectile.getY(),
                projectileWidth, projectileHeight);
        }
        spriteBatch.end();
    }

    public void setNextTargetingStrategy() {
        currentStrategyIndex = (currentStrategyIndex + 1) % strategies.size();
        this.targetingStrategy = strategies.get(currentStrategyIndex);
    }

    public void setPreviousTargetingStrategy() {
        currentStrategyIndex = (currentStrategyIndex - 1 + strategies.size()) % strategies.size();
        this.targetingStrategy = strategies.get(currentStrategyIndex);
    }

    public void setProjectileSpeed(float projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public void setProjectileWidth(int projectileWidth) {
        this.projectileWidth = projectileWidth;
    }

    public void setProjectileHeight(int projectileHeight) {
        this.projectileHeight = projectileHeight;
    }

    public void addKills(){
        this.kills += 1;
    }

    public float getRange() {
        return attackRange;
    }

    public float getDamage() {
        return damage;
    }

    public float getAttackCooldown() {
        return attackCooldown;
    }

    public float getCooldownClock() {
        return cooldownClock;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getMainPath(){
        return mainPath;
    }

    public int getKills() {
        return kills;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public int getWavePlaced() {
        if (wavePlaced != -1) return wavePlaced;
        throw new IllegalArgumentException("ERROR: Unregistered parameter. After placing the tower, use .setWavePlaced()");
    }

    public int getPath1Tier() {
        return path1Tier;
    }

    public int getPath2Tier() {
        return path2Tier;
    }

    public int getPath3Tier() {
        return path3Tier;
    }

    public TowerType getType() {
        return type;
    }

    public Texture getProjectileTexture() {
        return projectileTexture;
    }

    public TargetingStrategy getTargetingStrategy() {
        return targetingStrategy;
    }

    public List<Projectile> getActiveProjectiles() {
        return activeProjectiles;
    }

    public List<TargetingStrategy> getStrategies() {
        return strategies;
    }

    public int getCurrentStrategyIndex() {
        return currentStrategyIndex;
    }

    public float getDamageDealt() {
        return damageDealt;
    }

    public int[] getChosenPaths() {
        return chosenPaths;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setAttackCooldown(float attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public void setCooldownClock(float cooldownClock) {
        this.cooldownClock = cooldownClock;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setShotsFired(int shotsFired) {
        this.shotsFired = shotsFired;
    }

    public void setWavePlaced(int wavePlaced) {
        this.wavePlaced = wavePlaced;
    }

    public void setPath1Tier(int path1Tier) {
        this.path1Tier = path1Tier;
    }

    public void setPath2Tier(int path2Tier) {
        this.path2Tier = path2Tier;
    }

    public void setPath3Tier(int path3Tier) {
        this.path3Tier = path3Tier;
    }

    public void setType(TowerType type) {
        this.type = type;
    }

    public void setProjectileTexture(Texture projectileTexture) {
        this.projectileTexture = projectileTexture;
    }

    public void setTargetingStrategy(TargetingStrategy targetingStrategy) {
        this.targetingStrategy = targetingStrategy;
    }

    public void setActiveProjectiles(List<Projectile> activeProjectiles) {
        this.activeProjectiles = activeProjectiles;
    }

    public void setCurrentStrategyIndex(int currentStrategyIndex) {
        this.currentStrategyIndex = currentStrategyIndex;
    }

    public void setDamageDealt(float damageDealt) {
        this.damageDealt = damageDealt;
    }

    public void setAttackModifier(Consumer<WaveManager> attackModifier) {
        this.attackModifier = attackModifier;
    }

    public void setProjectileModifier(BiConsumer<Tower, WaveManager> projectileModifier) {
        this.projectileModifier = projectileModifier;
    }

    public void setTowerModifier(Consumer<Tower> towerModifier) {
        this.towerModifier = towerModifier;
    }

    public float getCriticalHitDamage() {
        return criticalHitDamage;
    }

    public void setCriticalHitDamage(float criticalHitDamage) {
        this.criticalHitDamage = criticalHitDamage;
    }

    public float getCriticalHitChance() {
        return criticalHitChance;
    }

    public void setCriticalHitChance(float criticalHitChance) {
        this.criticalHitChance = criticalHitChance;
    }

    public int getSecondPath() {
        return secondPath;
    }
}
