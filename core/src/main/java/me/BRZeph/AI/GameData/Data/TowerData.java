package me.BRZeph.AI.GameData.Data;

import me.BRZeph.entities.Towers.TowerType;

public class TowerData {
    private int positionX;
    private int positionY;
    private int kills;
    private int shotsFired;
    private int wavePlaced;
    private float attackCooldown;
    private float goldCost;
    private float essenceCost;
    private float momentumCost;
    private double damageDealt;
    private TowerType towerType;

    public TowerData(TowerType towerType, int positionX, int positionY, double damageDealt, int kills, int shotsFired, int wavePlaced) {
        // Fitness is calculated externally.
        this.towerType = towerType;
        this.positionX = positionX;
        this.positionY = positionY;
        this.damageDealt = damageDealt;
        this.kills = kills;
        this.shotsFired = shotsFired;
        this.attackCooldown = towerType.getAttackCooldown();
        this.goldCost = towerType.getGoldCost();
        this.essenceCost = towerType.getEssenceCost();
        this.momentumCost = towerType.getMomentumCost();
        this.wavePlaced = wavePlaced;
    }
    /*
    Merely just kills and damageDealt are not enough data, imagine a tower placed on wave 1 that costs 1 gold and deal 1 dmg/second,
    this tower might have 1000 damage by the end of the game, but imagine another tower placed on the last wave that costs 1 gold
    and deals 999 dmg/second, it clearly is better, but the AI might think it's worse due to the lower total damage.
    Thus, we need to know the DPS and also the time that the tower spent hitting (or the shotsFired), that way we can calculate the dps
    while considering that the tower might not attack at every second of the round.
    With the tower cost, attackCooldown, damageDealt and shotsFired parameters, we can calculate it better.
    Since attackCooldown and price are constant and gettable by the towerType, no need to create the parameters for it.
     */

    public int getWavePlaced() {
        return wavePlaced;
    }

    public void recordDamage(double damage) {
        this.damageDealt += damage;
    }

    public void recordKill(int killCount) {
        this.kills += killCount;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public double getDamageDealt() {
        return damageDealt;
    }

    public int getKills() {
        return kills;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public float getAttackCooldown() {
        return attackCooldown;
    }

    public float getGoldCost() {
        return goldCost;
    }

    public float getEssenceCost() {
        return essenceCost;
    }

    public float getMomentumCost() {
        return momentumCost;
    }
}
