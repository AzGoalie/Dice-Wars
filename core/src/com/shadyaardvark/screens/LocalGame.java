package com.shadyaardvark.screens;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.DiceWars;
import com.shadyaardvark.Settings;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.GameBoardRenderer;
import com.shadyaardvark.players.LocalPlayer;
import com.shadyaardvark.players.Player;

public class LocalGame implements Screen {
    private DiceWars game;
    private OrthographicCamera camera;
    private GameBoard board;
    private GameBoardRenderer boardRenderer;

    private Array<Player> players;

    public LocalGame(DiceWars diceWars) {
        this.game = diceWars;

        board = new GameBoard(5);
        boardRenderer = new GameBoardRenderer(board);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                HEX_WIDTH * 2 * (Settings.MAP_WIDTH + .5f),
                HEX_HEIGHT * 1.5f * (Settings.MAP_HEIGHT + .5f));

        players = new Array<>();
        players.add(new LocalPlayer(0, camera));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        players.get(board.getCurrentPlayer()).doTurn(board);

        camera.update();
        boardRenderer.render(camera);
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
        boardRenderer.dispose();
    }
}
