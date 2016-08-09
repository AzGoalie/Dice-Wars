package com.shadyaardvark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.shadyaardvark.DiceWars;
import com.shadyaardvark.map.GameBoardRenderer;

public class GameOver implements Screen {
    private GameBoardRenderer renderer;
    private Camera camera;
    private Stage stage;

    public GameOver(final DiceWars diceWars, GameBoardRenderer renderer, Camera camera, int winner) {
        this.renderer = renderer;
        this.camera = camera;

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        Dialog dialog = new Dialog("", skin);
        dialog.setMovable(false);

        Label winnerLabel = new Label("Player " + winner + " Won!", skin);
        dialog.getContentTable().add(winnerLabel).row();

        TextButton newGame = new TextButton("New Game", skin);
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diceWars.setScreen(new MainMenu(diceWars));
            }
        });
        dialog.getContentTable().add(newGame).row();

        dialog.show(stage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
