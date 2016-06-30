package com.shadyaardvark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class MainMenu implements Screen {
    private Game game;

    public MainMenu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.setScreen(new LocalGame(game));
    }

    @Override
    public void render(float delta) {

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
