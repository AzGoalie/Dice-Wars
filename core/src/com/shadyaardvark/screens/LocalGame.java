package com.shadyaardvark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.shadyaardvark.DiceWars;
import com.shadyaardvark.Settings;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.GameBoardRenderer;
import com.shadyaardvark.map.Region;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;

public class LocalGame implements Screen {
    private DiceWars game;
    private OrthographicCamera camera;
    private GameBoard board;
    private GameBoardRenderer boardRenderer;

    private Region currentRegion;

    public LocalGame(DiceWars diceWars) {
        this.game = diceWars;

        //TODO: make number of players configurable
        board = new GameBoard(5);

        boardRenderer = new GameBoardRenderer(board);

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
            Vector3 point = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Region clicked = board.getRegion(point.x, point.y);
            if (clicked != null) {
                if (currentRegion == null) {
                    currentRegion = clicked;
                    board.highlightNeighbors(currentRegion, true);
                } else if (clicked.getNeighboringRegions()
                        .contains(currentRegion.getId())) {
                    board.highlightNeighbors(currentRegion, false);
                    clicked.setTeam(currentRegion.getTeam());
                    currentRegion = null;
                } else {
                    board.highlightNeighbors(currentRegion, false);
                    currentRegion = null;
                }
            }
        }

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
