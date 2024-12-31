package me.BRZeph.AI.GameEnvironment;

import me.BRZeph.AI.Actions.ActionLogger;
import me.BRZeph.AI.Core.Action;
import me.BRZeph.AI.Core.State;
import me.BRZeph.Screens.Levels.BaseLevelScreen;
import me.BRZeph.core.Managers.CurrencyManager;
import me.BRZeph.core.Managers.TowerManager;
import me.BRZeph.core.Managers.WaveManager;
import me.BRZeph.entities.Map.TileMap;

import java.util.List;

public interface GameEnvironment {
    State getCurrentState();
    List<Action> getPossibleActions(State state, int wave);
    List<ActionLogger> getLoggedActions();
    WaveManager getWaveManager();
    CurrencyManager getCurrencyManager();
    TowerManager getTowerManager();
    TileMap getTileMap();
    BaseLevelScreen getGame();
    double performAction(Action action);
    boolean isGameOver();
    boolean isWaveActive();
    void reset();
}
