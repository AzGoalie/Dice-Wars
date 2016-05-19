package com.shadyaardvark;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.shadyaardvark.map.HexData;
import com.shadyaardvark.map.MapGenerator;
import com.shadyaardvark.map.MapRenderer;
import com.shadyaardvark.map.OutlineRenderer;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameBoard {
    private int width;
    private int height;

    private MapRenderer mapRenderer;
    private OutlineRenderer outlines;

    private HexagonalGrid grid;
    private Map<Integer, Region> regionMap;

    public GameBoard(int width, int height, int numPlayers) {
        this.width = width;
        this.height = height;
        regionMap = new HashMap<>();
        createMap(numPlayers);
    }

    public void createMap(int numPlayers) {
        MapGenerator generator = new MapGenerator(width, height, numPlayers);
        generator.print();

        grid = generator.getGrid();
        regionMap.clear();

        // Add each hexagon to its region
        grid.getHexagons().forEach(hexagon -> {
            if (hexagon.getSatelliteData().isPresent()) {
                HexData data = (HexData) hexagon.getSatelliteData().get();
                regionMap.putIfAbsent(data.region, new Region());

                Region region = regionMap.get(data.region);
                region.region = data.region;
                region.regionHexagons.add(hexagon);
            }
        });

        // Add all neighbors to a region
        grid.getHexagons().forEach(hexagon -> {
            if (hexagon.getSatelliteData().isPresent()) {
                HexData data = (HexData) hexagon.getSatelliteData().get();
                Region region = regionMap.get(data.region);

                grid.getNeighborsOf(hexagon).forEach(neighbor -> {
                    if (neighbor.getSatelliteData().isPresent()) {
                        HexData neighborData = (HexData) neighbor.getSatelliteData().get();

                        if (data.region != neighborData.region) {
                            region.neighboringRegions.add(neighborData.region);
                            region.neighboringHexagons.addAll(regionMap.get(neighborData.region).regionHexagons);
                        }
                    }
                });
            }
        });

        mapRenderer = new MapRenderer(grid);
        outlines = new OutlineRenderer(grid);
    }

    public void render(OrthographicCamera camera) {
        camera.update();
        mapRenderer.render(camera);
        outlines.render(camera);
    }

    public Hexagon getHexagonFromScreen(int x, int y, OrthographicCamera camera) {
        Vector3 pos = camera.unproject(new Vector3(x, y, 0));
        if (grid.getByPixelCoordinate(pos.x, pos.y).isPresent()) {
            return grid.getByPixelCoordinate(pos.x, pos.y)
                    .get();
        } else {
            return null;
        }
    }

    public Array<Hexagon> getNeighborRegionHexagons(Hexagon hexagon) {
        Array<Hexagon> neighbors = new Array<>();

        if (hexagon.getSatelliteData().isPresent()) {
            HexData data = (HexData) hexagon.getSatelliteData().get();
            regionMap.get(data.region).neighboringHexagons.forEach(neighbors::add);
        }

        return neighbors;
    }

    public IntArray getNeighborRegions(Hexagon hexagon) {
        IntArray neighbors = new IntArray();

        if (hexagon.getSatelliteData().isPresent()) {
            HexData data = (HexData) hexagon.getSatelliteData().get();
            regionMap.get(data.region).neighboringRegions.forEach(neighbors::add);
        }

        return neighbors;
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
