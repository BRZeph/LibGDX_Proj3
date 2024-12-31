package me.BRZeph.AI.GameEnvironment;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import me.BRZeph.AI.Actions.PlaceTower;
import me.BRZeph.AI.Actions.SellTower;
import me.BRZeph.AI.Actions.StartWave;
import me.BRZeph.AI.Core.QLearningTrainer;
import me.BRZeph.AI.GameData.Utils.GameDataExporter;
import me.BRZeph.AI.Actions.ActionLogger;
import me.BRZeph.AI.GameData.Utils.QTableSaver;
import me.BRZeph.AI.Core.Action;
import me.BRZeph.AI.Core.State;
import me.BRZeph.Screens.Levels.BaseLevelScreen;
import me.BRZeph.TowerDefenseGame;
import me.BRZeph.core.Managers.WaveManager;
import me.BRZeph.entities.Towers.PlacedTower.Tower;

import java.util.List;

import static me.BRZeph.utils.Constants.AIValues.*;
import static me.BRZeph.utils.Constants.CameraValues.*;
import static me.BRZeph.utils.GlobalUtils.consoleLog;

public class AIScreen extends BaseLevelScreen implements InputProcessor {
    private GameEnvironment environment;
    private QLearningTrainer trainer;
    private OrthographicCamera camera;
    private int waveActions;
    private boolean playerDead;
    private boolean finished;

    private int episodeCount = 1;

    private QTableSaver qTableSaver;

    public AIScreen(GameEnvironment environment, QLearningTrainer trainer) {
        super(TowerDefenseGame.getScreenManager(), TowerDefenseGame.getAssetManager()); // Allow input processor.
        this.environment = environment;
        this.trainer = trainer;
        camera = environment.getGame().getCamera();
        this.waveActions = 0;
        this.playerDead = false;
        this.finished = false;

        qTableSaver = new QTableSaver();
    }

    @Override
    public void show() {
        consoleLog("[INFO] AI Screen started for level 1");
    }

    @Override
    public void render(float delta) {
        if (episodeCount >= MAX_EPISODES) {
            consoleLog("[INFO] Maximum number of episodes reached. Ending training." + episodeCount);

            qTableSaver.saveQTable(trainer.getAgent().getQTable());

            TowerDefenseGame.getScreenManager().showMainMenu();

//            consoleLog("[INFO] Restarting AI for level 1");
//            TowerDefenseGame.getScreenManager().beginAI(1);
        }

        environment.getGame().render(delta);

        if(environment.getGame().getWaveManager().isFinishedLevel()){
            finished = true;
        }

        if(environment.getGame().isPlayerDead()){
            playerDead = true;
            finished = true;
        }

        if (!environment.isWaveActive()) {
            handleAI(delta);
        } else {
            waveActions = 0;
        }

        if (finished){
            endEpisode();
            startNewEpisode();
        }
    }

    private void handleAI(float delta) {
        waveActions += 1;

        State currentState = environment.getCurrentState();
        List<Action> possibleActions = environment.getPossibleActions(currentState, environment.getWaveManager().getCurrentWaveIndex());

        if (!possibleActions.isEmpty()) {
            Action action = trainer.getAgent().chooseAction(currentState, possibleActions);

            double reward = environment.performAction(action);

            State nextState = environment.getCurrentState();
            nextState.setCurrentWave(nextState.getCurrentWave() + 1); // Simulate next wave for the state
            List<Action> nextActions = environment.getPossibleActions(nextState, environment.getWaveManager().getCurrentWaveIndex());

            trainer.getAgent().updateQValue(currentState, action, reward, nextState, nextActions);
        }

        if (shouldStartWave(currentState, possibleActions)) {
            environment.getWaveManager().startNextWave();
            waveActions = 0;
        }
    }

    private void startNewEpisode() {
        episodeCount++;
        consoleLog("[INFO] Starting episode " + episodeCount);

        environment.reset();
        camera = environment.getGame().getCamera();
        environment.getGame().setAI(true);

        playerDead = false;
        finished = false;
        waveActions = 0;

        trainer.getAgent().adjustExplorationRate(episodeCount);
        trainer.getAgent().adjustLearningRate(episodeCount);
    }

