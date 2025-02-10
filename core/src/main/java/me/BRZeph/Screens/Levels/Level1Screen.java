package me.BRZeph.Screens.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import me.BRZeph.AI.GameData.Data.TowerData;
import me.BRZeph.AI.GameData.Data.WaveData;
import me.BRZeph.AI.GameData.Utils.GameDataExporter;
import me.BRZeph.AI.GameData.GameEndHandler;
import me.BRZeph.TowerDefenseGame;
import me.BRZeph.core.Assets.AdvancedAssetsManager;
import me.BRZeph.core.Assets.BasicAssetsManager;
import me.BRZeph.core.Assets.CustomGlyphLayout;
import me.BRZeph.core.Assets.FontManager;
import me.BRZeph.core.CurrencyManager;
import me.BRZeph.Screens.ScreenManager;
import me.BRZeph.core.UI.ButtonLayer;
import me.BRZeph.entities.Towers.TowerManager;
import me.BRZeph.core.WaveSystem.WaveManager;
import me.BRZeph.core.UI.ScrollableUI;
import me.BRZeph.core.UI.StaticGridButtonUI;
import me.BRZeph.core.Map.Tile;
import me.BRZeph.core.Map.TileMap;
import me.BRZeph.core.Map.TileType;
import me.BRZeph.entities.Player;
import me.BRZeph.entities.Towers.PlacedTower.Tower;
import me.BRZeph.entities.Towers.TowerItem;
import me.BRZeph.entities.Towers.TowerType;
import me.BRZeph.entities.Towers.UpgradingSystem.TowerUpgrade;
import me.BRZeph.entities.Towers.UpgradingSystem.TowerUpgradeManager;
import me.BRZeph.entities.monster.Monster;
import me.BRZeph.core.Map.Node;

import java.util.*;

import static me.BRZeph.entities.Towers.TowerManager.canBuyTower;
import static me.BRZeph.core.WaveSystem.WaveImport.getWaves;
import static me.BRZeph.utils.Constants.Constants.CameraValues.*;
import static me.BRZeph.utils.Constants.Constants.LocalPaths.GAME_DATA_EXPORT_NAME;
import static me.BRZeph.utils.Constants.Constants.Paths.MapsPath.LEVEL_1_MAP;
import static me.BRZeph.utils.Constants.Constants.Paths.ScreensTexturesPath.LEVEL_1_BACKGROUND;
import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Constants.Paths.UIPath.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.PlayerValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.UIValues.ButtonsValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.UIValues.*;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.UIValues.SelectedTowerValues.*;
import static me.BRZeph.utils.Constants.Constants.SCREEN_HEIGHT;
import static me.BRZeph.utils.Constants.Constants.SCREEN_WIDTH;
import static me.BRZeph.utils.Constants.Constants.TowerUpgradeValues.*;
import static me.BRZeph.utils.GlobalUtils.*;

public class Level1Screen extends BaseLevelScreen implements InputProcessor {
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera orthographicCamera;

    private BitmapFont headerLargeFont;
    private BitmapFont headerFont;
    private BitmapFont headerSmallFont;
    private BitmapFont bodyLargeFont;
    private BitmapFont bodyFont;
    private BitmapFont bodySmallFont;
    private BitmapFont buttonFont;
    private BitmapFont tooltipFont;
    private BitmapFont chatFont;
    private BitmapFont notificationFont;
    private BitmapFont subtitlesFont;
    private BitmapFont placeholderFont;
    private BitmapFont informativeTextFont;

    private Texture heartTexture;
    private Texture testButtonTexture;
    private Texture towerShopBackgroundTexture;
    private Texture towerShopfrontTexture;
    private Texture selectedTowerBackgroundTexture;
    private Texture placeholderButtonTexture;
    private Texture towerUpgradeButtonBackgroundTexture0; // shitty name, deal with it. for 0 upgrades.
    private Texture towerUpgradeButtonBackgroundTexture1; // shitty name, deal with it. for 1 upgrade.
    private Texture towerUpgradeButtonBackgroundTexture2; // shitty name, deal with it. for 2 upgrades.
    private Texture towerUpgradeButtonBackgroundTexture3; // shitty name, deal with it. for 3 upgrades.
    private Texture towerUpgradeButtonBackgroundTexture4; // shitty name, deal with it. for 4 upgrades.
    private Texture towerUpgradeButtonBackgroundTexture100; // shitty name, deal with it. for finalized upgrades on main path.
    private Texture towerUpgradeButtonBackgroundTexture101; // shitty name, deal with it. for finalized upgrades on sec path.
    private Texture towerUpgradeButtonBackgroundTexture102; // shitty name, deal with it. for finalized upgrades on third path.

