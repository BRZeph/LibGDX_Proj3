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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import me.BRZeph.Main;
import me.BRZeph.core.ScreenManager;
import me.BRZeph.core.LevelAssetsManager;
import me.BRZeph.core.UI.ScrollableUI;
import me.BRZeph.core.UI.StaticGridButtonUI;
import me.BRZeph.core.UI.WorldGridButtonUI;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.Monster;
import me.BRZeph.entities.Player;
import me.BRZeph.entities.Towers.PlacedTower;
import me.BRZeph.entities.Towers.TowerManager;
import me.BRZeph.entities.WaveSystem.WaveManager;
import me.BRZeph.entities.currency.CurrencyManager;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;
import me.BRZeph.utils.enums.TowerType;
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

    private Texture towerMenuTexture;
    private Texture heartTexture;
    private Texture testButtonTexture;
    private Texture towerShopBackgroundTexture;
    private Texture towerShopfrontTexture;

    private List<StaticGridButtonUI> staticButtonList;
    private List<WorldGridButtonUI> worldButtonList;
    private List<ScrollableUI> scrollableUIList;
    private TileMap tileMap;
    private Player player;
    private WaveManager waveManager;
    private TowerManager towerManager;
    private CurrencyManager currencyManager;

    private float screenClock;
    private float mapWidth = 1024;
    private float mapHeight = 1024;
    private boolean debug;
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
        staticButtonList = new ArrayList<>();
        worldButtonList = new ArrayList<>();
        scrollableUIList = new ArrayList<>();

        loadAssets();
        initializeButtons();
        initializeTowerShopUI();

        debug = false;
        player = new Player(0, 0, 50);
        tileMap = new TileMap(Constants.Paths.MapsPath.LEVEL_1_MAP,
            Constants.AssetsTiles.TILE_WIDTH, Constants.AssetsTiles.TILE_HEIGHT);
        tileMap.loadMapAndFindPath(Constants.Paths.MapsPath.LEVEL_1_MAP);
        waveManager = new WaveManager(Constants.WaveValues.getLevel1Waves(tileMap.getPath()));
        towerManager = new TowerManager();
        this.currencyManager = new CurrencyManager();

        playerMaxHealth = Constants.Values.PlayerValues.LEVEL_1_PLAYER_HEALTH;
        playerCurrentHealth = playerMaxHealth;
    }

    /*
    PRIORITY:
    1- (DONE): make currency system.
    2- make background UI for the "TOWER_MENU_UI" and implement it with a scrollable bar.
    2.1- give up on the idea of scrollable ui, create an ui will different pages.
    3- finish towers system.

    TO CONSIDER:
    1- consider removing the TowerType class since it's currently useless.

    TO IMPROVE:
    1- make the path finding algorithm more optimized, try version 3 of level 1 map (third map in the level_2_map.txt).
    2- make a better currency system (Values.CurrencyTypes). Consider making individual classes for each.
    3- make PlacedTower class abstract and every tower such as ARCHER_TOWER an implementation of PlacedTower.
    4- (DONE): improve wave system so that the wave can end at some point, consider adding a "finalBehavior" with a duration.

    TO FIX:
    1- fix issue that is caused by a map not having a path.
    2- (DONE): fix issue of monster not following the path. Maybe the path is being defined wrongly due to the map load with the Y axis inverted?
    3- fix GlobalUtils.adjustTextWidth().
    4- (DONE): fix the Map of behaviors not working with more than 1 behavior (see line 151 of constants.java).
    5- fix inverted Y position on TileMap.Render().

    TO IMPLEMENT:
    1- (DONE) implement player (green square for now) with movement.
    2- implement button for starting the wave with hotkey. "DONE" by using L to start the wave.
    3- (DONE) implement clock during the wave.
    4- (DONE) implement wave count ("Wave 4/30").
    5- implement game mechanics (towers, monsters and so on).
    6- (DONE) implement a better system for defining what will spawn when in each wave of each level.
    7- implement "coin system", whenever the monsters die, the player should receive "coin" currency.
    8- implement monster death generating coins and so on in updateWave().
    9- implement PlacedTower.update().
    10- implement endOfLevel() which happens when the last wave ends or the player dies or the player quits.
     */

    @Override
    protected void loadAssets() {
        LevelAssetsManager.loadCommonAssets(assetManager);
        LevelAssetsManager.loadSpecificAssets(1, assetManager);

        towerMenuTexture = assetManager.get(Constants.Paths.UIPath.TOWER_MENU_UI);
        heartTexture = assetManager.get(Constants.Paths.UIPath.HEART_UI);
        testButtonTexture = assetManager.get(Constants.Values.UIValues.ButtonsValues.TEST_BUTTON_TEXTURE_PATH, Texture.class);
        towerShopBackgroundTexture = assetManager.get(Constants.Paths.UIPath.TOWER_SHOP_BACKGROUND_UI);
        towerShopfrontTexture = assetManager.get(Constants.Paths.UIPath.TOWER_SHOP_FRONT_UI);
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
        updateStaticButtons();
        updateCamera();
        updateWave(delta);
        updateTowers(delta);

        renderTileMap(); //this also render the towers, there is no need to call "tower.render()" or whatever.
        renderWave();
        renderPath();
        renderUI();
        renderPlayer();
    }

    private void initializeButtons() {
        staticButtonList.add(new StaticGridButtonUI(100, 100, 200, 100, testButtonTexture, () -> {
            waveManager.startNextWave();
            GlobalUtils.consoleLog("Clicked button :D");
        }));
    }

    private void renderShopUI(){
        for (ScrollableUI scrollableUI : scrollableUIList){
            scrollableUI.render(spriteBatch);
        }
    }

    private void initializeTowerShopUI() {
        int startX = Constants.SCREEN_WIDTH - Constants.Values.UIValues.TOWER_MENU_WIDTH;
        int startY = Constants.SCREEN_HEIGHT - Constants.Values.UIValues.TOWER_MENU_HEIGHT;
        int length = 64;

        List<StaticGridButtonUI> buttons = new ArrayList<>();
        int y = 0;
        for (int i = 0; i < 16 ;i++){
            int n = i;
            if (i % 2 == 1) {
                buttons.add(
                    new StaticGridButtonUI(startX + 2*length, y*length, length, length, testButtonTexture, () -> {
                        GlobalUtils.consoleLog("Clicked on button " + n);
                    })
                );
            } else {
                y++;
                buttons.add(
                    new StaticGridButtonUI(startX + length, y*length, length, length, testButtonTexture, () -> {
                        GlobalUtils.consoleLog("Clicked on button " + n);
                    })
                );
            }
        }
        scrollableUIList.add(new ScrollableUI(
            startX, startY, startX + 250, Constants.SCREEN_HEIGHT, 2, 8,
            64,64,5,
            towerShopBackgroundTexture, towerShopfrontTexture, buttons
        ));
    }

    private void updateStaticButtons() {

        if (Gdx.input.justTouched()){
            float mouseX = Gdx.input.getX();
            float mouseY = Constants.SCREEN_HEIGHT - Gdx.input.getY();

            for (ScrollableUI scrollableUI : scrollableUIList){
                for (StaticGridButtonUI staticGridButtonUI : scrollableUI.getButtons()){
                    staticGridButtonUI.checkClick(mouseX, mouseY);
                }
            }

            for (StaticGridButtonUI staticButton : staticButtonList){
                staticButton.checkClick(mouseX, mouseY);
            }

        }
    }

    private void renderStaticButtons() {
        for (StaticGridButtonUI staticButton : staticButtonList){
            staticButton.render(spriteBatch);
        }
    }

    private void updateTowers(float delta) {
        for (PlacedTower tower : towerManager.getTowers()){
            tower.update(delta);
        }
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

        List<Monster> reachedEnd = waveManager.getCurrentWave().getMonsterReachedEnd();
        List<Monster> monstersAlive = waveManager.getCurrentWave().getMonsterList();
        for (Monster monster : reachedEnd) {
            playerCurrentHealth -= monster.getType().getNexusDmg();
            monstersAlive.remove(monster);
        }
        reachedEnd.clear();
        List<Monster> died = waveManager.getCurrentWave().getMonsterDied();
        for (Monster monster : died) {
            monstersAlive.remove(monster);
            currencyManager.addMonsterLoot(monster.getType());
            // IMPLEMENT MONSTER DEATH HERE.
        }
        died.clear();
    }

    private void renderTileMap(){
        tileMap.render(spriteBatch);
    }

    private void renderWave() {
        waveManager.render(spriteBatch, font, shapeRenderer);
    }

    private void renderPath() {
        if (debug){
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

//        renderTowerMenuUI();
        renderShopUI();
        renderFPSUI();
        renderPlayerHealthUI();
        renderCurrentWaveUI();
        renderCurrencyUI();
        renderTipsUI(); //REMOVE LATER
        renderStaticButtons();

        spriteBatch.setProjectionMatrix(originalProjection);
    }

    private void renderCurrencyUI() {
        int width = Constants.Values.UIValues.CURRENCY_X_POS_DECREASE;
        int startX = Constants.SCREEN_WIDTH - width - Constants.Values.UIValues.TOWER_MENU_WIDTH;
        int startY = 100;
        String message = "Gold -> " + currencyManager.getGold() + "\n" +
                         "Essence -> " + currencyManager.getEssence() + "\n" +
                         "Momentum -> " + currencyManager.getMomentum();

        float originalScaleX = font.getData().scaleX;
        float originalScaleY = font.getData().scaleY;
        font.getData().setScale(2f);

        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);
        font.draw(spriteBatch, message, startX, startY);
        spriteBatch.end();

        font.getData().setScale(originalScaleX, originalScaleY);
    }

    private void renderTipsUI() {
        /*
        M -> start wave
        F6 -> toggle debug
        F7 -> main menu
        F8 -> deal 1 dmg to all spawned monsters.
        F10 -> change tile type.
         */
        if (debug) {
            float startX = 10;
            float startY = 500;
            String message = "M -> start wave \n" +
                "F6 -> toggle debug \n" +
                "F7 -> main menu \n" +
                "F8 -> deal 1 dmg to mobs";
            spriteBatch.begin();
            spriteBatch.setColor(Color.WHITE);
            font.draw(spriteBatch, message, startX, startY);
            spriteBatch.end();
        }
    }

    private void renderCurrentWaveUI() {
        int width = Constants.Values.UIValues.CURRENT_WAVE_WIDTH;
        int startX = Constants.SCREEN_WIDTH - width - Constants.Values.UIValues.TOWER_MENU_WIDTH;
        int startY = Constants.SCREEN_HEIGHT - Constants.Values.UIValues.CURRENT_WAVE_HEIGHT_DECREASE;
        float waveClock = waveManager.getCurrentWave().getWaveClock();
        int minutes = (int)(waveClock/60);
        int seconds = (int)(waveClock - minutes*60);

        String message = "Wave: " + (waveManager.getCurrentWaveIndex() + 1) + "/" + Constants.WaveValues.WAVE_1_MAX_WAVE;

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

        orthographicCamera.zoom = Math.max(
            Constants.CameraValues.CAMERA_MIN_ZOOM,
            Math.min(Constants.CameraValues.CAMERA_MAX_ZOOM, orthographicCamera.zoom)
        );
        return true;
    }
private int tileCount = 0;
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.M){
            waveManager.startNextWave();
        }
        if (keycode == Input.Keys.F6) {
            debug = !debug;
        }
        if (keycode == Input.Keys.F7) {
            Main.getScreenManager().showMainMenu();
        }
        if (keycode == Input.Keys.F8){
            for (Monster monster : waveManager.getCurrentWave().getMonsterList()){
                monster.setCurrentHealth(monster.getCurrentHealth() - 1);
            }
        }
        if (keycode == Input.Keys.F10){
            towerManager.placeTower(tileMap, TileType.ARCHER_TOWER, TowerType.ARCHER, tileCount, 2);
            tileCount++;
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
