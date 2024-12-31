package me.BRZeph.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.BRZeph.TowerDefenseGame;

import java.io.IOException;
import java.net.URL;
import java.io.File;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.stream.Collectors;

import static me.BRZeph.utils.Constants.*;
import static me.BRZeph.utils.GlobalUtils.consoleLog;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {

    public static void main(String[] args) {

        if (System.getProperty("isSubProcess") != null) {
            System.out.println("Subprocess detected...");
            launchGame();
        } else {
            System.out.println("Main process: Launching multiple windows...");
            for (int i = 0; i < WINDOW_NUMBER; i++) {
                final int windowIndex2 = i;
                Thread windowThread = new Thread(() -> launchWindowInNewJvm(windowIndex2));
                windowThread.start();
            }
        }
    }

    private static void launchWindowInNewJvm(int windowIndex) {
        try {
            String command = "java -DisSubProcess=true -jar C:/Users/Ricardo/Documents/GitHub/LibGDX_Proj3/out/artifacts/LibGDX_Proj3_jar/LibGDX_Proj3.jar";

            Process process = new ProcessBuilder(command.split(" "))
                .redirectErrorStream(true)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void launchGame() {
        consoleLog("Launching the game window");
        new Lwjgl3Application(new TowerDefenseGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Tower Defense");
        configuration.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
        configuration.useVsync(true);
        configuration.setForegroundFPS(60);
        configuration.setResizable(false);

        return configuration;
    }
}
