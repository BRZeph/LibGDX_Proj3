package me.BRZeph.entities;


import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.entities.monster.Monster;

public class Projectile {
    private float x, y;
    private float speed;
    private float damage;
    private Monster target;
    private Texture texture;
    private boolean reachedTarget;
    private boolean originBounce;

    public Projectile(float startX, float startY, float speed, float damage, Monster target, Texture texture) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.damage = damage;
        this.target = target;
        this.texture = texture;
        this.reachedTarget = false;
        this.originBounce = false;
    }

    @Override
    public String toString() {
        String targetInfo = (target != null) ? "Target: [X: " + target.getX() + ", Y: " + target.getY() + ", HP: " + target.getCurrentHealth() + "]" : "No Target";
        return "Projectile{" +
            "Position=(" + x + ", " + y + ")" +
            ", Speed=" + speed +
            ", Damage=" + damage +
            ", " + targetInfo +
            ", ReachedTarget=" + reachedTarget +
            '}';
    }

    public void update(float delta) {
        if (target != null && !hasReachedTarget()) {
            float targetX = target.getX();
            float targetY = target.getY();

            float dx = targetX - x;
            float dy = targetY - y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance > 0) {
                float directionX = dx / distance;
                float directionY = dy / distance;

                float moveDistance = speed * delta;

                if (moveDistance >= distance) {
                    reachedTarget = true;
                    x = targetX;
                    y = targetY;
                } else {
                    x += directionX * moveDistance;
                    y += directionY * moveDistance;
                }
            }
        }
    }

    public boolean hasReachedTarget() {
        return reachedTarget;
    }

    public float getDamage() {
        return damage;
    }

    public Monster getTarget() {
        return target;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setOriginBounce(boolean originBounce) {
        this.originBounce = originBounce;
    }

    public boolean isOriginBounce() {
        return originBounce;
    }
}
