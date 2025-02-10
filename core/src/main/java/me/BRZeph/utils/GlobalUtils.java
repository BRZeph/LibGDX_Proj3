package me.BRZeph.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.text.DecimalFormat;

import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.TowerValues.TOWER_ATTACK_BAR_WIDTH;
import static me.BRZeph.utils.Constants.Constants.Paths.Values.UIValues.TEXTURE_MESSAGE_PADDING;

public class GlobalUtils {
    public static final DecimalFormat df = new DecimalFormat("#.##");

    public static void consoleLog(String log){
        System.out.println("*************************");
        System.out.println(log);
        System.out.println("*************************");
    }

    public static float getRandom(float min, float max){
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }
        return min + (float) Math.random() * (max - min);
    }

    public static void setToSpawn(){

    }

    public static void drawTowerAttackCooldown(ShapeRenderer shapeRenderer, float cooldownClock, float attackCooldown, int xPos, int yPos) {
        float progress = java.lang.Math.min(cooldownClock / attackCooldown, 1.0f);

        float xPosBar = ( 0.5f + xPos) * TILE_WIDTH - (float) TOWER_ATTACK_BAR_WIDTH /2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(xPosBar, yPos * TILE_HEIGHT, TOWER_ATTACK_BAR_WIDTH, 10);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(xPosBar, yPos * TILE_HEIGHT, TOWER_ATTACK_BAR_WIDTH * ( 1 -  progress ) , 10);

        shapeRenderer.end();
    }

    public static void drawTextWrapped(SpriteBatch spriteBatch, BitmapFont font, String message, float x, float y, float width) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, message);
        spriteBatch.begin();

        if (layout.width > width) {
            String[] words = message.split(" ");
            StringBuilder currentLine = new StringBuilder();
            float lineY = y;

            for (String word : words) {
                String testLine = currentLine.toString() + word + " ";
                layout.setText(font, testLine);

                if (layout.width > width) {
                    font.draw(spriteBatch, currentLine.toString(), x, lineY);
                    lineY -= font.getLineHeight();
                    currentLine.setLength(0);
                }
                currentLine.append(word).append(" ");
            }

            if (currentLine.length() > 0) {
                font.draw(spriteBatch, currentLine.toString(), x, lineY);
            }
        } else {
            font.draw(spriteBatch, message, x, y);
        }
        spriteBatch.end();
    }

    public static void drawCenteredText(SpriteBatch spriteBatch, BitmapFont font, String message, float x, float y) {
        spriteBatch.begin();
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, message);

        float centerX = x - layout.width / 2;
        float centerY = y + layout.height / 2;

        font.draw(spriteBatch, message, centerX, centerY);
        spriteBatch.end();
    }

    public static void drawTextureNextToMessage(SpriteBatch spriteBatch, BitmapFont font, Texture texture, float textureWidth, float textureHeight, float messageX, float messageY, String message, boolean isLeft) {
        float textureX;
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, message);
        float messageWidth = layout.width;

        if (isLeft) {
            textureX = messageX - messageWidth / 2 - TEXTURE_MESSAGE_PADDING - textureWidth;
        } else {
            textureX = messageX + messageWidth / 2 + TEXTURE_MESSAGE_PADDING;
        }

        spriteBatch.begin();
        spriteBatch.draw(texture, textureX, messageY - textureHeight/2, textureWidth, textureHeight);
        spriteBatch.end();
    }
}
