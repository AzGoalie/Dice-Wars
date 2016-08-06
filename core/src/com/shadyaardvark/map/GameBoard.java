package com.shadyaardvark.map;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;
import static com.shadyaardvark.Settings.MAP_HEIGHT;
import static com.shadyaardvark.Settings.MAP_WIDTH;
import static com.shadyaardvark.hex.Orientation.POINTY_TOP;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.shadyaardvark.hex.ConversionUtils;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.HexagonMap;
import com.shadyaardvark.hex.HexagonMapBuilder;
import com.shadyaardvark.hex.layouts.RectangleLayout;

public class GameBoard {
    private static final float PERCENT_FILLED = 0.75f;
    private IntMap<Region> regionMap;
    private HexagonMap hexagonMap;

    public GameBoard(int numPlayers) {
        regionMap = new IntMap<>();

        createMap(numPlayers);
    }

    private void createMap(int numPlayers) {
        hexagonMap = new HexagonMapBuilder()
                .setGridSize(MAP_WIDTH, MAP_HEIGHT)
                .setHexSize(HEX_WIDTH, HEX_HEIGHT)
                .setLayout(new RectangleLayout())
                .setOrientation(POINTY_TOP)
                .buid();
        regionMap.clear();

        Array<Hexagon> hexagons = hexagonMap.getHexagons();
        Array<Hexagon> available = new Array<>();

        // Generate Map
        int numFilled = 0;
        Vector2 currentPos = ConversionUtils.offsetToAxial(MAP_WIDTH / 2, MAP_HEIGHT / 2);
        while (numFilled <= MAP_WIDTH * MAP_HEIGHT * PERCENT_FILLED) {
            Hexagon hexagon = hexagonMap.getHexagon(currentPos);
            if (!available.contains(hexagon, false)) {
                available.add(hexagon);
                numFilled++;
            }

            Array<Hexagon> valid = new Array<>();
            for (Hexagon neighbor : hexagon.getNeighbors()) {
                if (hexagons.contains(neighbor, false)) {
                    valid.add(neighbor);
                }
            }

            currentPos = valid.random().getAxialPos();
        }

        // Assign regions
        int regionId = 1;
        while (available.size > 0) {
            Hexagon hexagon = available.random();
            available.removeValue(hexagon, false);

            Region region = new Region(regionId);
            region.setTeam(regionId % numPlayers);
            region.addHexagon(hexagon);

            Array<Hexagon> neighbors = hexagon.getNeighbors();
            neighbors.shuffle();
            for (Hexagon neighbor : neighbors) {
                if (available.contains(neighbor, false) && MathUtils.random() >= .75f) {
                    region.addHexagon(neighbor);
                    available.removeValue(neighbor, false);
                }
            }

            regionMap.put(regionId++, region);
        }
    }

    IntMap<Region> getRegionMap() {
        return regionMap;
    }

    HexagonMap getHexagonMap() {
        return hexagonMap;
    }
}