    private List<StaticGridButtonUI> staticButtonList;
    private List<ScrollableUI> scrollableUIList;
    private StaticGridButtonUI towerLeftAggroButton;
    private StaticGridButtonUI towerRightAggroButton;
    private StaticGridButtonUI towerInfoButton;
    private StaticGridButtonUI towerUpgradePath1;
    private StaticGridButtonUI towerUpgradePath2;
    private StaticGridButtonUI towerUpgradePath3;
    private TileMap tileMap;
    private Player player;
    private WaveManager waveManager;
    private TowerManager towerManager;
    private CurrencyManager currencyManager;
    private TowerUpgradeManager towerUpgradeManager;

    private boolean isPaused;
    private boolean debug;
    private boolean AI;
    private float playerMaxHealth;
    private float playerCurrentHealth;

    public Level1Screen(ScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);

        loadAssets(); // This NEEDS to be the first call.

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        staticButtonList = new ArrayList<>();
        scrollableUIList = new ArrayList<>();
        isPaused = false;
        AI = false; // Initialize as False and then change its value outside this class if it's AI.
        debug = false;
        player = new Player(0, 0, 50);
        tileMap = new TileMap(LEVEL_1_MAP);
        tileMap.loadMapAndFindPath();
        waveManager = new WaveManager(getWaves(tileMap.getPath()));
        towerManager = new TowerManager();
        currencyManager = new CurrencyManager();
        towerUpgradeManager = new TowerUpgradeManager(currencyManager);
        currencyManager.addGold(STARTING_GOLD);
        currencyManager.addEssence(STARTING_ESSENCE);
        currencyManager.addMomentum(STARTING_MOMENTUM);

        playerMaxHealth = PLAYER_HEALTH;
        playerCurrentHealth = playerMaxHealth;

