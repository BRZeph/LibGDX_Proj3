package me.BRZeph.Screens.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import me.BRZeph.AI.GameData.Data.TowerData;
import me.BRZeph.AI.GameData.Data.WaveData;
import me.BRZeph.AI.GameData.Utils.GameDataExporter;
import me.BRZeph.AI.GameData.GameEndHandler;
import me.BRZeph.TowerDefenseGame;
import me.BRZeph.core.Assets.AdvancedAssetsManager;
import me.BRZeph.core.Assets.BasicAssetsManager;
import me.BRZeph.core.Managers.CurrencyManager;
import me.BRZeph.core.Managers.ScreenManager;
import me.BRZeph.core.Managers.TowerManager;
import me.BRZeph.core.Managers.WaveManager;
import me.BRZeph.core.UI.ScrollableUI;
import me.BRZeph.core.UI.StaticGridButtonUI;
import me.BRZeph.entities.Map.Tile;
import me.BRZeph.entities.Map.TileMap;
import me.BRZeph.entities.Map.TileType;
import me.BRZeph.entities.Player;
import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.Towers.TowerItem;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.WaveSystem.SpawnBehavior;
import me.BRZeph.entities.WaveSystem.SpawnRuleEndWave;
import me.BRZeph.entities.WaveSystem.Wave;
import me.BRZeph.entities.monster.Monster;
import me.BRZeph.entities.Map.Node;
import me.BRZeph.entities.monster.MonsterType;

import java.io.IOException;
import java.util.*;

import static me.BRZeph.core.Managers.TowerManager.canBuyTower;
import static me.BRZeph.utils.Constants.CameraValues.*;
import static me.BRZeph.utils.Constants.LocalPaths.GAME_DATA_EXPORT_NAME;
import static me.BRZeph.utils.Constants.Paths.MapsPath.LEVEL_1_MAP;
import static me.BRZeph.utils.Constants.Paths.ScreensTexturesPath.LEVEL_1_BACKGROUND;
import static me.BRZeph.utils.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Paths.TileValues.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Paths.UIPath.*;
import static me.BRZeph.utils.Constants.Paths.Values.PlayerValues.*;
import static me.BRZeph.utils.Constants.Paths.Values.UIValues.ButtonsValues.*;
import static me.BRZeph.utils.Constants.Paths.Values.UIValues.*;
import static me.BRZeph.utils.Constants.Paths.Values.UIValues.SelectedTowerValues.*;
import static me.BRZeph.utils.Constants.Paths.WaveValues.getLevel1Waves;
import static me.BRZeph.utils.Constants.SCREEN_HEIGHT;
import static me.BRZeph.utils.Constants.SCREEN_WIDTH;
import static me.BRZeph.utils.GlobalUtils.consoleLog;

public class Level1Screen extends BaseLevelScreen implements InputProcessor {
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera orthographicCamera;
    private BitmapFont font;

    private Texture heartTexture;
    private Texture testButtonTexture;
    private Texture towerShopBackgroundTexture;
    private Texture towerShopfrontTexture;
    private Texture selectedTowerBackgroundTexture;

    private List<StaticGridButtonUI> staticButtonList;
    private List<ScrollableUI> scrollableUIList;
    private StaticGridButtonUI towerLeftAggroButton;
    private StaticGridButtonUI towerRightAggroButton;
    private TileMap tileMap;
    private Player player;
    private WaveManager waveManager;
    private TowerManager towerManager;
    private CurrencyManager currencyManager;

    private boolean isPaused;
    private boolean debug;
    private boolean AI;
    private float playerMaxHealth;
    private float playerCurrentHealth;

    public Level1Screen(ScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        font = new BitmapFont();
        staticButtonList = new ArrayList<>();
        scrollableUIList = new ArrayList<>();
        isPaused = false;
        this.AI = false;

        loadAssets();
        initializeButtons();
        initializeTowerShopUI();

        debug = false;
        player = new Player(0, 0, 50);
        tileMap = new TileMap(LEVEL_1_MAP);
        tileMap.loadMapAndFindPath(LEVEL_1_MAP);
        waveManager = new WaveManager(getLevel1Waves(tileMap.getPath()));
        towerManager = new TowerManager();
        currencyManager = new CurrencyManager();
        currencyManager.addGold(STARTING_GOLD);
        currencyManager.addEssence(STARTING_ESSENCE);
        currencyManager.addMomentum(STARTING_MOMENTUM);

        playerMaxHealth = PLAYER_HEALTH;
        playerCurrentHealth = playerMaxHealth;
    }

