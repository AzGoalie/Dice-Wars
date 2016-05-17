package com.shadyaardvark;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.shadyaardvark.map.HexData;
import com.shadyaardvark.map.MapGenerator;
import com.shadyaardvark.map.MapRenderer;
import com.shadyaardvark.map.OutlineRenderer;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {
    private int width;
    private int height;

    private MapRenderer mapRenderer;
    private OutlineRenderer outlines;

    private Map<Integer, Integer> regionMap;

    public GameBoard(int width, int height, int numPlayers) {
        this.width = width;
        this.height = height;
        regionMap = new HashMap<>();
        createMap(numPlayers);
    }

    public void createMap(int numPlayers) {
        MapGenerator generator = new MapGenerator(width, height, numPlayers);
        generator.print();

        regionMap.clear();
        generator.getGrid().getHexagons().forEach(hexagon -> {
            if (hexagon.getSatelliteData().isPresent()) {
                HexData data = (HexData) hexagon.getSatelliteData().get();
                regionMap.put(data.region, data.team);
            }
        });

        mapRenderer = new MapRenderer(generator.getGrid());
        outlines = new OutlineRenderer(generator.getGrid());
    }

    public void render(OrthographicCamera camera) {
        camera.update();
        mapRenderer.render(camera);
        outlines.render(camera);
    }

    public void dispose() {
        outlines.dispose();
        mapRenderer.dispose();
    }
}
