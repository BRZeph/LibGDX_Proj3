package me.BRZeph.core;


import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.entities.monster.Monster;

import static me.BRZeph.utils.Constants.Values.TowerValues.TARGET_HIT_THRESHOLD;
import static me.BRZeph.utils.GlobalUtils.consoleLog;


public class Projectile {
    private float x, y;
    private float speed;
    private float damage;
    private Monster target;
    private Texture texture;
    private boolean reachedTarget;

    public Projectile(float startX, float startY, float speed, float damage, Monster target, Texture texture) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.damage = damage;
        this.target = target;
        this.texture = texture;
        reachedTarget = false;
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
//        if (target != null && !hasReachedTarget()) {
        if (target != null && !hasReachedTarget()) {
            // Calculate direction
            float targetX = target.getX();
            float targetY = target.getY();

            float dx = targetX - x;
            float dy = targetY - y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            // Normalize direction
            if (distance > 0) {
                float directionX = dx / distance;
                float directionY = dy / distance;

                // Move towards the target based on speed and delta time
                x += directionX * speed * delta;
                y += directionY * speed * delta;

                // Check if the projectile has reached the target
                if (distance < TARGET_HIT_THRESHOLD) {
                    reachedTarget = true; // The projectile has reached the target
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
}
