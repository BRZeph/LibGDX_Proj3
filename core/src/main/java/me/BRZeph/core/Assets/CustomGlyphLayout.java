package me.BRZeph.core.Assets;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.BRZeph.utils.GlobalUtils;

public class CustomGlyphLayout {
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private String text;
    private float x,y;

    public CustomGlyphLayout(BitmapFont font, String text){
        this.font = font;
        this.glyphLayout = new GlyphLayout(font, text);
        this.text = text;
    }

    public void draw(SpriteBatch batch){
        GlobalUtils.drawCenteredText(batch, font, text, x, y);
    }

    public BitmapFont getFont() {
        return font;
    }

    public GlyphLayout getGlyphLayout() {
        return glyphLayout;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.glyphLayout.setText(font, text);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
