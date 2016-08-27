package com.shadyaardvark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.shadyaardvark.DiceWars;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.GameBoardRenderer;
import com.shadyaardvark.map.HexMesh;
import com.shadyaardvark.map.Region;
import com.shadyaardvark.players.AI;
import com.shadyaardvark.players.LocalPlayer;
import com.shadyaardvark.players.Player;
import com.shadyaardvark.players.PlayerInput;

import static com.shadyaardvark.Settings.HEIGHT_PERCENT;

public class LocalGame implements Screen {
    private DiceWars game;

    private OrthographicCamera camera;
    private GameBoard board;
    private GameBoardRenderer boardRenderer;

    private Stage uiStage;
    private TextButton endTurn;

    private Array<Player> players;
    private Array<Hexagon> chains;
    private BitmapFont font;

    public LocalGame(DiceWars diceWars, final GameBoard gameBoard) {
        this.game = diceWars;
        this.board = gameBoard;
        this.font = diceWars.getFont();

        boardRenderer = new GameBoardRenderer(gameBoard, font, game.getShapeRenderer(), game.getSpriteBatch());

        camera = diceWars.getCamera();

        players = new Array<>();
        players.add(new LocalPlayer(0));

        for (int i = 1; i < 5; i++) {
            players.add(new AI(i));
        }

        chains = new Array<>();
        for (int i = 0; i < 5; i++) {
            chains.add(new Hexagon(i + 5, -2));
        }

        Skin skin = diceWars.getAssetManager().get("uiskin.json");
        uiStage = new Stage(new StretchViewport(camera.viewportWidth, camera.viewportHeight));

        Table table = new Table(skin);
        table.setFillParent(true);
        table.align(Align.bottomLeft);

        endTurn = new TextButton("End Turn", skin);
        endTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameBoard.endTurn();
            }
        });

        table.add(endTurn).height(Value.percentHeight(HEIGHT_PERCENT, table))
                .bottom().padRight(5);

        Label chainLabel = new Label("Longest \nChains", skin);
        table.add(chainLabel);

        uiStage.addActor(table);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(new PlayerInput(board, camera), uiStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (checkWinCondition()) {
            game.setScreen(new GameOver(game, boardRenderer, board.getRegionMap().get(1).getTeam()));
        }

        Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        boardRenderer.render(camera);

        players.get(board.getCurrentPlayer()).doTurn(board);

        endTurn.setVisible(board.getCurrentPlayer() == 0);

        game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < chains.size; i++) {
            Hexagon hexagon = chains.get(i);
            Array<Vector2> points = new Array<>();
            points.add(board.getHexagonMap().getHexCenter(hexagon));
            points.addAll(board.getHexagonMap().getHexCorners(hexagon));

            game.getShapeRenderer().setColor(HexMesh.COLORS[i]);
            HexMesh.draw(game.getShapeRenderer(), points);
        }
        game.getShapeRenderer().end();

        game.getSpriteBatch().begin();
        for (int i = 0; i < chains.size; i++) {
            Vector2 p = board.getHexagonMap().getHexCenter(chains.get(i));
            int chain = board.calcLongestChain(i);
            font.draw(game.getSpriteBatch(), String.valueOf(chain), p.x - 5, p.y + 8);
        }
        game.getSpriteBatch().end();

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
        uiStage.dispose();
    }
}
