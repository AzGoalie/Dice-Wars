package com.shadyaardvark.map;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;
import static com.shadyaardvark.Settings.MAP_HEIGHT;
import static com.shadyaardvark.Settings.MAP_WIDTH;
import static com.shadyaardvark.hex.Orientation.POINTY_TOP;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.MathUtils;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.HexagonMap;
import com.shadyaardvark.hex.HexagonMapBuilder;
import com.shadyaardvark.hex.layouts.RectangleLayout;

public class GameBoard {
    private static final int GENERATING = -2;
    private static final float PERCENT_FILLED = 0.75f;
    private Map<Integer, Region> regionMap;

    public GameBoard(int numPlayers) {
        regionMap = new HashMap<>();
        createMap(numPlayers);
    }

    public void createMap(int numPlayers) {
        HexagonMap map = new HexagonMapBuilder().setGridSize(MAP_WIDTH, MAP_HEIGHT)
                .setHexSize(HEX_WIDTH, HEX_HEIGHT)
                .setLayout(new RectangleLayout())
                .setOrientation(POINTY_TOP)
                .buid();
        regionMap.clear();

        int i = 1;
        for (Hexagon hexagon : map.getHexagons()) {
            Region region = new Region(i);
            region.addPoints(map.getHexCorners(hexagon));
            region.setTeam(MathUtils.random(0, 7));
            regionMap.put(i, region);
            i++;
        }

        //        // Generate Map
        //        int numFilled = 0;
        //        Vector2 currentPos = ConversionUtils.offsetToAxial(MAP_WIDTH / 2, MAP_HEIGHT / 2);
        //        while (numFilled <= width * height * PERCENT_FILLED) {
        //            Hexagon hexagon = map.getHexagon(currentPos);
        //            hexagon.setTeam(GENERATING);
        //            hexagon.setRegion(GENERATING);
        //            numFilled++;
        //            currentPos = getNextFree(hexagon);
        //        }
        //
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
        //
        //        hexWidth = map.getHexagons()
        //                .first()
        //                .getWidth();
        //        hexHeight = map.getHexagons()
        //                .first()
        //                .getHeight();
        //
        //        mapRenderer = new GameBoardRenderer(map);
        //        outlines = new OutlineRenderer(map);
    }

    public Map<Integer, Region> getRegionMap() {
        return regionMap;
    }
}
