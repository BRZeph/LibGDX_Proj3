package me.BRZeph.core.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ButtonLayer {
    private TextureRegion texture;
    private float xOffset; // Relative to button's x position
    private float yOffset; // Relative to button's y position
    private float width;
    private float height;
    private int depth; // Determines rendering order

    public ButtonLayer(TextureRegion texture, float xOffset, float yOffset, float width, float height, int depth) {
        this.texture = texture;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }
}

