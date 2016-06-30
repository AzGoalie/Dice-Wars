package com.shadyaardvark;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.map.Hexagon;
import com.shadyaardvark.map.HexagonMap;
import com.shadyaardvark.map.MapGenerator;
import com.shadyaardvark.map.MapRenderer;
import com.shadyaardvark.map.OutlineRenderer;

public class GameBoard {
    private MapRenderer mapRenderer;
    private OutlineRenderer outlines;

    private Map<Integer, Region> regionMap;

    public GameBoard() {
        regionMap = new HashMap<>();
    }

    public void createMap(int width, int height, int numPlayers) {
        MapGenerator generator = new MapGenerator(width, height);

        HexagonMap map = generator.generate(numPlayers);

        regionMap.clear();

        // Add each hexagon to its region
        for (Hexagon hexagon : map.getHexagons()) {
            if (hexagon.isValid()) {
                Region region = new Region();
                region.region = hexagon.getRegion();
                region.regionHexagons.add(hexagon);

                regionMap.put(hexagon.getRegion(), region);
            }
        }

        // Add all neighbors to a region
        for (Hexagon hexagon : map.getHexagons()) {
            if (hexagon.isValid()) {
                Region region = regionMap.get(hexagon.getRegion());
                Array<Hexagon> neighbors = map.getNeighborsOf(hexagon);
                for (Hexagon neighbor : neighbors) {
                    if (neighbor.isValid() && hexagon.getRegion() != neighbor.getRegion()) {
                        region.neighboringRegions.add(neighbor.getRegion());
                        region.neighboringHexagons.add(neighbor);
                    }
                }
            }
        }

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

    private class Region {
        int region;

        Set<Integer> neighboringRegions = new HashSet<>();
        Set<Hexagon> neighboringHexagons = new HashSet<>();
        Set<Hexagon> regionHexagons = new HashSet<>();
    }
}
