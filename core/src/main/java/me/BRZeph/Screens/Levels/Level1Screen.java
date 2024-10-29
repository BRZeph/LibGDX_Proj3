package me.BRZeph.Screens.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import me.BRZeph.Main;
import me.BRZeph.core.ScreenManager;
import me.BRZeph.core.LevelAssetsManager;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Monster;
import me.BRZeph.entities.Player;
import me.BRZeph.entities.WaveSystem.WaveManager;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;
import me.BRZeph.utils.enums.MonsterType;
import me.BRZeph.utils.pathFinding.Node;

import java.util.ArrayList;
import java.util.List;

public class Level1Screen extends BaseLevelScreen implements InputProcessor {
    private SpriteBatch spriteBatch;
    private Music music;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera orthographicCamera;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private Texture backgroundTexture;
    private Texture towerMenuTexture;
    private Texture heartTexture;
    private TileMap tileMap;
    private Player player;
    private WaveManager waveManager;

    private float screenClock;
    private float waveClock;
    private float mapWidth = 1024;
    private float mapHeight = 1024;
    private boolean showPath;
    private List<Monster> monsterList;
    private List<Monster> monsterReachedEndList;
    private List<Monster> monsterDied;
    private float playerMaxHealth;
    private float playerCurrentHealth;

    public Level1Screen(ScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        font = new BitmapFont();
        glyphLayout = new GlyphLayout();

        loadAssets();

        showPath = false;
        monsterList = new ArrayList<>();
        monsterReachedEndList = new ArrayList<>();
        monsterDied = new ArrayList<>();
        player = new Player(0, 0, 50);
        tileMap = new TileMap(Constants.Paths.MapsPath.LEVEL_1_MAP,
            Constants.AssetsTiles.TILE_WIDTH, Constants.AssetsTiles.TILE_HEIGHT);
        tileMap.loadMapAndFindPath(Constants.Paths.MapsPath.LEVEL_1_MAP);
        waveManager = new WaveManager(10);

        playerMaxHealth = Constants.Values.PlayerValues.LEVEL_1_PLAYER_HEALTH;
        playerCurrentHealth = playerMaxHealth;
    }

    /*
    TO IMPROVE:
    1- make the path finding algorithm more optimized, try version 3 of level 1 map (third map in the level_2_map.txt).

    TO FIX:
    1- fix issue that is caused by a map not having a path.
    2- (DONE): fix issue of monster not following the path. Maybe the path is being defined wrongly due to the map load with the Y axis inverted?
    3- fix GlobalUtils.adjustTextWidth();

    TO IMPLEMENT:
    1- implement player (green square for now) with movement. DONE.
    2- implement button for starting the wave with hotkey. "DONE" by using L to start the wave.
    3- implement clock during the wave.
    4- implement wave count ("Wave 4/30").
    5- implement game mechanics (towers, monsters and so on).
    6- implement a better system for defining what will spawn when in each wave of each level.
    7- implement "coin system", whenever the monsters die, the player should receive "coin" currency.
     */

    @Override
    protected void loadAssets() {
        LevelAssetsManager.loadCommonAssets(assetManager);
        LevelAssetsManager.loadSpecificAssets(1, assetManager);

        backgroundTexture = assetManager.get(Constants.Paths.ScreensTexturesPath.LEVEL_1_BACKGROUND);
        towerMenuTexture = assetManager.get(Constants.Paths.UIPath.TOWER_MENU_UI);
        heartTexture = assetManager.get(Constants.Paths.UIPath.HEART_UI);
    }

    @Override
    public void show() {
        // Reset camera position when screen is shown
        orthographicCamera.position.set(
            player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2,
            0);
        orthographicCamera.update();
    }

    @Override
    public void render(float delta) {
        screenClock += delta;

        updatePlayer(delta);
        updateCamera();
        updateWave(delta);

        renderTileMap();
        renderPath();
        renderWave(delta);
        renderUI();
        renderPlayer();





        handleMonster(delta);

        //REMOVE THIS LATER.
        handleMonsterRemoveLater(delta);
    }

    private void handleMonster(float delta) {
        for (Monster monster : waveManager.getCurrentWave().getMonsterDied()){
            waveManager.getCurrentWave().getMonsters().remove(monster);
        }
        for (Monster monster : waveManager.getCurrentWave().getMonsterReachedEndList()){
            playerCurrentHealth -= monster.getType().getNexusDmg();
            waveManager.getCurrentWave().getMonsters().remove(monster);
        }
        waveManager.getCurrentWave().getMonsterDied().clear();
        waveManager.getCurrentWave().getMonsterReachedEndList().clear();
    }

