package me.BRZeph.core.UI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import me.BRZeph.core.Assets.CustomGlyphLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static me.BRZeph.core.Assets.AdvancedAssetsManager.getUITexture;
import static me.BRZeph.utils.Constants.Constants.Paths.UIPath.UI_ATLAS_REGION_LENGTH;
import static me.BRZeph.utils.Constants.Constants.SCREEN_HEIGHT;
import static me.BRZeph.utils.Constants.Constants.SCREEN_WIDTH;

public class StaticGridButtonUI { // Used for screen coordinates (static UI).
    private static TextureRegion TLCTexture, TRCTexture, BLCTexture, BLCTextureFlip, BRCTexture, BRCTextureFlip, LBTexture, UBTexture, RBTexture, DBTexture, MTexture, BLCATexture, BRCATexture;
    private Rectangle bounds; // To check for clicks
    private Texture texture;
    private Runnable onClick; // Action to perform when the button is clicked
    private Runnable onHover; // Action to perform when the button is clicked
    private float x, y, width, height, centerX, centerY;
    private boolean disable;
    private HashMap<String, CustomGlyphLayout> displayGlyph;
    private List<ButtonLayer> layers;

    public StaticGridButtonUI(float x, float y, float width, float height, Texture texture,
                              Runnable onClick, Runnable onHover) {
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = texture;
        this.onClick = onClick;
        this.onHover = onHover;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.disable = false;
        this.centerX = x + width/2;
        this.centerY = y + height/2;
        this.displayGlyph = new HashMap<>();
        this.layers = new ArrayList<>();
    }

    public StaticGridButtonUI(Texture texture){
        this.texture = texture;
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.bounds = new Rectangle(0,0,100,100);
        this.displayGlyph = new HashMap<>();
        this.layers = new ArrayList<>();
    }

    public static void initializeButtonsTextures(){
        TLCTexture = getUITexture("btn_speech_bubble_TLC");
        TRCTexture = getUITexture("btn_speech_bubble_TRC");
        BLCTexture = getUITexture("btn_speech_bubble_BLC");
        BRCTexture = getUITexture("btn_speech_bubble_BRC");
        LBTexture  = getUITexture("btn_speech_bubble_LB");
        RBTexture  = getUITexture("btn_speech_bubble_RB");
        UBTexture  = getUITexture("btn_speech_bubble_UB");
        DBTexture  = getUITexture("btn_speech_bubble_DB");
        MTexture   = getUITexture("btn_speech_bubble_M");

        BLCTextureFlip = getUITexture("btn_speech_bubble_BLC");
        BLCTextureFlip.flip(true,false);
        BRCTextureFlip = getUITexture("btn_speech_bubble_BRC");
        BRCTextureFlip.flip(true,false);
    }

    public void render(SpriteBatch batch) {

        if (disable) return;

        batch.begin();
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        batch.end();

        if (!layers.isEmpty()) {
            batch.begin();
            for (ButtonLayer layer : layers) {
                batch.draw(layer.getTexture(),
                    bounds.x + layer.getxOffset(),
                    bounds.y + layer.getyOffset(),
                    layer.getWidth(),
                    layer.getHeight()
                );
            }
            batch.end();
        }

        if (displayGlyph != null) {
            if (!displayGlyph.isEmpty()) {
                for (CustomGlyphLayout glyph : displayGlyph.values()) {
                    glyph.draw(batch);
                }
            }
        }

    }

    public boolean checkClick(float mouseX, float mouseY) {
        if (disable) return false;
        if (bounds.contains(mouseX, mouseY)) {
            onClick.run();
            return true;
        } else {
            return false;
        }
    }

    public void checkHover(float mouseX, float mouseY){
        if (disable) return;
        if (bounds.contains(mouseX, mouseY)){
            onHover.run();
        }
    }