    /*
    PRIORITY:
    1- (DONE): make currency system.
    1.1- (DONE): finish implementing the buy system (line 200 of this class).
    2- (DONE): make background UI for the "TOWER_MENU_UI" and implement it with a scrollable bar.
    2.1- (DONE): give up on the idea of scrollable ui, create an ui will different pages.
    3- finish towers system.
    3.1- implement new towers and tower divisions (neutral, military, magic etc).
    3.2- implement leveling tree.
    3.3- finish implementing new towers before handling the hero room thing.
    4- finish implementing monster system.
    4.1- finish all the 10 existing monsters specific mechanics.
    4.2- consider making the monsters stronger as the time passes (or variations of the same monster wearing armor etc).
    5- hero room.
    6- After this, it will be just more of the same.

    7- WHEN IMPLEMENTING UI USE THE STAGE CLASS.

    TO FIX:
    1- fix issue that is caused by a map not having a path.
    2- (DONE): fix issue of monster not following the path. Maybe the path is being defined wrongly due to the map load with the Y axis inverted?
    3- fix GlobalUtils.adjustTextWidth().
    4- (DONE): fix the Map of behaviors not working with more than 1 behavior (see line 151 of constants.java).
    5- (DONE): fix inverted Y position on TileMap.Render().
    6- (DONE): fix the targeting system resetting once the selectedTower is set to null.
    7- (DONE): fix map inversion.
    8- make upgraded towers different tiles? maybe (see TileType class).
    9- (DONE): fix issue with hero room tiles breaking the game when set to fire.
    10- fix issue with changing state of a rotated tile resetting the rotation of the tile. (is this even happening? idk).
    11- fix issue of spawnRule only starting when the highest startTime is triggered.

    TO IMPROVE:
    1- (DONE): make the path finding algorithm more optimized, try version 3 of level 1 map (third map in the level_2_map.txt).
    2- (DONE): make a better currency system (Values.CurrencyTypes). Consider making individual classes for each.
    3- (DONE): make PlacedTower class abstract and every tower such as ARCHER_TOWER an implementation of PlacedTower.
    4- (DONE): improve wave system so that the wave can end at some point, consider adding a "finalBehavior" with a duration.
    5- (DONE): remove "spawnRules" parameter of wave.class and use only behaviors/endBehavior.

    AI UPGRADES:
    1- Implement a getBetterTile method inside level1Environment.getPossibleActions() so that the AI prioritize the best tiles for each tower.
    2- (DONE): Fix tileMap problem when placing a tower.
    3- Implement TIME_MULTIPLIER constant.
    4- (DONE): Fix environment.reset().
    5- (DONE): Implement waveDataList in method performLevelEnd.
    6- Consider using getBestTile() from Level1Environment.getPossibleActions().
    7- (DONE): Implement an export from xml to excel.
    7.1- (DONE): Modify TowerData to also have the parameters of damageDealt and killCount.
    7.2- (DONE): Export to excel so that I can create an average value for towerType/Position/damage and then decide the best position for the tower.
    8- Merge QTables after the training is done so that the data from the different AI's will interact with each other.
    9- (DONE): Currently the AI action is taking immediate reward which should not be the case.
    10- (DONE): Make the excel export dynamic to the fields of the xml, it should not be static.
    11- Upgrade wave data and all actions data (early wave start).

create 3 worksheets, 1 for the full data of the AI, another for the wave stats (rewards, how far it got to, etc) and another with the specifics stats of
the wave (what im currently doing).

same logic as EKPO/EKKO but with 3 WS.

WS 1 -> see general values and stats of the AI full cycle (episodes -> max_expisodes).
WS 2 -> see general values and stats of each wave (add a key column for the AI count here).
WS 3 -> see specific values of each performed action (what im currently doing).

create a key column that will identify which AI it's for all 3 worksheets.
create a key column that will identify what wave it's for WS 2 and WS 3.

put average fitness inside WS 2 and WS 1.

put final AI reward inside WS 1.





    TO IMPLEMENT:
    1- implement basic game mechanics (towers, monsters, path and basic shop).
    2- (DONE): implement player (green square for now) with movement.
    3- (DONE): implement button for starting the wave with hotkey.
    4- (DONE): implement clock during the wave.
    5- (DONE): implement wave count ("Wave 4/30").
    6- (DONE): implement a better system for defining what will spawn when in each wave of each level.
    7- (DONE): implement "coin system", whenever the monsters die, the player should receive "coin" currency.
    8- (DONE): implement monster death generating coins and so on in updateWave().
    9- (DONE): implement PlacedTower.update().
    10- (DONE): implement endOfLevel() which happens when the last wave ends or the player dies or the player quits.
    11- (DONE): implement "incomingDamage" mechanic to monster class.
    11.1- (DONE): when a tower shoots a monster, the incomingDamage value increases.
    11.2- (DONE): when a tower is deciding who to shoot, it should skip monsters with incomingDamage > currentHealth.
    12- (DONE): **** PRIORITY **** PRIORITY **** PRIORITY **** PRIORITY **** implement new towers.
    12.1- (DONE): implement CannonTower (aoe damage).
    12.2- (DONE): implement LightningTower (chain damage).
    12.2.1- (DONE): finish update projectile method of lightningTower.
    13- implement new enemies.
    13.1- implement monsters abilities.
    13.2- maybe(?) increase monsters stats as the waves progresses (+10% hp every wave etc).
    14- (DONE): implement closesMonsterStrategy that will aggro the closest monster to the tower.
    15- implement tab click switching the current tower aggro to the next aggro if the selected tower != null.
    16- finish implementing AI.
    17- (DONE): implement/fix towerAttackCooldownUI;
    18- implement sell tower button.
    18.1- Implement hot key for tower buying.
    18.2- Implement UI for tower price.
    19- hero room.
    19.1- tileMap should automatically detect and rotate the corners.
    19.2- define what constitutes the hero.
    19.3- implement hero constructor class.
    19.4- create 3 heroes (warrior, archer and mage archetypes).
    19.5- create system to automatically pull the enemies.
    19.6- implement enemies having stats for combat with the hero.
    19.7- implement enemies AI.
    20- tower upgrade tree.

    TO REFACTOR:
    1- refactor A LOT of code from this class into the correct managers.
    2- extract renderAttackCooldown to a static method in GlobalUtils (maybe?).

    TO IMPLEMENT (TEXTURES):
    1- Texture for left aggro button, right aggro button, better tiles, better ui in general.
    2- Blood splatter when the archer tower his a monster.
    3- explosion animation for cannon hit.
    4- lightning animation for lightning tower.
     */

