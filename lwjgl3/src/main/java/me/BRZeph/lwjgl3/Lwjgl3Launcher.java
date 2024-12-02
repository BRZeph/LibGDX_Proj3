package me.BRZeph.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.BRZeph.Main;
import me.BRZeph.utils.Constants;

import static me.BRZeph.utils.Constants.SCREEN_HEIGHT;
import static me.BRZeph.utils.Constants.SCREEN_WIDTH;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static void createApplication() {
        new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }


    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("LibGDX_Proj3");
        configuration.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
        configuration.useVsync(true);
        configuration.setForegroundFPS(60);
        configuration.setResizable(false);
        return configuration;
    }
}
