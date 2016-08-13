package com.shadyaardvark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.shadyaardvark.DiceWars;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.GameBoardRenderer;

public class MainMenu implements Screen {
    private DiceWars game;
    private Stage stage;

    private GameBoard gameBoard;
    private GameBoardRenderer renderer;
    private OrthographicCamera camera;

    public MainMenu(DiceWars diceWars) {
        this.game = diceWars;

        camera = diceWars.getCamera();

        gameBoard = new GameBoard(5);
        final BitmapFont font = diceWars.getAssetManager().get("helvetica50.fnt");
        renderer = new GameBoardRenderer(gameBoard, font);

        Skin skin = diceWars.getAssetManager().get("uiskin.json");
        stage = new Stage(new StretchViewport(camera.viewportWidth, camera.viewportHeight));

        Table table = new Table(skin);
        table.setFillParent(true);
        table.align(Align.center | Align.bottom);

        TextButton start = new TextButton("Play", skin);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LocalGame(game, gameBoard));
            }
        });

        TextButton newMap = new TextButton("New Map", skin);
        newMap.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameBoard = new GameBoard(5);
                renderer = new GameBoardRenderer(gameBoard, font);
            }
        });

        table.add(start);
        table.add(newMap);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.render(camera);

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        stage.dispose();
    }
}
