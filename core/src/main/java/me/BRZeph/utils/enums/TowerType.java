package me.BRZeph.utils.enums;

import com.badlogic.gdx.graphics.Texture;
import me.BRZeph.utils.Constants;

public enum TowerType {
    ARCHER(new Texture(Constants.Paths.TowersTexturesPath.ARCHER_TOWER));

    public final Texture texture;

    TowerType(Texture texture) {
        this.texture = texture;
    }
}
