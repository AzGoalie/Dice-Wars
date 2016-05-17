package com.shadyaardvark.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shadyaardvark.DiceWars;
import com.shadyaardvark.Settings;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Settings.WIDTH;
        config.height = Settings.HEIGHT;
        config.useHDPI = true;
        config.samples = Settings.SAMPLES;
        config.foregroundFPS = Settings.FPS;
        new LwjglApplication(new DiceWars(), config);
    }
}
