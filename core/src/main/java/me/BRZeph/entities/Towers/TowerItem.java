package me.BRZeph.entities.Towers;

import me.BRZeph.core.Managers.CurrencyManager;

public class TowerItem {
    private TowerType type;
    private float goldPrice, essencePrice, momentumPrice;

    public TowerItem(TowerType type) {
        this.type = type;
        this.goldPrice = getGoldPrice(type);
        this.essencePrice = getEssencePrice(type);
        this.momentumPrice = getMomentumPrice(type);
    }

    @Override
    public String toString() {
        return "TowerItem{" +
            "type=" + type.name() +
            ", goldPrice=" + goldPrice +
            ", essencePrice=" + essencePrice +
            ", momentumPrice=" + momentumPrice +
            '}';
    }

    public static float getGoldPrice(TowerType type) {
        return type.getGoldCost();
    }

    public static float getEssencePrice(TowerType type) {
        return type.getEssenceCost();
    }

    public static float getMomentumPrice(TowerType type) {
        return type.getMomentumCost();
    }

    public TowerType getType() {
        return type;
    }

    public boolean canBeBought(float playerGold, float playerEssence, float playerMomentum) {
        return playerGold >= goldPrice && playerEssence >= essencePrice && playerMomentum >= momentumPrice;
    }

    public boolean canBeBought(CurrencyManager currencyManager) {
        return currencyManager.getGold() >= goldPrice && currencyManager.getEssence() >= essencePrice &&
            currencyManager.getMomentum() >= momentumPrice;
    }

    public float getGoldPrice() {
        return goldPrice;
    }

    public void setGoldPrice(float goldPrice) {
        this.goldPrice = goldPrice;
    }

    public float getEssencePrice() {
        return essencePrice;
    }

    public void setEssencePrice(float essencePrice) {
        this.essencePrice = essencePrice;
    }

    public float getMomentumPrice() {
        return momentumPrice;
    }

    public void setMomentumPrice(float momentumPrice) {
        this.momentumPrice = momentumPrice;
    }
}
