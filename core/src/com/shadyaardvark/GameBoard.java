package com.shadyaardvark;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ObjectSet;
import com.shadyaardvark.map.Hexagon;
import com.shadyaardvark.map.HexagonMap;
import com.shadyaardvark.map.MapGenerator;
import com.shadyaardvark.map.MapRenderer;
import com.shadyaardvark.map.OutlineRenderer;

public class GameBoard {
    private MapRenderer mapRenderer;
    private OutlineRenderer outlines;
    private IntMap<Region> regionMap;

    private float hexWidth;
    private float hexHeight;

    public GameBoard() {
        regionMap = new IntMap<>();
    }

    public void createMap(int width, int height, int numPlayers) {
        MapGenerator generator = new MapGenerator(width, height);

        HexagonMap map = generator.generate(numPlayers);

        regionMap.clear();

        for (Hexagon hexagon : map.getHexagons()) {
            if (hexagon.isValid()) {
                Region region = new Region();
                region.region = hexagon.getRegion();
                region.regionHexagons.add(hexagon);

                Array<Hexagon> neighbors = map.getNeighborsOf(hexagon);
                for (Hexagon neighbor : neighbors) {
                    if (neighbor.isValid() && hexagon.getRegion() != neighbor.getRegion()) {
                        region.neighboringRegions.add(neighbor.getRegion());
                    }
                }

                regionMap.put(hexagon.getRegion(), region);
            }
        }

        hexWidth = map.getHexagons().first().getWidth();
        hexHeight = map.getHexagons().first().getHeight();

        mapRenderer = new MapRenderer(map);
        outlines = new OutlineRenderer(map);
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

    public float getHexWidth() {
        return hexWidth;
    }

    public float getHexHeight() {
        return hexHeight;
    }

    private class Region {
        int region;

        IntSet neighboringRegions = new IntSet();
        ObjectSet<Hexagon> regionHexagons = new ObjectSet<>();
    }
}
