package me.BRZeph.core.Managers;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import me.BRZeph.AI.Core.QLearningAgent;
import me.BRZeph.AI.Core.QLearningTrainer;
import me.BRZeph.AI.GameEnvironment.AIScreen;
import me.BRZeph.AI.GameEnvironment.GameEnvironment;
import me.BRZeph.AI.GameEnvironment.StandardLevelsEnvironment;
import me.BRZeph.Screens.Levels.*;
import me.BRZeph.Screens.MiscelaneousScreens.ConfigurationScreen;
import me.BRZeph.Screens.MiscelaneousScreens.LevelSelectorScreen;
import me.BRZeph.Screens.MiscelaneousScreens.MainMenuScreen;
import me.BRZeph.TowerDefenseGame;
import me.BRZeph.utils.GlobalUtils;

import static me.BRZeph.utils.Constants.AIValues.*;

public class ScreenManager {
    private final Game game;
    private final AssetManager assetManager;

    private Screen currentScreen;

    public ScreenManager(Game game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
    }

    public void showMainMenu() {
        switchScreen(new MainMenuScreen(this, assetManager));
    }

    public void showConfiguration() {
        switchScreen(new ConfigurationScreen(this, assetManager));
    }

    public void showLevelSelector() {
        switchScreen(new LevelSelectorScreen(this, assetManager));
    }

    public void beginAI(int levelNumber) {
        GameEnvironment environment;
        switch (levelNumber){
            case 1:
                environment = new StandardLevelsEnvironment(new Level1Screen(this, TowerDefenseGame.getAssetManager()));
                break;
            default:
                throw new IllegalArgumentException("Unregistered class -> Level" + levelNumber + "Screen");
        }

        QLearningAgent agent = new QLearningAgent(STARTING_LEARNING_RATE, STARTING_DISCOUNT_FACTOR, STARTING_EXPLORATION_RATE);
        QLearningTrainer trainer = new QLearningTrainer(agent, environment);

        switchScreen(new AIScreen(environment, trainer));
    }

    public void showLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                switchScreen(new Level1Screen(this, assetManager));
                break;
            case 2:
                switchScreen(new Level2Screen(this, assetManager));
                break;
            case 3:
                switchScreen(new Level3Screen(this, assetManager));
                break;
            default:
                throw new IllegalArgumentException("Illegal level selected: " + levelNumber);
        }
    }

    private void switchScreen(Screen newScreen) {
        GlobalUtils.consoleLog("[INFO]: Redirecting from " + currentScreen + " to " + newScreen);
        if (currentScreen != null) {
            GlobalUtils.consoleLog("[INFO]: Disposing, hiding, and destroying inputProcessor from " + currentScreen);
            currentScreen.hide();
            currentScreen.dispose();
            Gdx.input.setInputProcessor(null);
        }

        currentScreen = newScreen;
        game.setScreen(currentScreen);

        if (currentScreen instanceof InputProcessor) {
            GlobalUtils.consoleLog("[INFO]: Setting inputProcessor for " + currentScreen);
            Gdx.input.setInputProcessor((InputProcessor) currentScreen);
        } else {
            Gdx.input.setInputProcessor(null);
        }
    }

    public void dispose() {
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
