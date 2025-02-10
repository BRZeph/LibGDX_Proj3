package me.BRZeph.AI.GameEnvironment;

import me.BRZeph.AI.Actions.ActionLogger;
import me.BRZeph.AI.Actions.PlaceTower;
import me.BRZeph.AI.Actions.StartWave;
import me.BRZeph.AI.Core.Action;
import me.BRZeph.AI.Core.State;
import me.BRZeph.Screens.Levels.BaseLevelScreen;
import me.BRZeph.Screens.Levels.Level1Screen;
import me.BRZeph.TowerDefenseGame;
import me.BRZeph.core.CurrencyManager;
import me.BRZeph.entities.Towers.TowerManager;
import me.BRZeph.core.WaveSystem.WaveManager;
import me.BRZeph.core.Map.Tile;
import me.BRZeph.core.Map.TileMap;
import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.Towers.TowerType;

import java.util.ArrayList;
import java.util.List;

import static me.BRZeph.core.Map.TileType.getTileFromTowerItem;

public class StandardLevelsEnvironment implements GameEnvironment {
    private Level1Screen game;
    private List<ActionLogger> loggedActions = new ArrayList<>();

    public StandardLevelsEnvironment(Level1Screen game) {
        this.game = game;
        this.game.setAI(true);
    }

    @Override
    public State getCurrentState() {
        List<Tower> towers = game.getTowerManager().getTowers();
        float gold = game.getCurrencyManager().getGold();
        float essence = game.getCurrencyManager().getEssence();
        float momentum = game.getCurrencyManager().getMomentum();
        int currentWave = game.getWaveManager().getCurrentWaveIndex();
        float health = game.getPlayerCurrentHealth();
        TileMap tileMap = game.getTileMap();

        return new State(towers, gold, essence, momentum, currentWave, health, tileMap);
    }

    @Override
    public List<Action> getPossibleActions(State state, int wave) {
        List<Action> actions = new ArrayList<>();

        if (state.getCurrentWave() != 0) {
            actions.add(new StartWave(wave));
        }
        TileMap tileMap = state.getTileMap();

        List<Tile> buildableTiles = tileMap.getBuildableTiles();

        for (TowerType towerType : TowerType.values()) {

            if (TowerManager.canBuyTower(state.getCurrencyManager(), towerType)) {

                Tile optimalTile = getOptimalTile(towerType);

                for (Tile tile : buildableTiles) {
                    actions.add(new PlaceTower(towerType, tileMap, getTileFromTowerItem(towerType), tile.getX(), tile.getY(), wave));
                }
            }
        }

        return actions;
    }

    private static Tile getOptimalTile(TowerType type){
        /*
        After each game is played, it should save the best positions for each tower, that way the AI will learn to position
cannon tower where the path make a turn etc.
         */
        return null;
    }

    @Override
    public double performAction(Action action) {
        double immediateReward = action.performAction(this);

        loggedActions.add(new ActionLogger(getCurrentState(), action));

        return immediateReward; // Return immediate reward (if any)
    }

    @Override
    public List<ActionLogger> getLoggedActions() {
        return loggedActions;
    }

    @Override
    public WaveManager getWaveManager() {
        return game.getWaveManager();
    }

    @Override
    public CurrencyManager getCurrencyManager() {
        return game.getCurrencyManager();
    }

    @Override
    public TowerManager getTowerManager() {
        return game.getTowerManager();
    }

    @Override
    public TileMap getTileMap() {
        return game.getTileMap();
    }

    @Override
    public BaseLevelScreen getGame() {
        return game;
    }

    @Override
    public void reset() {
        this.game = new Level1Screen(TowerDefenseGame.getScreenManager(), TowerDefenseGame.getAssetManager());
    }

    @Override
    public boolean isGameOver() {
        return game.getPlayerCurrentHealth() <= 0 || game.getWaveManager().isFinishedLevel() || game.getWaveManager().getCurrentWaveIndex() > 1;
    }

    @Override
    public boolean isWaveActive() {
        return !game.getWaveManager().isCanStartNewWave();
    }
}
