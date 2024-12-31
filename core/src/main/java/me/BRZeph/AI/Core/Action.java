package me.BRZeph.AI.Core;

import me.BRZeph.AI.GameEnvironment.GameEnvironment;

public interface Action {
    double performAction(GameEnvironment gameEnvironment);
}