    private void handleMonsterRemoveLater(float delta) {
        for (Monster monster : monsterList){
            monster.update(delta, tileMap.getPath());
            monster.render(spriteBatch, font, shapeRenderer, orthographicCamera);
            if (monster.getCurrentHealth() <= 0){
                monsterDied.add(monster);
            }
            if (monster.isFinishedPath()){
                monsterReachedEndList.add(monster);
            }
        }
        for (Monster monster : monsterReachedEndList){
            playerCurrentHealth -= monster.getType().getNexusDmg();
            monsterList.remove(monster);
        }
        for (Monster monster : monsterDied){
            monsterList.remove(monster);
        }
        monsterReachedEndList.clear();
        monsterDied.clear();
    }

    private void updatePlayer(float delta) {
        player.handleInput(delta, tileMap);
    }

    private void updateCamera() {
        // Set the camera to follow the player.
        orthographicCamera.position.set(
            player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2,
            0
        );
        orthographicCamera.update();

        // Set the camera to the SpriteBatch.
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
    }

    private void updateWave(float delta) {
        waveManager.update(delta);
    }

    private void renderWave(float delta){
        waveManager.render(spriteBatch, delta, tileMap.getPath(), font, shapeRenderer, orthographicCamera);
    }

    private void renderTileMap(){
        tileMap.render(spriteBatch);
    }

    private void renderPath() {
        if (showPath){
            List<Node> path = tileMap.getPath();

            if (path != null) {

                for (int i = 0; i < path.size() - 1; i++) {
                    Node current = path.get(i);
                    Node next = path.get(i + 1);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.WHITE);

                    // Draw a line from the current node to the next node
                    shapeRenderer.line(
                        (float) ((0.5 + current.x) * Constants.AssetsTiles.TILE_WIDTH),
                        (float) ((0.5 + current.y) * Constants.AssetsTiles.TILE_HEIGHT),
                        (float) ((0.5 + next.x) * Constants.AssetsTiles.TILE_WIDTH),
                        (float) ((0.5 + next.y) * Constants.AssetsTiles.TILE_HEIGHT)
                    );
                    shapeRenderer.end();

                    // Draw nodes count.
                    String message = "Node -> " + i;
                    spriteBatch.begin();
                    font.draw(spriteBatch, message, (float)(0.5 + current.x) * Constants.AssetsTiles.TILE_WIDTH, (float)(0.5 + current.y) * Constants.AssetsTiles.TILE_HEIGHT);
                    spriteBatch.end();
                }
            } else {
                GlobalUtils.consoleLog("path null");
            }
        }
    }

    private void renderUI() {
        // Changing the spriteBatch projection matrix to make it easier to render UI and screen static elements.
        Matrix4 originalProjection = spriteBatch.getProjectionMatrix().cpy();
        spriteBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        renderTowerMenuUI();
        renderFPSUI();
        renderPlayerHealthUI();
        renderCurrentWaveUI();

        spriteBatch.setProjectionMatrix(originalProjection);
    }

    private void renderCurrentWaveUI() {
        int width = Constants.Values.UIValues.CURRENT_WAVE_WIDTH;
        int startX = Constants.SCREEN_WIDTH - width - Constants.Values.UIValues.TOWER_MENU_WIDTH;
        int startY = Constants.SCREEN_HEIGHT - Constants.Values.UIValues.CURRENT_WAVE_HEIGHT_DECREASE;
        float waveClock = waveManager.getCurrentWave().getWaveClock();
        int minutes = (int)(waveClock/60);
        int seconds = (int)(waveClock - minutes*60);

        String message = "Wave: " + waveManager.getCurrentWaveIndex() + "/" + waveManager.getTotalWaves();

        float originalScaleX = font.getData().scaleX;
        float originalScaleY = font.getData().scaleY;
        font.getData().setScale(2.5f);

        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);
        font.draw(spriteBatch, message, startX, startY);
        message = "Timer: " + minutes + ":" + seconds;
        font.draw(spriteBatch, message, startX, startY - Constants.Values.UIValues.CURRENT_WAVE_CLOCK_HEIGHT_DECREASE);
        spriteBatch.end();

        font.getData().setScale(originalScaleX, originalScaleY);
    }

    private void renderTowerMenuUI() {
        float startX = Constants.SCREEN_WIDTH - Constants.Values.UIValues.TOWER_MENU_WIDTH;
        float startY = Constants.SCREEN_HEIGHT - Constants.Values.UIValues.TOWER_MENU_HEIGHT;

        spriteBatch.begin();
        spriteBatch.draw(
            towerMenuTexture,
            startX,
            startY,
            Constants.Values.UIValues.TOWER_MENU_WIDTH,
            Constants.Values.UIValues.TOWER_MENU_HEIGHT
        );
        spriteBatch.end();
    }

    private void renderFPSUI() {
        float startX = Constants.Values.UIValues.FPS_X_POS;
        float startY = Constants.SCREEN_HEIGHT - Constants.Values.UIValues.FPS_Y_POS_DECREASE;
        float desiredWidth = Constants.Values.UIValues.FPS_WIDTH;
        float fps = Gdx.graphics.getFramesPerSecond();
        String message = "FPS: " + (int) fps;

//        String adjustedMessage = adjustTextWidth(message, desiredWidth, font, glyphLayout);

        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);
        font.draw(spriteBatch, message, startX, startY);
        spriteBatch.end();
    }

    private void renderPlayerHealthUI() {
        float startX = Constants.Values.UIValues.HEALTH_X_POS;
        float startY = Constants.SCREEN_HEIGHT - Constants.Values.UIValues.HEALTH_Y_POS_DECREASE;
        float desiredWidth = Constants.Values.UIValues.HEALTH_WIDTH;

        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);
        spriteBatch.draw(
            heartTexture,
            startX,
            startY,
            Constants.Values.UIValues.HEART_WIDTH,
            Constants.Values.UIValues.HEART_HEIGHT
        );
        spriteBatch.draw(
            heartTexture,
            startX + Constants.Values.UIValues.HEART_WIDTH + 2*Constants.Values.UIValues.HEALTH_HEART_GAP +
                145,
            startY,
            Constants.Values.UIValues.HEART_WIDTH,
            Constants.Values.UIValues.HEART_HEIGHT
        );
        spriteBatch.end();

        String message = playerCurrentHealth + " / " + playerMaxHealth;