        initializeButtons();
        initializeTowerShopUI();
    }

    @Override
    protected void loadAssets() {
        BasicAssetsManager.loadCommonAssets(assetManager);
        BasicAssetsManager.loadSpecificAssets(1, assetManager);
        AdvancedAssetsManager.loadAdvancedAssets(assetManager);
        StaticGridButtonUI.initializeButtonsTextures();

        heartTexture = assetManager.get(HEART_UI);
        testButtonTexture = assetManager.get(TEST_BUTTON_TEXTURE_PATH, Texture.class);
        towerShopBackgroundTexture = assetManager.get(TOWER_SHOP_BACKGROUND_UI);
        towerShopfrontTexture = assetManager.get(TOWER_SHOP_FRONT_UI);
        selectedTowerBackgroundTexture = assetManager.get(SELECTED_TOWER_BACKGROUND_UI);
        placeholderButtonTexture = assetManager.get(PLACEHOLDER_BUTTON_TEXTURE_PATH);
        towerUpgradeButtonBackgroundTexture0 = assetManager.get(BUTTON_BACKGROUND0);
        towerUpgradeButtonBackgroundTexture1 = assetManager.get(BUTTON_BACKGROUND1);
        towerUpgradeButtonBackgroundTexture2 = assetManager.get(BUTTON_BACKGROUND2);
        towerUpgradeButtonBackgroundTexture3 = assetManager.get(BUTTON_BACKGROUND3);
        towerUpgradeButtonBackgroundTexture4 = assetManager.get(BUTTON_BACKGROUND4);
        towerUpgradeButtonBackgroundTexture100 = assetManager.get(BUTTON_BACKGROUND100);
        towerUpgradeButtonBackgroundTexture101 = assetManager.get(BUTTON_BACKGROUND101);
        towerUpgradeButtonBackgroundTexture102 = assetManager.get(BUTTON_BACKGROUND102);

        headerLargeFont = FontManager.getFont("Header", "large");
        headerFont = FontManager.getFont("Header","");
        headerSmallFont = FontManager.getFont("Header", "small");
        bodyLargeFont = FontManager.getFont("Body", "large");
        bodyFont = FontManager.getFont("Body", "");
        bodySmallFont = FontManager.getFont("Body", "small");
        buttonFont = FontManager.getFont("Button", "");
        tooltipFont = FontManager.getFont("Tooltip", "");
        chatFont = FontManager.getFont("Chat", "");
        notificationFont = FontManager.getFont("Notification", "");
        subtitlesFont = FontManager.getFont("Subtitles", "");
        placeholderFont = FontManager.getFont("Placeholder", "");
        informativeTextFont = FontManager.getFont("Informative_text", "");
    }

    @Override
    public void show() {
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

        renderTileMap();
        renderWave(delta);
        renderTowerProjectiles();
        renderTowerAttackCooldown();
        renderPath();
        renderUI();
        renderPlayer(delta);
    }

    /*
    IMPLEMENTATION PATH:
    1- (DONE): make currency system.
    1.1- (DONE): finish implementing the buy system.
    2- (DONE): make background UI for the "TOWER_MENU_UI" and implement it with a scrollable bar.
    2.1- (DONE): give up on the idea of scrollable ui, create an ui with different pages.
    3- finish towers system.
    4- finish implementing monster system.
    4.1- finish all the 10 existing monsters specific mechanics.
    4.2- consider making the monsters stronger as the time passes (or variations of the same monster wearing armor etc).
    5- hero room.
    6- Finish AI.
    6.1- Major balancing using AI.
    6.2- Major UI modification.
    6.3- Create tutorial/other game modes.
    7- Learn how to steam launch and launch.

    IDEAS:
    1- Towers that buff/helps/interact with the hero.
    1.1- After implementing the hero system, consider making a "class" of towers that can someway somehow interact with the hero.
    1.2- Maybe it's a tower that hits the enemies of the hero, maybe it buffs the hero, maybe it heals the hero, whatever.

    TO FIX:
    1- fix issue that is caused by a map not having a path.
    2- (DONE): fix issue of monster not following the path. Maybe the path is being defined wrongly due to the map load with the Y axis inverted?
    3- fix GlobalUtils.adjustTextWidth().
    4- (DONE): fix the Map of behaviors not working with more than 1 behavior (see line 151 of constants.java).
    5- (DONE): fix inverted Y position on TileMap.Render().
    6- (DONE): fix the targeting system resetting once the selectedTower is set to null.
    7- (DONE): fix map inversion.
    8- (DONE): make upgraded towers different tiles? maybe (see TileType class).
    8.1- (DONE): it probably is not necessary, change whatever I need from the tower using the consumers of upgrade (tower modifier should change the texture and whatever else is needed).
    9- (DONE): fix issue with hero room tiles breaking the game when set to fire.
    10- fix issue with changing state of a rotated tile resetting the rotation of the tile. (is this even happening? idk).
    11- (DONE): fix issue of spawnRule only starting when the highest startTime is triggered.

    TO IMPROVE:
    1- (DONE): make the path finding algorithm more optimized, try version 3 of level 1 map (third map in the level_2_map.txt).
    2- (DONE): make a better currency system (Values.CurrencyTypes). Consider making individual classes for each.
    3- (DONE): make PlacedTower class abstract and every tower such as ARCHER_TOWER an implementation of PlacedTower.
    4- (DONE): improve wave system so that the wave can end at some point, consider adding a "finalBehavior" with a duration.
    5- (DONE): remove "spawnRules" parameter of wave.class and use only behaviors/endBehavior.
    6- update font system to allow the same text to have more than 1 font example: "damage: "{font1} [damage]{font2}.

    AI UPGRADES:
    1- Implement a getBetterTile method inside level1Environment.getPossibleActions() so that the AI prioritize the best tiles for each tower.
    2- (DONE): Fix tileMap problem when placing a tower.
    3- (DONE): Implement TIME_MULTIPLIER constant.
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

    12- implement tower system.
    12.1- (DONE): implement CannonTower (aoe damage).
    12.2- (DONE): implement LightningTower (chain damage).
    12.2.1- (DONE): finish update projectile method of lightningTower.
    12.3- implement 3 more neutral, 3 light and 3 dark towers.
    12.4- implement leveling tree system.
    12.4.1- (DONE): implement handler inside towerUpgradeManager.getUpgrade(). Actually, this should be checked by the canUpgrade().
    12.4.1.1- (DONE): implement closing the unselected path after selecting both the main and secondary paths.
    12.4.1.2- (DONE): ^^ should use a texture for "CLOSED PATH" or something, maybe for now just change the text.
    12.4.1.3- implement hot keys for upgrading.
    12.4.1.4- (DONE): implement using the textures of each upgrade, change the button texture for the upgrade.
    12.4.1.5- (DONE): implement an ui for seeing the upgrades left (like the green squares in btd6).
    12.4.1.6- implement the actual upgrades instead of placeholders.
    12.4.1.7- implement getDescription method to tower and make each upgrade modify it to add the new information after upgrading.
    12.4.2- make all constants of the tower upgrades reference a constant inside Constants.class.
    12.4.2.1- maybe(?): while there, make the StaticGridButtonUI.initializeButtonTextures() also reference Constants.class.
    14.5- finish implementing the TowerVisuals.class and TowerVisualsFactory.class.
    12.5- implement TowerVisual.class.

    13- implement new enemies.
    13.1- implement monsters abilities.
    13.2- maybe(?) increase monsters stats as the waves progresses (+10% hp every wave etc).
    13.3- (DONE): allow multiple variants in the waves.json for the same mob.
    13.4- make corruption scaling faster after the final wave (during free play).
    13.5- consider making variants have a modified reward.

    14- (DONE): implement closesMonsterStrategy that will aggro the closest monster to the tower.

    15- (DONE): implement tab click switching the current tower aggro to the next aggro if the selected tower != null.

    16- Implement static method inside GlobalUtils to rendering animations such as archer tower rain of arrows attack.

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

    20- (Done): tower upgrade tree.

    TO REFACTOR:
    1- refactor A LOT of code from this class into the correct managers.
    1.1- create a ButtonManager.class and refactor code.
    2- (DONE): extract renderAttackCooldown to a static method in GlobalUtils (maybe?).
    3- (DONE): make Tower interface an abstract class to prevent needing a lot of recode.
    4- TowerUpgradeManager.class should probably be inside TowerManager.class.

    TO IMPLEMENT (TEXTURES):
    1- Texture for left aggro button, right aggro button, better tiles, better ui in general.
    2- Blood splatter when the archer tower his a monster.
    3- explosion animation for cannon hit.
    4- lightning animation for lightning tower.
    5- create lesser_abyssal_matron textures and update the enum.
     */

    private void renderUI() {
        Matrix4 originalProjection = spriteBatch.getProjectionMatrix().cpy();
        spriteBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        renderSelectedTowerUI();
        shapeRenderer.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        renderShopUI();
        renderFPSUI();
        renderPlayerHealthUI();
        renderCurrentWaveUI();
        renderCurrencyUI();
        renderStaticButtons();
        renderHoldingItem();
        renderTipsUI(); //REMOVE LATER
        spriteBatch.setProjectionMatrix(originalProjection);
        shapeRenderer.setProjectionMatrix(originalProjection);

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
            towerInfoButton.setDisable(false);
            towerUpgradePath1.setDisable(false);
            towerUpgradePath2.setDisable(false);
            towerUpgradePath3.setDisable(false);
            int tileX = towerManager.getSelectedTower().getxPos();
            int tileY = towerManager.getSelectedTower().getyPos();
            int radius = (int) towerManager.getSelectedTower().getRange(); // deleted this later.
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

            spriteBatch.begin();
            spriteBatch.draw(
                selectedTowerBackgroundTexture,
                SELECTED_TOWER_X_POS,
                SELECTED_TOWER_Y_POS,
                SELECTED_TOWER_UI_WIDTH,
                SELECTED_TOWER_UI_HEIGHT
                );
            spriteBatch.draw(towerTexture, TOWER_TEXTURE_X_POS, TOWER_TEXTURE_Y_POS, TOWER_TEXTURE_WIDTH, TOWER_TEXTURE_HEIGHT);
            spriteBatch.end();
        } else {
            towerLeftAggroButton.setDisable(true);
            towerRightAggroButton.setDisable(true);
            towerInfoButton.setDisable(true);
            towerUpgradePath1.setDisable(true);
            towerUpgradePath2.setDisable(true);
            towerUpgradePath3.setDisable(true);
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
        StaticGridButtonUI startWaveButton = getStartWaveButton();
        towerLeftAggroButton = getLeftTowerAggroButton();
        towerRightAggroButton = getRightTowerAggroButton();
        towerInfoButton = getTowerInfoButton();
        towerUpgradePath1 = initializeTowerUpgradePath1Button();
        towerUpgradePath2 = initializeTowerUpgradePath2Button();
        towerUpgradePath3 = initializeTowerUpgradePath3Button();

        /*
        change path's button texture to the actual texture from the constructor and make it update after each upgrade.
         */

        staticButtonList.add(towerUpgradePath1);
        staticButtonList.add(towerUpgradePath2);
        staticButtonList.add(towerUpgradePath3);
        staticButtonList.add(startWaveButton);
        staticButtonList.add(towerLeftAggroButton);
        staticButtonList.add(towerRightAggroButton);
        staticButtonList.add(towerInfoButton);
    }

    private void updateSelectedTowerButtons() {
        updateUpgradeButton(towerUpgradePath1, 1);
        updateUpgradeButton(towerUpgradePath2, 2);
        updateUpgradeButton(towerUpgradePath3, 3);

        towerInfoButton.putDisplayGlyph("info", new CustomGlyphLayout(informativeTextFont, "Extra information"));
        towerInfoButton.getDisplayGlyph("info").setX(towerInfoButton.getCenterX());
        towerInfoButton.getDisplayGlyph("info").setY(towerInfoButton.getCenterY());

        towerInfoButton.setOnHover(() -> {
            towerInfoButton.renderButtonInformationOnHover(spriteBatch, informativeTextFont, getTowerInformation());
        });
    }

    private void updateUpgradeButton(StaticGridButtonUI towerUpgradePathX, int path) {
        int tier;
        int secPath = towerManager.getSelectedTower().getSecondPath();
        int mainPath = towerManager.getSelectedTower().getMainPath();
        int[] chosenPaths = towerManager.getSelectedTower().getChosenPaths();
        TowerUpgrade upgrade = towerUpgradeManager.getUpgrade(towerManager.getSelectedTower(), path);

        if (path == mainPath){

            if (upgrade != null) {
                tier = updateButtonDisplayGlyph(towerUpgradePathX, path, upgrade);
            } else { // Locks main path
                tier = 100; // Fully upgraded already, set final texture.
                towerUpgradePathX.setLocked(spriteBatch, buttonFont, informativeTextFont);
            }

        } else if (path == secPath) {

            if (towerManager.getSelectedTower().getTier(secPath) != SECONDARY_PATH_UPGRADE_LIMIT) {
                tier = updateButtonDisplayGlyph(towerUpgradePathX, path, upgrade);
            } else { // Locks secondary path.
                towerUpgradePathX.setLocked(spriteBatch, buttonFont, informativeTextFont);
                tier = 101;
            }

        } else { // Path == thirdPath OR thirdPath does not exist.

            if (mainPath != -1 && secPath != -1) { // If main and sec path aren't defined, the logic can't check for third path.

                if (towerManager.getSelectedTower().getTier(mainPath) != 0 && towerManager.getSelectedTower().getTier(secPath) != 0) { // Main and sec path are defined, lock third.
                    tier = 102;
                    towerUpgradePathX.setLocked(spriteBatch, buttonFont, informativeTextFont);
                } else { // This is never reached.
                    tier = updateButtonDisplayGlyph(towerUpgradePathX, path, upgrade);
                }

            } else {

                boolean thirdPath = true;
                for (int i : chosenPaths){
                    if (i == path){
                        thirdPath = false;
                        break;
                    }
                }
                if (thirdPath && chosenPaths[1] != -1){ // if this is the third path AND main/sec paths have been chosen, lock this path.
                    tier = 102;
                    towerUpgradePathX.setLocked(spriteBatch, buttonFont, informativeTextFont);
                } else {
                    tier = updateButtonDisplayGlyph(towerUpgradePathX, path, upgrade);
                }
            }
        }

        towerUpgradePathX.clearLayers();

        if (tier < MAIN_PATH_UPGRADE_LIMIT){
            if(upgrade.getBuyTexture() != null) { // ( Texture == null ) <-> ( Texture not created or loaded in asset manager).
                towerUpgradePathX.addLayer(new ButtonLayer( // Upgrade texture.
                    upgrade.getBuyTexture(),
                    (float) TOWER_PATH_UPGRADE_BUTTON_WIDTH / 2, 0,
                    (float) TOWER_PATH_UPGRADE_BUTTON_WIDTH / 2, TOWER_PATH_UPGRADE_BUTTON_HEIGHT,
                    1
                ));
            }
        }

        switch (tier) {
            case 0:
                towerUpgradePathX.setTexture(towerUpgradeButtonBackgroundTexture0);
                break;
            case 1:
                towerUpgradePathX.setTexture(towerUpgradeButtonBackgroundTexture1);
                break;
            case 2:
                towerUpgradePathX.setTexture(towerUpgradeButtonBackgroundTexture2);
                break;
            case 3:
                towerUpgradePathX.setTexture(towerUpgradeButtonBackgroundTexture3);
                break;
            case 4:
                towerUpgradePathX.setTexture(towerUpgradeButtonBackgroundTexture4);
                break;
            case 100:
                towerUpgradePathX.setTexture(towerUpgradeButtonBackgroundTexture100);
                break;
            case 101:
                towerUpgradePathX.setTexture(towerUpgradeButtonBackgroundTexture101);
                break;
            case 102:
                towerUpgradePathX.setTexture(towerUpgradeButtonBackgroundTexture102);
                break;
            default:
                throw new IllegalArgumentException("Illegal value for tier: " + tier);
        }
    }

    private int updateButtonDisplayGlyph(StaticGridButtonUI towerUpgradePathX, int path, TowerUpgrade upgrade) {
        int tier;
        towerUpgradePathX.removeDisplayLayout("final");
        towerUpgradePathX.putDisplayGlyph("name", new CustomGlyphLayout(buttonFont, upgrade.getName()));
        towerUpgradePathX.getDisplayGlyph("name").setX(towerUpgradePathX.getCenterX() - 71);
        towerUpgradePathX.getDisplayGlyph("name").setY(towerUpgradePathX.getCenterY());
        towerUpgradePathX.putDisplayGlyph("price", new CustomGlyphLayout(headerSmallFont, upgrade.getCost()));
        towerUpgradePathX.getDisplayGlyph("price").setX(towerUpgradePathX.getCenterX() + 71);
        towerUpgradePathX.getDisplayGlyph("price").setY(towerUpgradePathX.getCenterY() + 60);
        towerUpgradePathX.setOnHover(() -> {
            TowerUpgrade upgrade2 = towerUpgradeManager.getUpgrade(towerManager.getSelectedTower(), path); // Can't use the same upgrade inside lambda.
            towerUpgradePathX.renderButtonInformationOnHover(spriteBatch, informativeTextFont, upgrade2.getDescription());
        });
        tier = upgrade.getTier() - 1;
        return tier;
    }

    private StaticGridButtonUI getStartWaveButton() {
        StaticGridButtonUI button = new StaticGridButtonUI(
            START_WAVE_BUTTON_X_POS, START_WAVE_BUTTON_Y_POS,
            START_WAVE_BUTTON_WIDTH, START_WAVE_BUTTON_HEIGHT,
            assetManager.get(START_WAVE_BUTTON_TEXTURE_PATH),
            () -> {
                waveManager.startNextWave();
            },
            () -> {
            }
        );
        button.setOnHover(() -> {
            button.renderButtonInformationOnHover(spriteBatch, informativeTextFont, "Começar\na\nwave\n bora");
        });
        return button;
    }

    private StaticGridButtonUI getLeftTowerAggroButton() {
        StaticGridButtonUI button = new StaticGridButtonUI(
            TOWER_AGGRO_LEFT_BUTTON_X_POS, TOWER_AGGRO_LEFT_BUTTON_Y_POS,
            TOWER_AGGRO_LEFT_BUTTON_WIDTH, TOWER_AGGRO_LEFT_BUTTON_HEIGHT,
            testButtonTexture,
            () -> {
                towerManager.getSelectedTower().setPreviousTargetingStrategy();
            },
            () -> {
            }
        );
        button.setOnHover(() -> {
            button.renderButtonInformationOnHover(spriteBatch, informativeTextFont, "Select previous aggro");
        });
        return button;
    }

    private StaticGridButtonUI getRightTowerAggroButton() {
        StaticGridButtonUI button = new StaticGridButtonUI(
            TOWER_AGGRO_RIGHT_BUTTON_X_POS, TOWER_AGGRO_RIGHT_BUTTON_Y_POS,
            TOWER_AGGRO_RIGHT_BUTTON_WIDTH, TOWER_AGGRO_RIGHT_BUTTON_HEIGHT,
            testButtonTexture,
            () -> {
                towerManager.getSelectedTower().setNextTargetingStrategy();
            },
            () -> {
            }
        );
        button.setOnHover(() -> {
            button.renderButtonInformationOnHover(spriteBatch, informativeTextFont, "Select Next aggro");
        });
        return button;
    }

    private StaticGridButtonUI getTowerInfoButton() {
        return new StaticGridButtonUI(
            TOWER_INFO_BUTTON_X_POS, TOWER_INFO_BUTTON_Y_POS,
            TOWER_INFO_BUTTON_WIDTH, TOWER_INFO_BUTTON_HEIGHT,
            placeholderButtonTexture,
            () -> {
                towerManager.getSelectedTower().setNextTargetingStrategy();
            },
            () -> {
            }
        );
    }

    private StaticGridButtonUI initializeTowerUpgradePath1Button() {
        return new StaticGridButtonUI(
            TOWER_PATH1_UPGRADE_BUTTON_X_POS, TOWER_PATH1_UPGRADE_BUTTON_Y_POS,
            TOWER_PATH_UPGRADE_BUTTON_WIDTH, TOWER_PATH_UPGRADE_BUTTON_HEIGHT,
            towerUpgradeButtonBackgroundTexture0,
            () -> {
                if (towerUpgradeManager.canUpgrade(getTowerManager().getSelectedTower(), 1)){
                    TowerUpgrade upgrade = towerUpgradeManager.getUpgrade(towerManager.getSelectedTower(), 1);
                    if (towerUpgradeManager.canAffordUpgrade(upgrade)){
                        towerUpgradeManager.applyUpgradeToTower(towerManager.getSelectedTower(), upgrade);
                        updateSelectedTowerButtons();
                    }
                }
            },
            () -> {
            }
        );
    }

    private StaticGridButtonUI initializeTowerUpgradePath2Button() {
        return new StaticGridButtonUI(
            TOWER_PATH2_UPGRADE_BUTTON_X_POS, TOWER_PATH2_UPGRADE_BUTTON_Y_POS,
            TOWER_PATH_UPGRADE_BUTTON_WIDTH, TOWER_PATH_UPGRADE_BUTTON_HEIGHT,
            towerUpgradeButtonBackgroundTexture0,
            () -> {
                if (towerUpgradeManager.canUpgrade(getTowerManager().getSelectedTower(), 2)){
                    TowerUpgrade upgrade = towerUpgradeManager.getUpgrade(towerManager.getSelectedTower(), 2);
                    if (towerUpgradeManager.canAffordUpgrade(upgrade)){
                        towerUpgradeManager.applyUpgradeToTower(towerManager.getSelectedTower(), upgrade);
                        updateSelectedTowerButtons();
                    }
                }
            },
            () -> {
            }
        );
    }

    private StaticGridButtonUI initializeTowerUpgradePath3Button() {
        return new StaticGridButtonUI(
            TOWER_PATH3_UPGRADE_BUTTON_X_POS, TOWER_PATH3_UPGRADE_BUTTON_Y_POS,
            TOWER_PATH_UPGRADE_BUTTON_WIDTH, TOWER_PATH_UPGRADE_BUTTON_HEIGHT,
            towerUpgradeButtonBackgroundTexture0,
            () -> {
                if (towerUpgradeManager.canUpgrade(getTowerManager().getSelectedTower(), 3)){
                    TowerUpgrade upgrade = towerUpgradeManager.getUpgrade(towerManager.getSelectedTower(), 3);
                    if (towerUpgradeManager.canAffordUpgrade(upgrade)){
                        towerUpgradeManager.applyUpgradeToTower(towerManager.getSelectedTower(), upgrade);
                        updateSelectedTowerButtons();
                    }
                }
            },
            () -> {
            }
        );
    }

    private String getTowerInformation() {
        Tower tower = getTowerManager().getSelectedTower();
        String attackSpeed = String.format("%.2f", tower.getAttackCooldown());
        return "Damage: " + tower.getDamage() + "\n" +
            "Attack speed: " + attackSpeed + "/sec\n" +
            "Aggro: " + tower.getTargetingStrategy() + "\n" +
            "Range: " + tower.getRange();
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
                button.renderButtonInformationOnHover(spriteBatch, informativeTextFont, "Não implementado ainda");
            });

            buttons.add(button);
        }
        return buttons;
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
            buyArcher.renderButtonInformationOnHover(spriteBatch, informativeTextFont,
                "Comprar torre de arquearia\npreco" + "\n" +
                    "ouro -> " + (int)TowerType.ARCHER.getGoldCost() + "\n" +
                    "essencia -> " + (int)TowerType.ARCHER.getEssenceCost() + "\n" +
                    "momentum -> " + (int)TowerType.ARCHER.getMomentumCost()
            );
        });
        return buyArcher;
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
            buyLightning.renderButtonInformationOnHover(spriteBatch, informativeTextFont,
                "Comprar torre de raio\npreco" + "\n" +
                    "ouro -> " + (int)TowerType.LIGHTNING.getGoldCost() + "\n" +
                    "essencia -> " + (int)TowerType.LIGHTNING.getEssenceCost() + "\n" +
                    "momentum -> " + (int)TowerType.LIGHTNING.getMomentumCost()
            );
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
            buyCannon.renderButtonInformationOnHover(spriteBatch, informativeTextFont,
                "Comprar torre de canhao\npreco" + "\n" +
                    "ouro -> " + (int)TowerType.CANNON.getGoldCost() + "\n" +
                    "essencia -> " + (int)TowerType.CANNON.getEssenceCost() + "\n" +
                    "momentum -> " + (int)TowerType.CANNON.getMomentumCost()
            );
        });
        return buyCannon;
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

        for (StaticGridButtonUI staticButton : staticButtonList){
            staticButton.render(spriteBatch);
        }

        for (ScrollableUI scrollableUI : scrollableUIList){
            for (StaticGridButtonUI staticGridButtonUI : scrollableUI.getButtons()){
                staticGridButtonUI.checkHover(mouseXHover, mouseYHover);
            }
        }

        for (StaticGridButtonUI staticButton : staticButtonList){
            staticButton.checkHover(mouseXHover, mouseYHover);
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
                    updateSelectedTowerButtons();
                    break;
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
        List<Monster> reachedEnd    = waveManager.getCurrentWave().getMonsterReachedEnd();

        for (Monster monster : reachedEnd) {
            playerCurrentHealth -= monster.getType().getNexusDmg();
            monstersAlive.remove(monster);
        }
        reachedEnd.clear();
        List<Monster> died = waveManager.getCurrentWave().getMonsterDied();
        for (Monster monster : died) {
            monstersAlive.remove(monster);
            monster.die(currencyManager, waveManager.getCurrentWave());
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
            TowerDefenseGame.getScreenManager().showMainMenu();
        }
    }

    private void renderTileMap(){
        tileMap.render(spriteBatch);
    }

    private void renderWave(float delta) {
        waveManager.render(spriteBatch, bodySmallFont, shapeRenderer, delta);
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
                    placeholderFont.draw(spriteBatch, message, (float)(0.5 + current.x) * TILE_WIDTH, messageYPos);
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
        String message = "Gold -> " + (int) currencyManager.getGold() + "\n" +
                         "Essence -> " + (int) currencyManager.getEssence() + "\n" +
                         "Momentum -> " + (int) currencyManager.getMomentum();

        spriteBatch.begin();
        bodyLargeFont.draw(spriteBatch, message, startX, startY);
        spriteBatch.end();
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
            informativeTextFont.draw(spriteBatch, message, startX, startY);
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

        spriteBatch.begin();
        bodyLargeFont.draw(spriteBatch, message, startX, startY);
        message = "Timer: " + minutes + ":" + seconds;
        bodyLargeFont.draw(spriteBatch, message, startX, startY - CURRENT_WAVE_CLOCK_HEIGHT_DECREASE);
        spriteBatch.end();
    }

    private void renderFPSUI() {
        float startY = SCREEN_HEIGHT - FPS_Y_POS_DECREASE;
        float fps = Gdx.graphics.getFramesPerSecond();
        String message = "FPS: " + (int) fps;
        drawTextWrapped(spriteBatch, bodySmallFont, message, FPS_X_POS, startY, 160);
    }

    private void renderPlayerHealthUI() {
        float startX = HEALTH_X_POS;
        float startY = SCREEN_HEIGHT - HEALTH_Y_POS_DECREASE;

        String message = (int) playerCurrentHealth + " / " + (int) playerMaxHealth;
        drawTextureNextToMessage(spriteBatch, headerFont, heartTexture, HEART_WIDTH, HEART_HEIGHT, startX, startY, message, true);
        drawTextureNextToMessage(spriteBatch, headerFont, heartTexture, HEART_WIDTH, HEART_HEIGHT, startX, startY, message, false);
        drawCenteredText(spriteBatch, headerFont, message, startX, startY);
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
        consoleLog("[INFO]: Calling dispose method of Level1Screen");
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
        if(towerManager.getSelectedTower() != null){
            if(keycode == Input.Keys.TAB){
                if(player.isHoldingDownShift()){
                    towerManager.getSelectedTower().setPreviousTargetingStrategy();
                } else {
                    towerManager.getSelectedTower().setNextTargetingStrategy();
                }
            }
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
