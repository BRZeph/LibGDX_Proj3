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
import com.badlogic.gdx.math.Vector3;
import me.BRZeph.Main;
import me.BRZeph.core.ScreenManager;
import me.BRZeph.core.LevelAssetsManager;
import me.BRZeph.core.UI.ScrollableUI;
import me.BRZeph.core.UI.StaticGridButtonUI;
import me.BRZeph.entities.Map.Tile;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.monster.Monster;
import me.BRZeph.entities.Player;
import me.BRZeph.entities.Towers.PlacedTower;
import me.BRZeph.entities.Towers.TowerItem;
import me.BRZeph.core.TowerManager;
import me.BRZeph.core.WaveManager;
import me.BRZeph.core.CurrencyManager;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.utils.pathFinding.Node;

import java.util.ArrayList;
import java.util.List;

import static me.BRZeph.utils.GlobalUtils.getTowerTypeFromTileType;

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
    private Texture selectedTowerBackgroundTexture;

    private List<StaticGridButtonUI> staticButtonList;
    private List<ScrollableUI> scrollableUIList;
    private TileMap tileMap;
    private Player player;
    private WaveManager waveManager;
    private TowerManager towerManager;
    private CurrencyManager currencyManager;

    private float screenClock;
    private float mapWidth = 1024;
    private float mapHeight = 1024;
    private boolean isPaused;
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
        scrollableUIList = new ArrayList<>();
        isPaused = false;

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
        currencyManager = new CurrencyManager();
        currencyManager.addGold(300);

        playerMaxHealth = Constants.Values.PlayerValues.LEVEL_1_PLAYER_HEALTH;
        playerCurrentHealth = playerMaxHealth;
    }

    /*
    PRIORITY:
    1- (DONE): make currency system.
    1.1- (DONE): finish implementing the buy system (line 200 of this class).
    2- (DONE): make background UI for the "TOWER_MENU_UI" and implement it with a scrollable bar.
    2.1- (DONE): give up on the idea of scrollable ui, create an ui will different pages.
    3- finish towers system.

    TO IMPROVE:
    1- make the path finding algorithm more optimized, try version 3 of level 1 map (third map in the level_2_map.txt).
    2- (DONE): make a better currency system (Values.CurrencyTypes). Consider making individual classes for each.
    3- make PlacedTower class abstract and every tower such as ARCHER_TOWER an implementation of PlacedTower.
    4- (DONE): improve wave system so that the wave can end at some point, consider adding a "finalBehavior" with a duration.

    TO FIX:
    1- fix issue that is caused by a map not having a path.
    2- (DONE): fix issue of monster not following the path. Maybe the path is being defined wrongly due to the map load with the Y axis inverted?
    3- fix GlobalUtils.adjustTextWidth().
    4- (DONE): fix the Map of behaviors not working with more than 1 behavior (see line 151 of constants.java).
    5- (DONE): fix inverted Y position on TileMap.Render().

    TO IMPLEMENT:
    1- (DONE): implement player (green square for now) with movement.
    2- (DONE): implement button for starting the wave with hotkey.
    3- (DONE): implement clock during the wave.
    4- (DONE): implement wave count ("Wave 4/30").
    5- implement basic game mechanics (towers, monsters, path and basic shop).
    6- (DONE): implement a better system for defining what will spawn when in each wave of each level.
    7- (DONE): implement "coin system", whenever the monsters die, the player should receive "coin" currency.
    8- (DONE): implement monster death generating coins and so on in updateWave().
    9- (DONE): implement PlacedTower.update().
    10- (DONE): implement endOfLevel() which happens when the last wave ends or the player dies or the player quits.
    11- implement "incomingDamage" mechanic to monster class.
    11.1- when a tower shoots a monster, the incomingDamage value increases.
    11.2- when a tower is deciding who to shoot, it should skip monsters with incomingDamage > currentHealth.
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
        selectedTowerBackgroundTexture = assetManager.get(Constants.Paths.UIPath.SELECTED_TOWER_BACKGROUND);
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

        if (!isPaused) {
            updatePlayer(delta);
            updateCamera();
            updateTowers(delta);
            updateWave(delta);
        }

        renderTileMap(); //this also render the towers, there is no need to call "tower.render()" or whatever.
        renderWave();
        renderTowerProjectiles();
        renderPath();
        renderUI();
        renderPlayer();
    }

    private void renderUI() {
        Matrix4 originalProjection = spriteBatch.getProjectionMatrix().cpy();
        spriteBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        renderSelectedTowerUI();
        renderShopUI();
        renderFPSUI();
        renderPlayerHealthUI();
        renderCurrentWaveUI();
        renderCurrencyUI();
        renderStaticButtons();
        renderHoldingItem();
        renderTipsUI(); //REMOVE LATER

        spriteBatch.setProjectionMatrix(originalProjection);
    }

    private void renderTowerProjectiles() {
        for (PlacedTower tower : towerManager.getTowers()) {
            tower.renderProjectiles(spriteBatch);
        }
    }

    private void renderSelectedTowerUI() {
        if (towerManager.getSelectedTower() != null){
            int tileX = towerManager.getSelectedTower().getxPos();
            int tileY = towerManager.getSelectedTower().getyPos();
            int radius = (int) towerManager.getSelectedTower().getType().getRange(); // deleted this later.
            int centerX = tileX*tileMap.getTileWidth();
            int centerY = tileY*tileMap.getTileHeight();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(
                centerX + (float) tileMap.getTileWidth() /2, centerY + (float) tileMap.getTileHeight() /2,
                radius
            );
            Gdx.gl.glLineWidth(5);
            shapeRenderer.end();
            Gdx.gl.glLineWidth(1); // resets line width.

            Texture towerTexture = towerManager.getSelectedTower().getType().getItemTexture();
            int towerXPos = Constants.Values.UIValues.SelectedTowerValues.TOWER_TEXTURE_X_POS;
            int towerYPos = Constants.Values.UIValues.SelectedTowerValues.TOWER_TEXTURE_Y_POS;
            int width = Constants.Values.UIValues.SelectedTowerValues.TOWER_TEXTURE_WIDTH;
            int height = Constants.Values.UIValues.SelectedTowerValues.TOWER_TEXTURE_HEIGHT;
            String towerRangeMessage = "Tower range: " + towerManager.getSelectedTower().getType().getRange();
            int towerRangeXPos = Constants.Values.UIValues.SelectedTowerValues.TOWER_RANGE_X_POS;
            int towerRangeYPos = Constants.Values.UIValues.SelectedTowerValues.TOWER_RANGE_Y_POS;
            String towerDamageMessage = "Tower damage: " + towerManager.getSelectedTower().getType().getDamage();
            int towerDamageXPos = Constants.Values.UIValues.SelectedTowerValues.TOWER_DAMAGE_X_POS;
            int towerDamageYPos = Constants.Values.UIValues.SelectedTowerValues.TOWER_DAMAGE_Y_POS;


            float originalScaleX = font.getData().scaleX;
            float originalScaleY = font.getData().scaleY;
            font.getData().setScale(1.5f);
            spriteBatch.begin();
            spriteBatch.draw(
                selectedTowerBackgroundTexture,
                Constants.Values.UIValues.SelectedTowerValues.SELECTED_TOWER_X_POS,
                Constants.Values.UIValues.SelectedTowerValues.SELECTED_TOWER_Y_POS,
                Constants.Values.UIValues.SelectedTowerValues.SELECTED_TOWER_UI_WIDTH,
                Constants.Values.UIValues.SelectedTowerValues.SELECTED_TOWER_UI_HEIGHT
                );
            spriteBatch.draw(towerTexture, towerXPos, towerYPos, width, height);
            font.draw(spriteBatch, towerRangeMessage, towerRangeXPos, towerRangeYPos);
            font.draw(spriteBatch, towerDamageMessage, towerDamageXPos, towerDamageYPos);
            spriteBatch.end();
            font.getData().setScale(originalScaleX, originalScaleY);
        }
    }

    private void renderHoldingItem() {
        if (player.getHoldingItem() != null) {
            float mouseXClick = Gdx.input.getX();
            float mouseYClick = Constants.SCREEN_HEIGHT - Gdx.input.getY();
            float width = Constants.Values.PlayerValues.HOLDING_ITEM_WIDTH;
            float height = Constants.Values.PlayerValues.HOLDING_ITEM_HEIGHT;
            spriteBatch.begin();
            spriteBatch.draw(
                player.getHoldingItem().getType().getItemTexture(),
                    mouseXClick - width/2, mouseYClick - height/2,
                    width, height
                );
            spriteBatch.end();
        }

    }

    private void checkPlaceTowerDown(){
        Vector3 screenCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        orthographicCamera.unproject(screenCoordinates);
        float worldX = screenCoordinates.x;
        float worldY = screenCoordinates.y;
        Tile tile = tileMap.getTileAtScreen((int) worldX, (int) worldY);
        int tileY = (int)worldX/ tileMap.getTileWidth();
        int tileX = (int)worldY/ tileMap.getTileHeight();

        if (tile == null || !tile.isBuildable()) {
            return;
        }
        if (!validateClick(Gdx.input.getX(), Gdx.input.getY())){
            return;
        }
        if (!towerManager.canBuyTower(currencyManager, player.getHoldingItem())) {
            player.setHoldingItem(null);
            return;
        }

        towerManager.placeTower(
            tileMap, player.getHoldingItemTileType(), player.getHoldingItem().getType(),
            tileX, tileY
        );
        towerManager.buyItem(currencyManager, player.getHoldingItem());

        if (!player.isHoldingDownControl()) {
            player.setHoldingItem(null);
        }
    }

    private boolean validateClick(float worldX, float worldY) {
        /*
        Guarantee that the click is inside the map (?).
         */
        for (ScrollableUI scrollableUI : scrollableUIList){
            if (scrollableUI.getBounds().contains(worldX, worldY)){
                return false;
            }
        }
        return true;
    }

    private void initializeButtons() {
        // Create a new button instance
        StaticGridButtonUI button = new StaticGridButtonUI(
            Constants.Values.UIValues.ButtonsValues.START_WAVE_BUTTON_X_POS,
            Constants.Values.UIValues.ButtonsValues.START_WAVE_BUTTON_Y_POS,
            Constants.Values.UIValues.ButtonsValues.START_WAVE_BUTTON_WIDTH,
            Constants.Values.UIValues.ButtonsValues.START_WAVE_BUTTON_HEIGHT,
            testButtonTexture,
            () -> {
                waveManager.startNextWave();
                GlobalUtils.consoleLog("Clicked button :D");
            },
            () -> {
            }
        );
        button.setOnHover(() -> {
            button.renderButtonInformationOnHover(spriteBatch, font, "Começar a wave");
        });
        staticButtonList.add(button);
    }

    private void renderShopUI(){
        for (ScrollableUI scrollableUI : scrollableUIList){
            scrollableUI.render(spriteBatch);
        }
    }

    private void initializeTowerShopUI() {
        int width = Constants.Values.UIValues.TOWER_SHOP_WIDTH;
        int height = Constants.Values.UIValues.TOWER_SHOP_HEIGHT;
        int startX = Constants.SCREEN_WIDTH - width;
        int startY = Constants.SCREEN_HEIGHT - height;

        List<StaticGridButtonUI> buttons = getTowerShopButtonList();

        scrollableUIList.add(new ScrollableUI(
            startX, startY, startX + width, Constants.SCREEN_HEIGHT,
            Constants.Values.UIValues.TOWER_SHOP_GRIDX, Constants.Values.UIValues.TOWER_SHOP_GRIDY,
            Constants.Values.UIValues.TOWER_SHOP_ITEM_LENGTH,
            towerShopBackgroundTexture, towerShopfrontTexture, buttons
        ));
    }

    private List<StaticGridButtonUI> getTowerShopButtonList() {
        List<StaticGridButtonUI> buttons = new ArrayList<>();

        StaticGridButtonUI buyArcher = getBuyArcherButton();
        buttons.add(buyArcher);

        for (int i = 0; i < 5 ;i++){
            int n = i;

            StaticGridButtonUI button = new StaticGridButtonUI(testButtonTexture);
            button.setOnClick(() -> {
                GlobalUtils.consoleLog("Clicked on button " + (n + 1));
            });
            button.setOnHover(() -> {
                button.renderButtonInformationOnHover(spriteBatch, font, "Não implementado ainda");
            });

            buttons.add(button);
        }
        return buttons;
    }

    private StaticGridButtonUI getBuyArcherButton() {
        StaticGridButtonUI buyArcher = new StaticGridButtonUI(TowerType.ARCHER.getItemTexture());
        buyArcher.setOnClick(() -> {
            if(towerManager.canBuyTower(currencyManager, new TowerItem(TowerType.ARCHER))){
                player.setHoldingItem(new TowerItem(TowerType.ARCHER));
                player.setTileType(TileType.ARCHER_TOWER);
            }
        });
        buyArcher.setOnHover(() -> {
            buyArcher.renderButtonInformationOnHover(spriteBatch, font, "Comprar torre");
        });
        return buyArcher;
    }

    private void checkStaticButtonClick() {
        float mouseXClick = Gdx.input.getX();
        float mouseYClick = Constants.SCREEN_HEIGHT - Gdx.input.getY();

        for (ScrollableUI scrollableUI : scrollableUIList){
            for (StaticGridButtonUI staticGridButtonUI : scrollableUI.getButtons()){
                staticGridButtonUI.checkClick(mouseXClick, mouseYClick);
            }
        }

        for (StaticGridButtonUI staticButton : staticButtonList){
            staticButton.checkClick(mouseXClick, mouseYClick);
        }
    }

    private void renderStaticButtons() {
        float mouseXHover = Gdx.input.getX();
        float mouseYHover = Constants.SCREEN_HEIGHT - Gdx.input.getY();

        for (ScrollableUI scrollableUI : scrollableUIList){
            for (StaticGridButtonUI staticGridButtonUI : scrollableUI.getButtons()){
                staticGridButtonUI.checkHover(mouseXHover, mouseYHover);
            }
        }

        for (StaticGridButtonUI staticButton : staticButtonList){
            staticButton.checkHover(mouseXHover, mouseYHover);
            staticButton.render(spriteBatch);
        }
    }

    private void updateTowers(float delta) {
        for (PlacedTower tower : towerManager.getTowers()){
            tower.update(delta, waveManager);
        }
    }

    private void updatePlayer(float delta) {
        handlePlayerClick();
        player.handlePlayerMovement(delta, tileMap);
    }

    private void handlePlayerClick() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            checkStaticButtonClick();

            if (player.getHoldingItem() != null) {
                checkPlaceTowerDown();
            } else {
                checkSelectTower();
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
        player.setHoldingItem(null);
        towerManager.setSelectedTower(null);
        }
    }

    private void checkSelectTower() {
        Vector3 screenCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        orthographicCamera.unproject(screenCoordinates);
        float worldX = screenCoordinates.x;
        float worldY = screenCoordinates.y;

        Tile tile = tileMap.getTileAtScreen((int) worldX, (int) worldY);

        int tileY = (int)worldY/ tileMap.getTileWidth();
        int tileX = (int)worldX/ tileMap.getTileHeight();

        if (tile == null) return;

        if (tile.getType().isTurret()){
            towerManager.setSelectedTower(new PlacedTower(getTowerTypeFromTileType(tile.getType()),
                tileX, tileY
            ));
        } else {
            towerManager.setSelectedTower(null);
        }
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

    private void renderCurrencyUI() {
        int width = Constants.Values.UIValues.CURRENCY_X_POS_DECREASE;
        int startX = Constants.SCREEN_WIDTH - width - Constants.Values.UIValues.TOWER_SHOP_WIDTH;
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
        P -> pause.
         */
        if (debug) {
            float startX = 10;
            float startY = 500;
            String message =
                "M  -> start wave \n" +
                "F6 -> toggle debug \n" +
                "F7 -> main menu \n" +
                "F8 -> deal 1 dmg to mobs \n" +
                "P  -> pause";
            spriteBatch.begin();
            spriteBatch.setColor(Color.WHITE);
            font.draw(spriteBatch, message, startX, startY);
            spriteBatch.end();
        }
    }

    private void renderCurrentWaveUI() {
        int width = Constants.Values.UIValues.CURRENT_WAVE_WIDTH;
        int startX = Constants.SCREEN_WIDTH - width - Constants.Values.UIValues.TOWER_SHOP_WIDTH;
        int startY = Constants.SCREEN_HEIGHT - Constants.Values.UIValues.CURRENT_WAVE_HEIGHT_DECREASE;
        float waveClock = waveManager.getCurrentWave().getWaveClock();
        int minutes = (int)(waveClock/60);
        int seconds = (int)(waveClock - minutes*60);

        String message = "Wave: " + (waveManager.getCurrentWaveIndex() + 1) + "/" + Constants.WaveValues.LEVEL_1_MAX_WAVE;

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
        if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT){
            player.setHoldingDownControl(true);
        }
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            player.setHoldingDownShift(true);
        }
        if (keycode == Input.Keys.P){
            isPaused = !isPaused;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) { // keycode == what was just released.
        if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT){
            player.setHoldingDownControl(false);
        }
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            player.setHoldingDownShift(false);
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
