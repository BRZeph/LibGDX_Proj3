package me.BRZeph.core.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class WorldGridButtonUI { // Used for world coordinates (non-static UI).
    private Rectangle bounds; // To check for clicks
    private Texture texture;
    private Runnable onClick; // Action to perform when the button is clicked

    public WorldGridButtonUI(float x, float y, float width, float height, Texture texture, Runnable onClick) {
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = texture;
        this.onClick = onClick;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void checkClick(float mouseX, float mouseY) {
        if (bounds.contains(mouseX, mouseY)) {
            onClick.run(); // Execute the action if clicked
        }
    }

    public Rectangle getBounds() {
        return bounds; // For further manipulations if needed
    }
}