//        String adjustedMessage = adjustTextWidth(message, desiredWidth, font, glyphLayout);

        float originalScaleX = font.getData().scaleX;
        float originalScaleY = font.getData().scaleY;
        spriteBatch.begin();
        font.getData().setScale(Constants.Values.UIValues.HEALTH_FONT_SIZE);
        font.draw(
            spriteBatch,
            message,
            startX + Constants.Values.UIValues.HEART_WIDTH + Constants.Values.UIValues.HEALTH_HEART_GAP,
            startY + (float) Constants.Values.UIValues.HEART_HEIGHT /2 + font.getData().xHeight/2
        );
        spriteBatch.end();
        font.getData().setScale(originalScaleX, originalScaleY);
    }

    private void renderPlayer() {
        player.render(shapeRenderer);
    }

    private void drawBackground() {
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture,
            (float) -Constants.SCREEN_WIDTH /2,
            (float) -Constants.SCREEN_HEIGHT /2,
            Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        GlobalUtils.consoleLog("Calling dispose method of Level1Screen");
        assetManager.unload(Constants.Paths.ScreensTexturesPath.LEVEL_1_BACKGROUND);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        float zoomAmount = amountY * Constants.CameraValues.CAMERA_ZOOM_SPEED;
        orthographicCamera.zoom += zoomAmount;

        // Clamp the zoom level using your constants
        orthographicCamera.zoom = Math.max(
            Constants.CameraValues.CAMERA_MIN_ZOOM,
            Math.min(Constants.CameraValues.CAMERA_MAX_ZOOM, orthographicCamera.zoom)
        );
        return true; // Indicates that the event was handled
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.L) {
//            waveManager.startNextWave();
            if (monsterList.size() % 2 == 0) {
                monsterList.add(new Monster(tileMap.getPathStartPointX(), tileMap.getPathStartPointY(), MonsterType.ZOMBIE));
            } else {
                monsterList.add(new Monster(tileMap.getPathStartPointX(), tileMap.getPathStartPointY(), MonsterType.SKELETON));
            }
            return true;
        }
        if (keycode == Input.Keys.M){
            waveManager.startNextWave();
        }
        if (keycode == Input.Keys.F6) {
            showPath = !showPath;
        }
        if (keycode == Input.Keys.F7) {
            Main.getScreenManager().showMainMenu();
        }
        if (keycode == Input.Keys.F8){
            for (Monster monster : monsterList){
                monster.setCurrentHealth(monster.getCurrentHealth() - 1);
            }
        }
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

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

    public float getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(float mapWidth) {
        this.mapWidth = mapWidth;
    }

    public float getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(float mapHeight) {
        this.mapHeight = mapHeight;
    }
}
