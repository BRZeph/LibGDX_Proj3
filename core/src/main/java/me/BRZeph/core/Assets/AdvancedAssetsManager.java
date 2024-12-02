package me.BRZeph.core.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import me.BRZeph.utils.GlobalUtils;

import java.util.HashMap;

import static me.BRZeph.utils.Constants.Paths.Animations.*;

public class AdvancedAssetsManager {

    private static final HashMap<String, Animation<TextureRegion>> animations = new HashMap<>();

    public static void loadAdvancedAssets(AssetManager assetManager) {
        loadAnimations(assetManager);
    }

/*
Position of the regions inside the TextureRegion.split():

[0][0]   [0][1]   [0][2]   [0][3]   ... [0][15]
[1][0]   [1][1]   [1][2]   [1][3]   ... [1][15]
[2][0]   [2][1]   [2][2]   [2][3]   ... [2][15]
...
[15][0]  [15][1]  [15][2]  [15][3]  ... [15][15]
 */

    private static void loadAnimations(AssetManager assetManager) { // Load all atlases before registering the frames.
        GlobalUtils.consoleLog("Loading advanced asset: animations ...");
        assetManager.load(PLAYER_WALK_ANIMATION, Texture.class);
        assetManager.load(MONSTER_WALK_ANIMATION, Texture.class);
        /*
        load all other atlases here.
         */

        assetManager.finishLoading();

        loadPlayerWalkAnimation(assetManager);
        loadMonstersWalkAnimation(assetManager);
        /*
        load all other animations here (remember to unload atlas).
         */

        GlobalUtils.consoleLog("Finished loading advanced asset: animations :D");
    }

    private static void loadMonstersWalkAnimation(AssetManager assetManager) {
        Texture zombieAtlas = assetManager.get(MONSTER_WALK_ANIMATION, Texture.class);

        TextureRegion[][] regions = TextureRegion.split(zombieAtlas, 64, 64);

        Array<TextureRegion> zombieWalkFrames = new Array<>();
        zombieWalkFrames.addAll(
            regions[0][0], // Frame 1
            regions[0][1], // Frame 2
            regions[0][2], // Frame 3
            regions[0][3], // Frame 4
            regions[0][4], // Frame 5
            regions[0][5]  // Frame 6
        );
        Animation<TextureRegion> zombieWalk = new Animation<>(
            ZOMBIE_WALK_FRAME_TIME, zombieWalkFrames, Animation.PlayMode.LOOP
        );
        animations.put(ZOMBIE_WALK_ANIMATION_NAME, zombieWalk);


        Array<TextureRegion> skeletonWalkFrames = new Array<>();
        skeletonWalkFrames.addAll(
            regions[1][0], // Frame 1
            regions[1][1], // Frame 2
            regions[1][2], // Frame 3
            regions[1][3], // Frame 4
            regions[1][4], // Frame 5
            regions[1][5]  // Frame 6
        );
        Animation<TextureRegion> skeletonWalk = new Animation<>(
            SKELETON_WALK_FRAME_TIME, skeletonWalkFrames, Animation.PlayMode.LOOP
        );
        animations.put(SKELETON_WALK_ANIMATION_NAME, skeletonWalk);
    }

    private static void loadPlayerWalkAnimation(AssetManager assetManager) {
        Texture atlasTexture = assetManager.get(PLAYER_WALK_ANIMATION, Texture.class);

        TextureRegion[][] regions = TextureRegion.split(atlasTexture, 64, 64);

        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.addAll(
            regions[0][0], // Frame 1
            regions[0][1], // Frame 2
            regions[0][2], // Frame 3
            regions[0][3], // Frame 4
            regions[0][4], // Frame 5
            regions[0][5], // Frame 6
            regions[0][6], // Frame 7
            regions[0][7], // Frame 8
            regions[0][8], // Frame 9
            regions[0][9], // Frame 10
            regions[0][10], // Frame 11
            regions[0][11], // Frame 12
            regions[0][12], // Frame 13
            regions[0][13], // Frame 14
            regions[0][14], // Frame 15
            regions[0][15]  // Frame 16
        );
        Animation<TextureRegion> walkAnimation = new Animation<>(
            PLAYER_WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP
        );
        animations.put(PLAYER_WALK_ANIMATION_NAME, walkAnimation);
    }

    public static Animation<TextureRegion> getAnimation(String animationName) {
        return animations.get(animationName);
    }
}
