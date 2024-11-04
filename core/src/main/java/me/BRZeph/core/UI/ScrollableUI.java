package me.BRZeph.core.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.BRZeph.utils.GlobalUtils;

import java.awt.*;
import java.util.List;

public class ScrollableUI {
    private final int startX, startY, endX, endY;
    private final int gridX, gridY;
    private final int borderSize;
    private final Texture backgroundTexture, frontTexture;
    private final List<StaticGridButtonUI> buttons;
    private float itemLength;
    private float adjustBorder;

    public ScrollableUI(int startX, int startY, int endX, int endY,
                        int gridX, int gridY,
                        int itemLength,
                        Texture backgroundTexture, Texture frontTexture,
                        List<StaticGridButtonUI> buttons) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.gridX = gridX;
        this.gridY = gridY;
        this.backgroundTexture = backgroundTexture;
        this.frontTexture = frontTexture;
        this.buttons = buttons;
        this.itemLength = itemLength;
        this.borderSize = calculateBorderSize();
        this.adjustBorder = calculateAdjustBorder();
        if (buttons.size() > gridX*gridY){
            GlobalUtils.consoleLog("buttons size -> " + buttons.size() + "\n" +
                "gridX * gridY -> " + (gridX*gridY));
            throw new IllegalArgumentException("Buttons list > gridX*gridY");
        }
    }

    private float calculateAdjustBorder() {
        float adjustBorder;
        if (endY - startY > endX - startX){ //height > width
            adjustBorder = (endY - startY - (gridY)*(itemLength + 2*borderSize))/2;
        } else {
            adjustBorder = (endX - startX - gridX*(itemLength + 2*borderSize))/2;
        }
        return (int)adjustBorder;
    }

    private int calculateBorderSize() {
        double borderSize;
        if (endY - startY > endX - startX){ //height > width
            borderSize = ((endX - startX) - gridX*itemLength)/(2*gridX + 1.5);
        } else {
            borderSize = ((endY - startY) - gridY*itemLength)/(2*gridY + 1.5);
        }
        return (int)borderSize;
    }
    /*
    8 9
    6 7
    4 5
    2 3
    0 1
    gridX = 2
    gridY = 5
     */

    public void render(SpriteBatch batch) {

        batch.begin();
        batch.draw(backgroundTexture, startX, startY, endX - startX, endY - startY);
        batch.end();

        // Calculate button positions
        float buttonX, buttonY;
        int count = 0;

        for (int col = 0; col < gridY; col++) {
            for (int row = 0; row < gridX; row++) {

                int index = col * gridX + row;
                if (index < buttons.size() && buttons.get(count) != null) {

                    buttonX = (float) (startX + itemLength*row + (2*row + 1.5)*borderSize);
                    buttonY = (float) (startY + itemLength*col + (2*col + 1.5)*borderSize);

                    if (endY - startY > endX - startX){
                        buttonY += adjustBorder;
                    } else {
                        buttonX += adjustBorder;
                    }

                    buttons.get(count).setWidth(itemLength);
                    buttons.get(count).setHeight(itemLength);
                    buttons.get(count).setY(buttonY);
                    buttons.get(count).setX(buttonX);
                    buttons.get(count).render(batch);
                }
                count++;
            }
        }

        // Render front texture
//        batch.begin();
//        batch.draw(frontTexture, startX, startY, endX - startX, endY - startY);
//        batch.end();
    }

    public Rectangle getBounds(){
        return new Rectangle(startX, startY, endX - startX, endY - startY);
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
}
