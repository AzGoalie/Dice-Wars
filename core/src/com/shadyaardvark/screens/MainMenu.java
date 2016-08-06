package com.shadyaardvark.screens;

import com.badlogic.gdx.Screen;
import com.shadyaardvark.DiceWars;

public class MainMenu implements Screen {
    private DiceWars game;

    public MainMenu(DiceWars diceWars) {
        this.game = diceWars;
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
