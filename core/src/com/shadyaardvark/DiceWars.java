package com.shadyaardvark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.shadyaardvark.screens.MainMenu;

import static com.shadyaardvark.Settings.*;

public class DiceWars extends Game {
    private AssetManager manager;
    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private Skin skin;
    private BitmapFont font;

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("uiskin.json", Skin.class);
        manager.load("helvetica50.fnt", BitmapFont.class);
        manager.finishLoading();

        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        skin = manager.get("uiskin.json");
        font = manager.get("helvetica50.fnt");
        font.getData().setScale(0.4f);

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
        shapeRenderer.dispose();
        spriteBatch.dispose();
    }

    public AssetManager getAssetManager() {
        return manager;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Skin getSkin() {
        return skin;
    }

    public BitmapFont getFont() {
        return font;
    }
}
