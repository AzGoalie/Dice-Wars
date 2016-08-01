package com.shadyaardvark.screens;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.shadyaardvark.Settings;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.GameBoardRenderer;

public class LocalGame implements Screen {
    private Game game;
    private OrthographicCamera camera;
    private GameBoard board;
    private GameBoardRenderer boardRenderer;

    public LocalGame(Game game) {
        this.game = game;

        //TODO: make number of players configurable
        board = new GameBoard(5);

        boardRenderer = new GameBoardRenderer(board.getRegionMap());

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                HEX_WIDTH * 2 * (Settings.MAP_WIDTH + .5f),
                HEX_HEIGHT * 1.5f * (Settings.MAP_HEIGHT + .5f));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            board = new GameBoard(5);
            boardRenderer = new GameBoardRenderer(board.getRegionMap());
        }

        camera.update();
        boardRenderer.render(camera);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,
                HEX_WIDTH * 1.75f * (Settings.MAP_WIDTH + .5f),
                HEX_HEIGHT * 1.5f * (Settings.MAP_HEIGHT + .5f));
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