    private void endEpisode() {
        consoleLog("[INFO] Episode " + episodeCount + " ended.");

        double winLoseFitness;
        if (playerDead) {
            consoleLog("[INFO] Player died. Applying death penalty.");
            winLoseFitness = LOSE_REWARD;
        } else {
            consoleLog("[INFO] Player survived. Rewarding success.");
            winLoseFitness = SUCCESS_REWARD;
        }

        processLoggedActions(winLoseFitness);

        trainer.getAgent().finalizeEpisode(episodeCount);
    }

    public void processLoggedActions(double winLoseFitness) {
        double fitness = winLoseFitness;
        for (ActionLogger loggedAction : environment.getLoggedActions()) {

            if (loggedAction.getAction() instanceof PlaceTower){

                PlaceTower placeTower = (PlaceTower) loggedAction.getAction();
                Tower tower = placeTower.getLastPlacedTower();

                fitness += GameDataExporter.getFitness(tower);

                trainer.getAgent().updateQValue(
                    loggedAction.getState(),
                    loggedAction.getAction(),
                    fitness,
                    environment.getCurrentState(),
                    environment.getPossibleActions(environment.getCurrentState(), environment.getWaveManager().getCurrentWaveIndex())
                );

            } else if (loggedAction.getAction() instanceof StartWave){

                fitness += ((StartWave) loggedAction.getAction()).getReward();
                if (((StartWave) loggedAction.getAction()).getSkippedWave() - getWaveManager().getCurrentWaveIndex() > 3){
                    // Player died too long after skipping wave, probably did not have anything to do with this action, re add reward.
                    fitness -= LOSE_REWARD; // Re adding lose_reward since this action most likely did not cause the losing.
                }
                trainer.getAgent().updateQValue(
                    loggedAction.getState(),
                    loggedAction.getAction(),
                    fitness,
                    environment.getCurrentState(),
                    environment.getPossibleActions(environment.getCurrentState(), environment.getWaveManager().getCurrentWaveIndex())
                );

            } else if (loggedAction.getAction() instanceof SellTower){
                /*
                Implement sell tower here.
                 */
            }
        }

        environment.getLoggedActions().clear();
    }

    private boolean shouldStartWave(State nextWaveState, List<Action> actions) {
//        double waveDifficultyThreshold = calculateWaveDifficultyThreshold(nextWaveState);
//        double defenseStrength = calculateDefenseStrength(nextWaveState);

//        if (defenseStrength >= waveDifficultyThreshold && environment.hasSufficientResources()) {
//            return true;
//        }

//        return false;

        return waveActions > ACTIONS_LIMIT_PER_WAVE;
    }

//    private double calculateWaveDifficultyThreshold(State nextWaveState) {
//        int currentWave = nextWaveState.getCurrentWave();
//        double enemyCount = environment.getWaveManager().getEnemyCountForWave(currentWave);
//        double enemyHealth = environment.getWaveManager().getAverageEnemyHealthForWave(currentWave);
//
//        return (enemyCount * enemyHealth) * 1.5;
//    }
//
//    private double calculateDefenseStrength(State nextWaveState) {
//        double totalDamage = 0;
//        double totalRange = 0;
//        double towerCount = 0;
//
//        for (Tower tower : environment.getPlacedTowers()) {
//            totalDamage += tower.getDamage();
//            totalRange += tower.getRange();
//            towerCount++;
//        }
//
//        return totalDamage * 2 + totalRange * 0.5 + towerCount * 3;
//    }




    @Override
    public boolean scrolled(float v, float amountY) {
        float zoomAmount = amountY * CAMERA_ZOOM_SPEED;
        camera.zoom += zoomAmount;

        camera.zoom = Math.max(
            CAMERA_MIN_ZOOM,
            Math.min(CAMERA_MAX_ZOOM, camera.zoom)
        );
        return true;
    }








    // ALL METHODS BELLOW HERE ARE NOT BEING USED.
    @Override
    public void resize(int width, int height) {
        // Handle resizing
    }

    @Override
    public void pause() {
        // Handle pause
    }

    @Override
    public void resume() {
        // Handle resume
    }

    @Override
    public void hide() {
        // Clean up resources if needed
    }

    @Override
    public void dispose() {
        // Dispose of resources
    }

    @Override
    public void setAI(boolean value) {

    }

    @Override
    protected void loadAssets() {

    }

    @Override
    public boolean isPlayerDead() {
        return environment.getGame().isPlayerDead();
    }

    @Override
    public OrthographicCamera getCamera() {
        return environment.getGame().getCamera();
    }

    @Override
    public WaveManager getWaveManager() {
        return environment.getWaveManager();
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }
}
