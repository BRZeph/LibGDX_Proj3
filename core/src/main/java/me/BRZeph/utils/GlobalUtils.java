package me.BRZeph.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.text.DecimalFormat;

import static me.BRZeph.utils.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Paths.TileValues.TILE_WIDTH;
import static me.BRZeph.utils.Constants.Paths.Values.TowerValues.ArcherTowerValues.ARCHER_TOWER_ATTACK_BAR_WIDTH;

public class GlobalUtils {
    public static final DecimalFormat df = new DecimalFormat("#.##");

    public static void consoleLog(String log){
        System.out.println("*************************");
        System.out.println(log);
        System.out.println("*************************");
    }

    public static String adjustTextWidth(String message, float desiredWidth, BitmapFont font, GlyphLayout glyphLayout) {
        glyphLayout.setText(font, message);
        float textWidth = glyphLayout.width;

        // If the text is too wide, truncate it
        if (textWidth > desiredWidth) {
            String truncatedMessage = message;
            while (glyphLayout.width > desiredWidth && !truncatedMessage.isEmpty()) {
                truncatedMessage = truncatedMessage.substring(0, truncatedMessage.length() - 1);
                glyphLayout.setText(font, truncatedMessage);
            }
            return truncatedMessage; // Return the truncated message
        } else if (textWidth < desiredWidth) {
            // If the text is too short, adjust spacing between words
            StringBuilder spacedMessage = getStringBuilder(message, desiredWidth, textWidth);
            return spacedMessage.toString(); // Return the spaced message
        }

        return message; // Return the original message if it fits
    }

    private static StringBuilder getStringBuilder(String message, float desiredWidth, float textWidth) {
        String[] words = message.split(" ");
        float totalSpacing = desiredWidth - textWidth;
        float spacing = totalSpacing / (words.length - 1); // Space between words

        StringBuilder spacedMessage = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            spacedMessage.append(words[i]);
            if (i < words.length - 1) {
                // Add spacing only between words
                for (int j = 0; j < spacing; j++) {
                    spacedMessage.append(" "); // You can adjust this for finer control
                }
            }
        }
        return spacedMessage;
    }

    public static void drawTowerAttackCooldown(ShapeRenderer shapeRenderer, float cooldownClock, float attackCooldown, int xPos, int yPos) {
        float progress = java.lang.Math.min(cooldownClock / attackCooldown, 1.0f);

        float xPosBar = ( 0.5f + xPos) * TILE_WIDTH - (float) ARCHER_TOWER_ATTACK_BAR_WIDTH /2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(xPosBar, yPos * TILE_HEIGHT, ARCHER_TOWER_ATTACK_BAR_WIDTH, 10);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(xPosBar, yPos * TILE_HEIGHT, ARCHER_TOWER_ATTACK_BAR_WIDTH * ( 1 -  progress ) , 10);

        shapeRenderer.end();
    }
}
