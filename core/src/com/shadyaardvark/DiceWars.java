package com.shadyaardvark;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;
import static com.shadyaardvark.Settings.MAP_HEIGHT;
import static com.shadyaardvark.Settings.MAP_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.shadyaardvark.screens.MainMenu;

public class DiceWars extends Game {
    private AssetManager manager;
    private OrthographicCamera camera;

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("uiskin.json", Skin.class);
        manager.load("helvetica50.fnt", BitmapFont.class);

        manager.finishLoading();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                HEX_WIDTH * 2f * (MAP_WIDTH - .75f),
                HEX_HEIGHT * 1.5f * (MAP_HEIGHT + 3f));
        camera.position.y -= (HEX_HEIGHT * 1.5f * (MAP_HEIGHT + 3f)) - (HEX_HEIGHT * 1.5f * (MAP_HEIGHT + .5f));

        setScreen(new MainMenu(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
    }

    public AssetManager getAssetManager() {
        return manager;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
