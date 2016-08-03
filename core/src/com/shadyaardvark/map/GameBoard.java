package com.shadyaardvark.map;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;
import static com.shadyaardvark.Settings.MAP_HEIGHT;
import static com.shadyaardvark.Settings.MAP_WIDTH;
import static com.shadyaardvark.hex.Orientation.POINTY_TOP;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.hex.ConversionUtils;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.HexagonMap;
import com.shadyaardvark.hex.HexagonMapBuilder;
import com.shadyaardvark.hex.layouts.RectangleLayout;

public class GameBoard {
    private static final int GENERATING = -1;
    private static final float PERCENT_FILLED = 0.75f;
    private Map<Integer, Region> regionMap;

    private HexagonMap hexagonMap;
    private Array<Hexagon> tmpMap;

    public GameBoard(int numPlayers) {
        regionMap = new HashMap<>();
        tmpMap = new Array<>();
        createMap(numPlayers);
    }

    private void createMap(int numPlayers) {
        hexagonMap = new HexagonMapBuilder().setGridSize(MAP_WIDTH, MAP_HEIGHT)
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
            tmpMap.add(hexagon);

            numFilled++;
            currentPos = getNextFree(hexagon);
        }

        int regionId = 0;
        tmpMap.shuffle();
        for (Hexagon hexagon : tmpMap) {
            Region region = new Region();
            region.setRegion(regionId);
            region.setTeam(regionId % numPlayers);
            region.addHexagon(hexagon);

//            Array<Hexagon> neighbors = hexagon.getNeighbors();
//            for (Hexagon neighbor : neighbors) {
//                if (tmpMap.contains(neighbor, false)) {
//                    if (MathUtils.random() <= .75f) {
//                        region.addHexagon(neighbor);
//                        tmpMap.removeValue(neighbor, false);
//                    }
//                }
//            }
//            tmpMap.removeValue(hexagon, true);
            regionMap.put(regionId++, region);
        }

        //        // Generate Regions and teams
        //        int region = 0;
        //        for (int x = 0; x < width; x++) {
        //            for (int y = 0; y < height; y++) {
        //                AxialCoordinate coordinate = AxialCoordinate.offsetToAxial(x, y);
        //                Hexagon hexagon = map.getHexagon(coordinate);
        //                if (hexagon.isValid() && hexagon.getRegion() == GENERATING) {
        //                    hexagon.setRegion(region);
        //                    hexagon.setTeam(region % numPlayers);
        //                    Array<Hexagon> adj = getAdjacent(hexagon, true);
        //                    for (Hexagon hex : adj) {
        //                        if (MathUtils.random() <= .75f && hex.getRegion() == GENERATING) {
        //                            hex.setRegion(region);
        //                            hex.setTeam(region % numPlayers);
        //                        }
        //                    }
        //                    region++;
        //                }
        //            }
        //        }
        //
        //        for (Hexagon hexagon : map.getHexagons()) {
        //            if (hexagon.isValid()) {
        //                Region region = new Region();
        //                region.region = hexagon.getRegion();
        //                region.regionHexagons.add(hexagon);
        //
        //                Array<Hexagon> neighbors = map.getNeighborsOf(hexagon);
        //                for (Hexagon neighbor : neighbors) {
        //                    if (neighbor.isValid() && hexagon.getRegion() != neighbor.getRegion()) {
        //                        region.neighboringRegions.add(neighbor.getRegion());
        //                    }
        //                }
        //
        //                regionMap.put(hexagon.getRegion(), region);
        //            }
        //        }
    }

    private Vector2 getNextFree(Hexagon hexagon) {
        Array<Hexagon> neighbors = hexagon.getNeighbors();
        neighbors.shuffle();

        // Check all neighbors for free hexagon
        for (Hexagon hex : neighbors) {
            if (hexagonMap.getHexagons().contains(hex, false) && !tmpMap.contains(hex, false)) {
                return new Vector2(hex.q, hex.r);
            }
        }

        // No neighbors were free, get next free hexagon
        neighbors.clear();
        for (Hexagon hex : tmpMap) {
            for (Hexagon n : hex.getNeighbors()) {
                if (hexagonMap.getHexagons().contains(n, false)) {
                    neighbors.add(n);
                }
            }
        }
        neighbors.shuffle();

        return new Vector2(neighbors.first().q, neighbors.first().r);
    }

    Map<Integer, Region> getRegionMap() {
        return regionMap;
    }

    HexagonMap getHexagonMap() {
        return hexagonMap;
    }
}
