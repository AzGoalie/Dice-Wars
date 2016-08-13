package com.shadyaardvark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.shadyaardvark.DiceWars;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.GameBoardRenderer;
import com.shadyaardvark.map.Region;
import com.shadyaardvark.players.AI;
import com.shadyaardvark.players.LocalPlayer;
import com.shadyaardvark.players.Player;
import com.shadyaardvark.players.PlayerInput;

public class LocalGame implements Screen {
    private DiceWars game;

    private OrthographicCamera camera;
    private GameBoard board;
    private GameBoardRenderer boardRenderer;

    private Stage uiStage;
    private TextButton endTurn;
    private Array<Label> largestChainLabels;

    private Array<Player> players;

    public LocalGame(DiceWars diceWars, final GameBoard gameBoard) {
        this.game = diceWars;

        this.board = gameBoard;
        BitmapFont font = diceWars.getAssetManager().get("helvetica50.fnt");
        boardRenderer = new GameBoardRenderer(gameBoard, font);

        camera = diceWars.getCamera();

        players = new Array<>();
        players.add(new LocalPlayer(0));

        for (int i = 1; i < 5; i++) {
            players.add(new AI(i));
        }

        Skin skin = diceWars.getAssetManager().get("uiskin.json");
        uiStage = new Stage(new StretchViewport(camera.viewportWidth, camera.viewportHeight));

        Table table = new Table(skin);
        table.setFillParent(true);
        table.align(Align.bottom | Align.left);

        endTurn = new TextButton("End Turn", skin);
        endTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameBoard.endTurn();
            }
        });

        table.add(endTurn).align(Align.bottomLeft).padRight(10);

        largestChainLabels = new Array<>();
        VerticalGroup group = new VerticalGroup();
        for (int i = 0; i < 5; i++) {
            Label label = new Label("Player " + i + " largest chain: " + board.calcLongestChain(i), skin);
            largestChainLabels.add(label);
            group.addActor(label);
        }

        group.setScale(0.5f);
        table.add(group);
        uiStage.addActor(table);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(new PlayerInput(board, camera), uiStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        for (int i = 0; i < largestChainLabels.size; i++) {
            largestChainLabels.get(i).setText("Player " + i + " largest chain: " + board.calcLongestChain(i));
        }

        if (checkWinCondition()) {
            game.setScreen(new GameOver(game, boardRenderer, board.getRegionMap().get(1).getTeam()));
        }

        Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        boardRenderer.render(camera);

        players.get(board.getCurrentPlayer()).doTurn(board);

        endTurn.setVisible(board.getCurrentPlayer() == 0);
        uiStage.act();
        uiStage.draw();
    }

    private boolean checkWinCondition() {
        Region first = board.getRegionMap().get(1);
        for (Region region : board.getRegionMap().values()) {
            if (region.getTeam() != first.getTeam()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height);
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
        uiStage.dispose();
    }
}
