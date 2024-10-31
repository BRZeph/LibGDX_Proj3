package me.BRZeph.core.UI;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class StaticGridButtonUI { // Used for screen coordinates (static UI).
    private Rectangle bounds; // To check for clicks
    private Texture texture;
    private Runnable onClick; // Action to perform when the button is clicked
    private float x, y, width, height;

    public StaticGridButtonUI(float x, float y, float width, float height, Texture texture, Runnable onClick) {
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = texture;
        this.onClick = onClick;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        batch.end();
    }

    public void checkClick(float mouseX, float mouseY) {
        if (bounds.contains(mouseX, mouseY)) {
            onClick.run();
        }
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
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
