package com.shadyaardvark.screens;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;
import static com.shadyaardvark.Settings.MAP_HEIGHT;
import static com.shadyaardvark.Settings.MAP_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                HEX_WIDTH * 2f * (MAP_WIDTH - .75f),
                HEX_HEIGHT * 1.5f * (MAP_HEIGHT + 3f));
        camera.position.y -= (HEX_HEIGHT * 1.5f * (MAP_HEIGHT + 3f)) - (HEX_HEIGHT * 1.5f * (MAP_HEIGHT + .5f));

        gameBoard = new GameBoard(5);
        renderer = new GameBoardRenderer(gameBoard);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

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
                renderer = new GameBoardRenderer(gameBoard);
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

    }
}