    @Override
    protected void loadAssets() {
        BasicAssetsManager.loadCommonAssets(assetManager);
        BasicAssetsManager.loadSpecificAssets(1, assetManager);
        AdvancedAssetsManager.loadAdvancedAssets(assetManager);

        heartTexture = assetManager.get(HEART_UI);
        testButtonTexture = assetManager.get(TEST_BUTTON_TEXTURE_PATH, Texture.class);
        towerShopBackgroundTexture = assetManager.get(TOWER_SHOP_BACKGROUND_UI);
        towerShopfrontTexture = assetManager.get(TOWER_SHOP_FRONT_UI);
        selectedTowerBackgroundTexture = assetManager.get(SELECTED_TOWER_BACKGROUND_UI);
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

        if (!isPaused) {
            updatePlayer(delta);
            updateCamera();
            updateTowers(delta);
            updateWave(delta);
        }

        renderTileMap(); //this also render the towers, there is no need to call "tower.render()" or whatever.
        renderWave(delta);
        renderTowerProjectiles();
        renderTowerAttackCooldown();
        renderPath();
        renderUI();
        renderPlayer(delta);
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

    private void renderTowerAttackCooldown(){
        for (Tower tower : towerManager.getTowers()){
            tower.renderAttackCooldown(shapeRenderer);
        }
    }

    private void renderTowerProjectiles() {
        for (Tower tower : towerManager.getTowers()) {
            tower.renderProjectiles(spriteBatch);
        }
    }

    private void renderSelectedTowerUI() {
        if (towerManager.getSelectedTower() != null){
            towerLeftAggroButton.setDisable(false);
            towerRightAggroButton.setDisable(false);
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
            Gdx.gl.glLineWidth(1);

            Texture towerTexture = towerManager.getSelectedTower().getType().getItemTexture();
            String towerRangeMessage = "Tower range: " + towerManager.getSelectedTower().getType().getRange();
            String towerDamageMessage = "Tower damage: " + towerManager.getSelectedTower().getType().getDamage();
            String towerAggroTypeMessage = "Tower aggro: \n" + towerManager.getSelectedTower().getTargetingStrategy();

            float originalScaleX = font.getData().scaleX;
            float originalScaleY = font.getData().scaleY;
            font.getData().setScale(1.5f);
            spriteBatch.begin();
            spriteBatch.draw(
                selectedTowerBackgroundTexture,
                SELECTED_TOWER_X_POS,
                SELECTED_TOWER_Y_POS,
                SELECTED_TOWER_UI_WIDTH,
                SELECTED_TOWER_UI_HEIGHT
                );
            spriteBatch.draw(towerTexture, TOWER_TEXTURE_X_POS, TOWER_TEXTURE_Y_POS, TOWER_TEXTURE_WIDTH, TOWER_TEXTURE_HEIGHT);
            font.draw(spriteBatch, towerRangeMessage, TOWER_RANGE_X_POS, TOWER_RANGE_Y_POS);
            font.draw(spriteBatch, towerDamageMessage, TOWER_DAMAGE_X_POS, TOWER_DAMAGE_Y_POS);
            font.draw(spriteBatch, towerAggroTypeMessage, TOWER_AGGRO_TEXT_X_POS, TOWER_AGGRO_TEXT_Y_POS);
            spriteBatch.end();
            font.getData().setScale(originalScaleX, originalScaleY);
        } else {
            towerLeftAggroButton.setDisable(true);
            towerRightAggroButton.setDisable(true);
        }
    }

    private void renderHoldingItem() {
        if (player.getHoldingItem() != null) {
            float mouseXClick = Gdx.input.getX();
            float mouseYClick = SCREEN_HEIGHT - Gdx.input.getY();
            float width = HOLDING_ITEM_WIDTH;
            float height = HOLDING_ITEM_HEIGHT;
            spriteBatch.begin();
            spriteBatch.draw(
                player.getHoldingItem().getType().getItemTexture(),
                    mouseXClick - width/2, mouseYClick - height/2,
                    width, height
                );
            spriteBatch.end();
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
        StaticGridButtonUI startWaveButton = new StaticGridButtonUI(
            START_WAVE_BUTTON_X_POS, START_WAVE_BUTTON_Y_POS,
            START_WAVE_BUTTON_WIDTH, START_WAVE_BUTTON_HEIGHT,
            testButtonTexture,
            () -> {
                waveManager.startNextWave();
            },
            () -> {
            }
        );
        startWaveButton.setOnHover(() -> {
            startWaveButton.renderButtonInformationOnHover(spriteBatch, font, "Começar a wave");
        });

         towerLeftAggroButton = new StaticGridButtonUI(
            TOWER_AGGRO_LEFT_BUTTON_X_POS, TOWER_AGGRO_LEFT_BUTTON_Y_POS,
            TOWER_AGGRO_LEFT_BUTTON_WIDTH, TOWER_AGGRO_LEFT_BUTTON_HEIGHT,
            testButtonTexture,
            () -> {
                towerManager.getSelectedTower().setPreviousTargetingStrategy();
            },
            () -> {
            }
        );
        towerLeftAggroButton.setOnHover(() -> {
            towerLeftAggroButton.renderButtonInformationOnHover(spriteBatch, font, "Select previous aggro");
        });

        towerRightAggroButton = new StaticGridButtonUI(
            TOWER_AGGRO_RIGHT_BUTTON_X_POS, TOWER_AGGRO_RIGHT_BUTTON_Y_POS,
            TOWER_AGGRO_RIGHT_BUTTON_WIDTH, TOWER_AGGRO_RIGHT_BUTTON_HEIGHT,
            testButtonTexture,
            () -> {
                towerManager.getSelectedTower().setNextTargetingStrategy();
            },
            () -> {
            }
        );
        towerRightAggroButton.setOnHover(() -> {
            towerRightAggroButton.renderButtonInformationOnHover(spriteBatch, font, "Select Next aggro");
        });

        staticButtonList.add(startWaveButton);
        staticButtonList.add(towerLeftAggroButton);
        staticButtonList.add(towerRightAggroButton);
    }

    private void renderShopUI(){
        for (ScrollableUI scrollableUI : scrollableUIList){
            scrollableUI.render(spriteBatch);
        }
    }

    private void initializeTowerShopUI() {
        int width = TOWER_SHOP_WIDTH;
        int startX = SCREEN_WIDTH - width;
        int startY = SCREEN_HEIGHT - TOWER_SHOP_HEIGHT;

        List<StaticGridButtonUI> buttons = getTowerShopButtonList();

        scrollableUIList.add(new ScrollableUI(
            startX, startY, startX + width, SCREEN_HEIGHT,
            TOWER_SHOP_GRIDX, TOWER_SHOP_GRIDY,
            TOWER_SHOP_ITEM_LENGTH,
            towerShopBackgroundTexture, towerShopfrontTexture, buttons
        ));
    }

    private List<StaticGridButtonUI> getTowerShopButtonList() {
        List<StaticGridButtonUI> buttons = new ArrayList<>();

        StaticGridButtonUI buyArcher = createBuyArcherButton();
        buttons.add(buyArcher);
        StaticGridButtonUI buyCannon = createBuyCannonButton();
        buttons.add(buyCannon);
        StaticGridButtonUI buyLightning = createBuyLightningButton();
        buttons.add(buyLightning);

        for (int i = 0; i < 5 ;i++){
            int n = i;

            StaticGridButtonUI button = new StaticGridButtonUI(testButtonTexture);
            button.setOnClick(() -> {
                consoleLog("Clicked on button " + (n + 1));
            });
            button.setOnHover(() -> {
                button.renderButtonInformationOnHover(spriteBatch, font, "Não implementado ainda");
            });

            buttons.add(button);
        }
        return buttons;
    }

    private StaticGridButtonUI createBuyLightningButton() {
        StaticGridButtonUI buyLightning = new StaticGridButtonUI(TowerType.LIGHTNING.getItemTexture());
        buyLightning.setOnClick(() -> {
            if(canBuyTower(currencyManager, new TowerItem(TowerType.LIGHTNING))){
                player.setHoldingItem(new TowerItem(TowerType.LIGHTNING));
                player.setTileType(TileType.LIGHTNING_TOWER);
            }
        });
        buyLightning.setOnHover(() -> {
            buyLightning.renderButtonInformationOnHover(spriteBatch, font, "Comprar torre");
        });
        return buyLightning;
    }

    private StaticGridButtonUI createBuyCannonButton() {
        StaticGridButtonUI buyCannon = new StaticGridButtonUI(TowerType.CANNON.getItemTexture());
        buyCannon.setOnClick(() -> {
            if(canBuyTower(currencyManager, new TowerItem(TowerType.CANNON))){
                player.setHoldingItem(new TowerItem(TowerType.CANNON));
                player.setTileType(TileType.CANNON_TOWER);
            }
        });
        buyCannon.setOnHover(() -> {
            buyCannon.renderButtonInformationOnHover(spriteBatch, font, "Comprar torre");
        });
        return buyCannon;
    }

    private StaticGridButtonUI createBuyArcherButton() {
        StaticGridButtonUI buyArcher = new StaticGridButtonUI(TowerType.ARCHER.getItemTexture());
        buyArcher.setOnClick(() -> {
            if(canBuyTower(currencyManager, new TowerItem(TowerType.ARCHER))){
                player.setHoldingItem(new TowerItem(TowerType.ARCHER));
                player.setTileType(TileType.ARCHER_TOWER);
            }
        });
        buyArcher.setOnHover(() -> {
            buyArcher.renderButtonInformationOnHover(spriteBatch, font, "Comprar torre");
        });
        return buyArcher;
    }

    private boolean checkStaticButtonClick() {
        float mouseXClick = Gdx.input.getX();
        float mouseYClick = SCREEN_HEIGHT - Gdx.input.getY();

        for (ScrollableUI scrollableUI : scrollableUIList){ // Currently only the tower shop is here.
            for (StaticGridButtonUI staticGridButtonUI : scrollableUI.getButtons()){
                if(staticGridButtonUI.checkClick(mouseXClick, mouseYClick)){
                    return true;
                }
            }
        }

        for (StaticGridButtonUI staticButton : staticButtonList){ // Tower aggro is here.
            if (staticButton.checkClick(mouseXClick, mouseYClick)){
                return true;
            }
        }

        return false;
    }

    private void renderStaticButtons() {
        float mouseXHover = Gdx.input.getX();
        float mouseYHover = SCREEN_HEIGHT - Gdx.input.getY();

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
        for (Tower tower : towerManager.getTowers()){
            tower.update(delta, waveManager);
        }
    }

    private void updatePlayer(float delta) {
        handlePlayerClick();
        player.handlePlayerMovement(delta, tileMap);
    }

    private void handlePlayerClick() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            if(checkStaticButtonClick()){
                return;
            }

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

    private void checkPlaceTowerDown(){
        Vector3 screenCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        orthographicCamera.unproject(screenCoordinates);
        float worldX = screenCoordinates.x;
        float worldY = screenCoordinates.y;
        Tile tile = tileMap.getTileAtScreen((int) worldX, (int) worldY);
        int tileX = (int)worldX/ tileMap.getTileWidth();
        int tileY = (int)worldY/ tileMap.getTileHeight();

        if (tile == null || !tile.isBuildable()) {
            return;
        }
        if (!validateClick(Gdx.input.getX(), Gdx.input.getY())){
            player.setHoldingItem(null);
            return;
        }
        if (!canBuyTower(currencyManager, player.getHoldingItem())) {
            player.setHoldingItem(null);
            return;
        }

        towerManager.placeTower(
            tileMap, player.getHoldingItemTileType(), player.getHoldingItem().getType(),
            tileX, tileY, waveManager.getCurrentWaveIndex()
        );
        towerManager.buyItem(currencyManager, player.getHoldingItem());

        if (!player.isHoldingDownControl()) {
            player.setHoldingItem(null);
        }
    }

    private void checkSelectTower() {
        Vector3 screenCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        orthographicCamera.unproject(screenCoordinates);
        float worldX = screenCoordinates.x;
        float worldY = screenCoordinates.y;
        Tile tile = tileMap.getTileAtScreen((int) worldX, (int) worldY);
        int tileX = (int)worldX/ tileMap.getTileWidth();
        int tileY = (int)worldY/ tileMap.getTileHeight();

        if(tile == null) return;

        if(tile.getType().isTurret()){
            for (Tower tower : towerManager.getTowers()){
                if(tower.getyPos() == tileY &&
                    tower.getxPos() == tileX){
                    towerManager.setSelectedTower(tower);
                }
            }
        } else {
            towerManager.setSelectedTower(null);

            if (debug) {
                if (Objects.equals(tile.getType().getState(), "idle")) { // REMOVE LATER
                    tile.getType().updateTileStateTexture("fire"); // REMOVE LATER
                } else if (Objects.equals(tile.getType().getState(), "fire")) { // REMOVE LATER
                    tile.getType().updateTileStateTexture("idle"); // REMOVE LATER
                }
                if (Objects.equals(tile.getType().getFacingDirection(), "up")){
                    tile.getType().setFacingDirection("right");
                } else if (Objects.equals(tile.getType().getFacingDirection(), "right")){
                    tile.getType().setFacingDirection("down");
                } else if (Objects.equals(tile.getType().getFacingDirection(), "down")){
                    tile.getType().setFacingDirection("left");
                } else if (Objects.equals(tile.getType().getFacingDirection(), "left")){
                    tile.getType().setFacingDirection("up");
                }
            }

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

    private void updateWave(float delta) { // this fucking method should be in WaveManager class.
        waveManager.update(delta);

        List<Monster> monstersAlive = waveManager.getCurrentWave().getMonsterList();

        List<Monster> reachedEnd = waveManager.getCurrentWave().getMonsterReachedEnd();
        for (Monster monster : reachedEnd) {
            playerCurrentHealth -= monster.getType().getNexusDmg();
            monstersAlive.remove(monster);
        }
        reachedEnd.clear();
        List<Monster> died = waveManager.getCurrentWave().getMonsterDied();
        for (Monster monster : died) {
            currencyManager.addMonsterLoot(monster.getType());
            monstersAlive.remove(monster);
            // IMPLEMENT MONSTER DEATH HERE.
        }
        died.clear();
        if (waveManager.isFinishedLevel() || isPlayerDead()){
            performLevelEnd();
        }
    }

    private void performLevelEnd() {
        GameEndHandler gameEndHandler = new GameEndHandler(towerManager.getTowers(), waveManager.getWaves());
        List<TowerData> towerDataList = gameEndHandler.collectTowerData();
        List<WaveData> waveDataList = gameEndHandler.collectWaveData();

        GameDataExporter exporter = new GameDataExporter();
        exporter.exportGameData(towerDataList, waveDataList, GAME_DATA_EXPORT_NAME);

        if (!AI){ // Prevent creating a new file every frame.
            consoleLog("NOT AI ***************************************************************");
            TowerDefenseGame.getScreenManager().showMainMenu();
        }
    }

    private void renderTileMap(){
        tileMap.render(spriteBatch);
    }

    private void renderWave(float delta) {
        waveManager.render(spriteBatch, font, shapeRenderer, delta);
    }

    private void renderPath() {
        if(debug){
            List<Node> path = tileMap.getPath();

            if(path != null) {

                for (int i = 0; i < path.size() - 1; i++) {
                    Node current = path.get(i);
                    Node next = path.get(i + 1);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.WHITE);

                    // Draw a line from the current node to the next node
                    shapeRenderer.line(
                        (float) ((0.5 + current.x) * TILE_WIDTH),
                        (float) ((0.5 + current.y) * TILE_HEIGHT),
                        (float) ((0.5 + next.x) * TILE_WIDTH),
                        (float) ((0.5 + next.y) * TILE_HEIGHT)
                    );
                    shapeRenderer.end();

                    // Draw nodes count.
                    String message = "Node: " + i;
                    float messageYPos; // This makes it easier to read.
                    if(i%2 == 0){
                        messageYPos = (float)(0.5 + current.y) * TILE_HEIGHT;
                    } else {
                        messageYPos = (float)(0.5 + current.y) * TILE_HEIGHT + 18;
                    }
                    spriteBatch.begin();
                    font.draw(spriteBatch, message, (float)(0.5 + current.x) * TILE_WIDTH, messageYPos);
                    spriteBatch.end();
                }
            } else {
                consoleLog("path null");
            }
        }
    }

    private void renderCurrencyUI() {
        int startX = SCREEN_WIDTH - CURRENCY_X_POS_DECREASE - TOWER_SHOP_WIDTH;
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
        if(debug) {
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
        int startX = SCREEN_WIDTH - CURRENT_WAVE_WIDTH - TOWER_SHOP_WIDTH;
        int startY = SCREEN_HEIGHT - CURRENT_WAVE_HEIGHT_DECREASE;
        float waveClock = waveManager.getCurrentWave().getWaveClock();
        int minutes = (int)(waveClock/60);
        int seconds = (int)(waveClock - minutes*60);

        String message = "Wave: " + (waveManager.getCurrentWaveIndex() + 1) + "/" + waveManager.getWaves().size();

        float originalScaleX = font.getData().scaleX;
        float originalScaleY = font.getData().scaleY;
        font.getData().setScale(2.5f);

        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);
        font.draw(spriteBatch, message, startX, startY);
        message = "Timer: " + minutes + ":" + seconds;
        font.draw(spriteBatch, message, startX, startY - CURRENT_WAVE_CLOCK_HEIGHT_DECREASE);
        spriteBatch.end();

        font.getData().setScale(originalScaleX, originalScaleY);
    }

    private void renderFPSUI() {
        float startY = SCREEN_HEIGHT - FPS_Y_POS_DECREASE;
        float desiredWidth = FPS_WIDTH;
        float fps = Gdx.graphics.getFramesPerSecond();
        String message = "FPS: " + (int) fps;

//        String adjustedMessage = adjustTextWidth(message, desiredWidth, font, glyphLayout);

        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);
        font.draw(spriteBatch, message, (float) FPS_X_POS, startY);
        spriteBatch.end();
    }

    private void renderPlayerHealthUI() {
        float startX = HEALTH_X_POS;
        float startY = SCREEN_HEIGHT - HEALTH_Y_POS_DECREASE;
        float desiredWidth = HEALTH_WIDTH;

        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);
        spriteBatch.draw(
            heartTexture,
            startX,
            startY,
            HEART_WIDTH,
            HEART_HEIGHT
        );
        spriteBatch.draw(
            heartTexture,
            startX + HEART_WIDTH + 2*HEALTH_HEART_GAP +
                145,
            startY,
            HEART_WIDTH,
            HEART_HEIGHT
        );
        spriteBatch.end();

        String message = playerCurrentHealth + " / " + playerMaxHealth;
//        String adjustedMessage = adjustTextWidth(message, desiredWidth, font, glyphLayout);

        float originalScaleX = font.getData().scaleX;
        float originalScaleY = font.getData().scaleY;
        spriteBatch.begin();
        font.getData().setScale(HEALTH_FONT_SIZE);
        font.draw(
            spriteBatch,
            message,
            startX + HEART_WIDTH + HEALTH_HEART_GAP,
            startY + (float) HEART_HEIGHT /2 + font.getData().xHeight/2
        );
        spriteBatch.end();
        font.getData().setScale(originalScaleX, originalScaleY);
    }

    private void renderPlayer(float delta) {
        player.render(spriteBatch, delta);
    }

    @Override
    public boolean isPlayerDead() {
        return getPlayerCurrentHealth() <= 0;
    }

    public float getPlayerCurrentHealth() {
        return playerCurrentHealth;
    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        consoleLog("Calling dispose method of Level1Screen");
        assetManager.unload(LEVEL_1_BACKGROUND);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        float zoomAmount = amountY * CAMERA_ZOOM_SPEED;
        orthographicCamera.zoom += zoomAmount;

        orthographicCamera.zoom = Math.max(
            CAMERA_MIN_ZOOM,
            Math.min(CAMERA_MAX_ZOOM, orthographicCamera.zoom)
        );
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.M){
            waveManager.startNextWave();
        }
        if(keycode == Input.Keys.F6) {
            debug = !debug;
        }
        if(keycode == Input.Keys.F7) {
            TowerDefenseGame.getScreenManager().showMainMenu();
        }
        if(keycode == Input.Keys.F8){
            for (Monster monster : waveManager.getCurrentWave().getMonsterList()){
                monster.setCurrentHealth(monster.getCurrentHealth() - 1);
            }
        }
        if(keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT){
            player.setHoldingDownControl(true);
        }
        if(keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            player.setHoldingDownShift(true);
        }
        if(keycode == Input.Keys.P){
            isPaused = !isPaused;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) { // keycode == what was just released.
        if(keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT){
            player.setHoldingDownControl(false);
        }
        if(keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
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

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public CurrencyManager getCurrencyManager() {
        return currencyManager;
    }

    public float getPlayerMaxHealth() {
        return playerMaxHealth;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    @Override
    public OrthographicCamera getCamera() {
        return orthographicCamera;
    }

    public boolean isAI() {
        return AI;
    }

    public void setAI(boolean AI) {
        this.AI = AI;
    }
}
