package me.BRZeph.core.UI;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;

public class StaticGridButtonUI { // Used for screen coordinates (static UI).
    private Rectangle bounds; // To check for clicks
    private Texture texture;
    private Runnable onClick; // Action to perform when the button is clicked
    private Runnable onHover; // Action to perform when the button is clicked
    private float x, y, width, height;

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
    }

    public StaticGridButtonUI(Texture texture){
        this.texture = texture;
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.bounds = new Rectangle(0,0,100,100);
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

    public void checkHover(float mouseX, float mouseY){
        if (bounds.contains(mouseX, mouseY)){
            onHover.run();
        }
    }

    public void renderButtonInformationOnHover(SpriteBatch batch, BitmapFont font, String message){
        batch.begin();
        font.draw(batch, message, this.x + this.width, this.y + this.height/2);
        batch.end();
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
}
