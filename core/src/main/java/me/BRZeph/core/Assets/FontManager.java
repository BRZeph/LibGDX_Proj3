package me.BRZeph.core.Assets;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import me.BRZeph.utils.GlobalUtils;

import java.util.HashMap;
import java.util.Objects;

public class FontManager {

    private static final HashMap<String, BitmapFont> fonts = new HashMap<>();

    public static void loadFonts() {
        GlobalUtils.consoleLog("[INFO]: Loading fonts...");
        fonts.put("Header_large", loadFont("assets/Fonts/Header/Header_large.fnt"));
        fonts.put("Header", loadFont("assets/Fonts/Header/Header.fnt"));
        fonts.put("Header_small", loadFont("assets/Fonts/Header/Header_small.fnt"));

        fonts.put("Body_large", loadFont("assets/Fonts/Body/Body_large.fnt"));
        fonts.put("Body", loadFont("assets/Fonts/Body/Body.fnt"));
        fonts.put("Body_small", loadFont("assets/Fonts/Body/Body_small.fnt"));

        fonts.put("Button", loadFont("assets/Fonts/Button/Button.fnt"));

        fonts.put("Tooltip", loadFont("assets/Fonts/Tooltip/Tooltip.fnt"));

        fonts.put("Chat", loadFont("assets/Fonts/Chat/Chat.fnt"));

        fonts.put("Notification", loadFont("assets/Fonts/Notification/Notification.fnt"));

        fonts.put("Subtitles", loadFont("assets/Fonts/Subtitles/Subtitles.fnt"));

        fonts.put("Placeholder", loadFont("assets/Fonts/Placeholder/Placeholder.fnt"));

        fonts.put("Informative_text", loadFont("assets/Fonts/Informative/Informative.fnt"));
        GlobalUtils.consoleLog("[INFO]: Finished loading fonts");
    }

    private static BitmapFont loadFont(String fontPath) {
        FileHandle fontFile = Gdx.files.internal(fontPath);
        return new BitmapFont(fontFile);
    }

    public static BitmapFont getFont(String category, String size) {
        String key;
        if (!Objects.equals(size, "")) {
            key = category + "_" + size;
        } else {
            key = category;
        }
        return fonts.get(key);
    }

    public static void disposeFonts() {
        GlobalUtils.consoleLog("[INFO]: Disposing fonts");
        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }
        fonts.clear();
    }
}
