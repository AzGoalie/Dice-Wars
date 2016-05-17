package com.shadyaardvark.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.shadyaardvark.DiceWars;
import com.shadyaardvark.Settings;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(Settings.WIDTH, Settings.HEIGHT);
        config.antialiasing = Settings.MSAA;

        return config;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new DiceWars();
    }
}