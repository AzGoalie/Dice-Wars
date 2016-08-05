package com.shadyaardvark.map;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;
import static com.shadyaardvark.Settings.MAP_HEIGHT;
import static com.shadyaardvark.Settings.MAP_WIDTH;
import static com.shadyaardvark.hex.Orientation.POINTY_TOP;

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
    private IntMap<Region> regionMap = new IntMap<>();
    private Array<Hexagon> available = new Array<>();
    private Array<Hexagon> valid = new Array<>();
    private HexagonMap hexagonMap;

    public GameBoard(int numPlayers) {
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

        // Generate Map
        int numFilled = 0;
        Vector2 currentPos = ConversionUtils.offsetToAxial(MAP_WIDTH / 2, MAP_HEIGHT / 2);
        while (numFilled <= MAP_WIDTH * MAP_HEIGHT * PERCENT_FILLED) {
            Hexagon hexagon = hexagonMap.getHexagon(currentPos);
            available.add(hexagon);

            numFilled++;
            currentPos = getNextFree(hexagon);
        }

        int regionId = 0;
        Array<Hexagon> tmp = new Array<>(available);
        tmp.shuffle();
        for (Hexagon hexagon : tmp) {
            if (!available.contains(hexagon, false)) {
                continue;
            }
            Region region = new Region();
            region.setRegion(regionId);
            region.setTeam(regionId % numPlayers);
            region.addHexagon(hexagon);

            Array<Hexagon> neighbors = hexagon.getNeighbors();
            for (Hexagon neighbor : neighbors) {
                if (available.contains(neighbor, false)) {
                    region.addHexagon(neighbor);
                    available.removeValue(neighbor, false);
                }
            }

            available.removeValue(hexagon, false);
            regionMap.put(regionId++, region);
        }
    }

    private Vector2 getNextFree(Hexagon hexagon) {
        valid.clear();

        // Check all neighbors for free hexagon
        for (Hexagon hex : hexagon.getNeighbors()) {
            if (hexagonMap.getHexagons().contains(hex, false)
                    && !available.contains(hex, false)) {
                valid.add(hex);
            }
        }

        if (valid.size > 0) {
            return valid.random().getAxialPos();
        }

        // No neighbors were free, get next free hexagon
        valid.clear();
        for (Hexagon hex : available) {
            for (Hexagon n : hex.getNeighbors()) {
                if (hexagonMap.getHexagons().contains(n, false)) {
                    valid.add(n);
                }
            }
        }

        return valid.random().getAxialPos();
    }

    IntMap<Region> getRegionMap() {
        return regionMap;
    }

    HexagonMap getHexagonMap() {
        return hexagonMap;
    }
}
