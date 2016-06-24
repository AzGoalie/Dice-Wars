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

        map = new GameBoard(10, 10);
        map.createMap(5);
    }

    @Override
    public void render() {
        //        if (Gdx.input.justTouched()) {
        //            Hexagon hexagon = map.getHexagonFromScreen(Gdx.input.getX(), Gdx.input.getY(), camera);
        //            if (hexagon != null) {
        //                map.getNeighborRegionHexagons(hexagon)
        //                        .forEach(neighbor -> {
        //                            HexData neighborData = (HexData) neighbor.getSatelliteData()
        //                                    .get();
        //                            neighborData.highlight = !neighborData.highlight;
        //                        });
        //            }
        //        }

        Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.render(camera);
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
