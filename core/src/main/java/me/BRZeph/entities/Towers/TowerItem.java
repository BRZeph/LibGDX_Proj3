package me.BRZeph.entities.Towers;

import me.BRZeph.utils.Constants;
import me.BRZeph.utils.enums.TowerType;

public class TowerItem {
    private TowerType type;
    private int price;

    public TowerItem(TowerType type) {
        this.type = type;
        this.price = TowerManager.getPrice(type);
    }

    public int getPrice() {
        return price;
    }

    public TowerType getType() {
        return type;
    }

    public boolean canBeBought(int playerGold) {
        return playerGold >= price;
    }
}
