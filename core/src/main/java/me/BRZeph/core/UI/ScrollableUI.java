package me.BRZeph.core.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class ScrollableUI {
    private final int startX, startY, endX, endY;
    private final int gridX, gridY;
    private final float itemWidth, itemHeight, borderSize;
    private final Texture backgroundTexture, frontTexture;
    private final List<StaticGridButtonUI> buttons;
    private float scrollOffset;

    public ScrollableUI(int startX, int startY, int endX, int endY, int gridX, int gridY,
                        float itemWidth, float itemHeight, float borderSize,
                        Texture backgroundTexture, Texture frontTexture,
                        List<StaticGridButtonUI> buttons) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.gridX = gridX;
        this.gridY = gridY;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.borderSize = borderSize;
        this.backgroundTexture = backgroundTexture;
        this.frontTexture = frontTexture;
        this.buttons = buttons;
        this.scrollOffset = 0;
    }

    public void render(SpriteBatch batch) {

        batch.begin();
        batch.draw(backgroundTexture, startX, startY, endX - startX, endY - startY);
        batch.end();

        // Calculate button positions
        float buttonX, buttonY;
        int count = 0;
        for (int row = 0; row < gridY; row++) {
            for (int col = 0; col < gridX; col++) {
                int index = row * gridX + col;
                if (index < buttons.size()) {
                    buttonX = startX + col * (itemWidth + borderSize);
                    buttonY = startY + (row * (itemHeight + borderSize) - scrollOffset);
                    if (buttons.get(count) != null) {
                        buttons.get(count).setWidth(itemWidth);
                        buttons.get(count).setHeight(itemHeight);
                        buttons.get(count).setY(buttonY);
                        buttons.get(count).setX(buttonX);
                        buttons.get(count).render(batch);
                    }
                }
                count++;
            }
        }

        // Render front texture
        batch.begin();
        batch.draw(frontTexture, startX, startY, endX - startX, endY - startY);
        batch.end();
    }

    public void setScrollOffset(float scrollOffset) {
        this.scrollOffset = scrollOffset;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public float getItemWidth() {
        return itemWidth;
    }

    public float getItemHeight() {
        return itemHeight;
    }

    public float getBorderSize() {
        return borderSize;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Texture getFrontTexture() {
        return frontTexture;
    }

    public List<StaticGridButtonUI> getButtons() {
        return buttons;
    }

    public float getScrollOffset() {
        return scrollOffset;
    }
}
