package me.BRZeph.AI.Actions;

import me.BRZeph.AI.GameEnvironment.GameEnvironment;
import me.BRZeph.AI.Core.Action;

public class UpgradeTower implements Action {

    private float reward;

    public UpgradeTower(){
        reward = 0;
    }
    @Override
    public double performAction(GameEnvironment gameEnvironment) {
        return 0;
    }

    @Override
    public String toString() {
        return "UpgradeTowerAction{" +
            "reward=" + reward +
            '}';
    }
}
