package com.shadyaardvark;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class DiceWars extends ApplicationAdapter {
    private OrthographicCamera camera;
    private GameBoard map;

    @Override
    public void create() {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        map = new GameBoard(10, 10, 5);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.render(camera);
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