    public void renderButtonInformationOnHover(SpriteBatch batch, BitmapFont font, String message) {
        if (disable) return;
        float offset = UI_ATLAS_REGION_LENGTH + 8;

        GlyphLayout layout = new GlyphLayout(font, message);
        float messageWidth = layout.width + offset;
        float messageHeight = layout.height + offset;


        float backgroundWidth = messageWidth + offset;
        float backgroundHeight = messageHeight + offset;

        float mouseX = Gdx.input.getX();
        float mouseY = SCREEN_HEIGHT - Gdx.input.getY();

        if (mouseX + backgroundWidth > SCREEN_WIDTH) {
            mouseX = mouseX - backgroundWidth;
            if (!BLCTexture.isFlipX()){
                BLCTexture.flip(true,false);
            }
            if (!BRCTexture.isFlipX()){
                BRCTexture.flip(true,false);
            }
            BLCATexture = BRCTextureFlip;
            BRCATexture = BLCTextureFlip;
        } else {
            if (BLCTexture.isFlipX()){
                BLCTexture.flip(true,false);
            }
            if (BRCTexture.isFlipX()){
                BRCTexture.flip(true,false);
            }
            BLCATexture = BLCTexture;
            BRCATexture = BRCTexture;
        }

        batch.begin();
        // Draw corners
        batch.draw(BLCATexture, mouseX, mouseY);
        batch.draw(BRCATexture, mouseX + backgroundWidth - TRCTexture.getRegionWidth(), mouseY);
        batch.draw(TLCTexture, mouseX, mouseY + backgroundHeight - BLCTexture.getRegionHeight());
        batch.draw(TRCTexture, mouseX + backgroundWidth - BRCTexture.getRegionWidth(), mouseY + backgroundHeight - BRCTexture.getRegionHeight());
        // Draw borders
        batch.draw(DBTexture, mouseX + TLCTexture.getRegionWidth(), mouseY, backgroundWidth - TLCTexture.getRegionWidth() - TRCTexture.getRegionWidth(), UBTexture.getRegionHeight());
        batch.draw(UBTexture, mouseX + BLCTexture.getRegionWidth(), mouseY + backgroundHeight - DBTexture.getRegionHeight(), backgroundWidth - BLCTexture.getRegionWidth() - BRCTexture.getRegionWidth(), DBTexture.getRegionHeight());
        batch.draw(LBTexture, mouseX, mouseY + TLCTexture.getRegionHeight(), LBTexture.getRegionWidth(), backgroundHeight - TLCTexture.getRegionHeight() - BLCTexture.getRegionHeight());
        batch.draw(RBTexture, mouseX + backgroundWidth - RBTexture.getRegionWidth(), mouseY + TRCTexture.getRegionHeight(), RBTexture.getRegionWidth(), backgroundHeight - TRCTexture.getRegionHeight() - BRCTexture.getRegionHeight());
        // Draw the middle part
        batch.draw(MTexture, mouseX + TLCTexture.getRegionWidth(), mouseY + TLCTexture.getRegionHeight(),
            backgroundWidth - TLCTexture.getRegionWidth() - TRCTexture.getRegionWidth(),
            backgroundHeight - TLCTexture.getRegionHeight() - BLCTexture.getRegionHeight());

        font.draw(batch, message, mouseX + offset, mouseY + messageHeight);

        batch.end();
    }

    public void addLayer(ButtonLayer layer){
        layers.add(layer);
        layers.sort(Comparator.comparingInt(ButtonLayer::getDepth));
    }

    public void clearLayers(){
        layers.clear();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.bounds.x = x;
        this.x = x;
    }

    public void setY(float y) {
        this.bounds.y = y;
        this.y = y;
    }

    public void setWidth(float width) {
        this.bounds.setWidth(width);
        this.width = width;
    }

    public void setHeight(float height) {
        this.bounds.setHeight(height);
        this.height = height;
    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public void setOnHover(Runnable onHover) {
        this.onHover = onHover;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public CustomGlyphLayout getDisplayGlyph(String name){
        if (displayGlyph.containsKey(name)) {
            return this.displayGlyph.get(name);
        } else {
            return null;
        }
    }

    public void removeDisplayLayout(String name){
        displayGlyph.remove(name);
    }

    public void putDisplayGlyph(String name, CustomGlyphLayout displayGlyph){
        this.displayGlyph.put(name, displayGlyph);
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setLocked(SpriteBatch spriteBatch, BitmapFont buttonFont, BitmapFont informativeTextFont) {
        this.removeDisplayLayout("name");
        this.removeDisplayLayout("price");
        this.putDisplayGlyph("final", new CustomGlyphLayout(buttonFont, "Finalized"));
        this.getDisplayGlyph("final").setX(this.getCenterX() - 71);
        this.getDisplayGlyph("final").setY(this.getCenterY());
        this.setOnHover(() -> { // Replace hover after finalizing upgrades to prevent problems.
            this.renderButtonInformationOnHover(spriteBatch, informativeTextFont, "Finalized");
        });
    }
}
