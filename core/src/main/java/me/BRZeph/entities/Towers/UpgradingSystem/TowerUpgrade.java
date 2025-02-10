package me.BRZeph.entities.Towers.UpgradingSystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.BRZeph.TowerDefenseGame;
import me.BRZeph.core.WaveSystem.WaveManager;
import me.BRZeph.entities.Towers.PlacedTower.Tower;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static me.BRZeph.core.Assets.AdvancedAssetsManager.getUITexture;

public class TowerUpgrade {
    private final String name;
    private final String description;
    private final String buyTexture;
    private int goldCost;
    private int essenceCost;
    private int momentumCost;
    private final int path;
    private final int tier;

    private final Consumer<WaveManager> attackModifier;
    private final Consumer<Tower> towerModifier;
    private final BiConsumer<Tower, WaveManager> projectileModifier;

    public TowerUpgrade(String name, String description,
                        int goldCost, int essenceCost, int momentumCost, int path, int tier,
                        String buyTexture,
                        Consumer<Tower> towerModifier, Consumer<WaveManager> attackModifier,
                        BiConsumer<Tower, WaveManager> projectileModifier){
        this.name = name;
        this.description = description;
        this.goldCost = goldCost;
        this.essenceCost = essenceCost;
        this.momentumCost = momentumCost;
        this.path = path;
        this.tier = tier;
        this.buyTexture = buyTexture;
        this.attackModifier = attackModifier;
        this.towerModifier = towerModifier;
        this.projectileModifier = projectileModifier;
    }

    @Override
    public String toString() {
        return "TowerUpgrade{" +
            "name='" + name + "\n" +
            ", description='" + description + "\n" +
            ", goldCost=" + goldCost + "\n" +
            ", essenceCost=" + essenceCost + "\n" +
            ", momentumCost=" + momentumCost + "\n" +
            '}';
    }

    public String getCost(){
        String val = "";
        if (goldCost != 0){
            val += goldCost + "G";
            if (essenceCost != 0 || momentumCost != 0){
                val += "   ";
            }
        }
        if (essenceCost != 0){
            val += essenceCost + "E";
            if (momentumCost != 0){
                val += "   ";
            }
        }
        if (momentumCost != 0){
            val += momentumCost + "M";
        }
        return val;
    }

    public void applyUpgrade(Tower tower){
        tower.registerTowerUpgrade(path, tier);
        if (towerModifier != null){ // Stats modifier such as range and damage.
            tower.setTowerModifier(towerModifier);
        }
        if (attackModifier != null) { // Behavior of attack modifier such as how many enemies get hit and once.
            tower.setAttackModifier(attackModifier);
        }
        if (projectileModifier != null){ // Behavior of projectile modifier such as lightning tower chain damage.
            tower.setProjectileModifier(projectileModifier);
        }
        // The acceptance of the consumer is inside the Tower.class.
        // For projectile and attack consumer, it's replaced once and accepted on every frame.
        // For tower modifier, it's replaced once, accepted once and then deleted to prevent repeating the same modifier.
    }

    public int getGoldCost() {
        return goldCost;
    }

    public int getEssenceCost() {
        return essenceCost;
    }

    public int getMomentumCost() {
        return momentumCost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPath() {
        return path;
    }

    public int getTier() {
        return tier;
    }

    public TextureRegion getBuyTexture() {
        return getUITexture(buyTexture);
    }

    public Consumer<WaveManager> getAttackModifier() {
        return attackModifier;
    }

    public Consumer<Tower> getTowerModifier() {
        return towerModifier;
    }

    public BiConsumer<Tower, WaveManager> getProjectileModifier() {
        return projectileModifier;
    }
}
