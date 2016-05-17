package com.shadyaardvark.map;

import org.codetome.hexameter.core.api.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.shadyaardvark.map.Util.convertToAxial;

public class MapGenerator {
    private static final float PERCENT_FILLED = 0.75f;
    private static final int RADIUS = 32;
    private HexagonalGrid grid;

    public MapGenerator(int width, int height, int numPlayers) {
        grid = new HexagonalGridBuilder()
                .setOrientation(HexagonOrientation.POINTY_TOP)
                .setGridLayout(HexagonalGridLayout.RECTANGULAR)
                .setGridWidth(width)
                .setGridHeight(height)
                .setRadius(RADIUS)
                .build();

        generate(numPlayers);
    }

    public void generate(int numPlayers) {
        int width = grid.getGridData().getGridWidth();
        int height = grid.getGridData().getGridHeight();

        // Generate Map
        int numFilled = 0;
        AxialCoordinate currentPos = convertToAxial(width / 2, height / 2);
        while (numFilled <= width * height * PERCENT_FILLED) {
            grid.getByAxialCoordinate(currentPos).get().setSatelliteData(new HexData());
            numFilled++;
            currentPos = getNextFree(grid.getByAxialCoordinate(currentPos).get());
        }

        // Generate Regions and teams
        Random random = new Random();
        int region = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Hexagon hexagon = grid.getByAxialCoordinate(convertToAxial(x, y)).get();
                if (hexagon.getSatelliteData().isPresent()
                        && ((HexData) hexagon.getSatelliteData().get()).region == HexData.INVALID) {
                    HexData data = (HexData) hexagon.getSatelliteData().get();
                    data.region = region;
                    data.team = region % numPlayers;
                    List<Hexagon> adj = getAdjacent(hexagon, true);
                    for (Hexagon hex : adj) {
                        HexData adjData = (HexData) hex.getSatelliteData().get();
                        if (random.nextFloat() <= .75f
                                && adjData.region == HexData.INVALID) {
                            adjData.region = region;
                            adjData.team = region % numPlayers;
                        }
                    }
                    region++;
                }
            }
        }
    }

    private AxialCoordinate getNextFree(Hexagon hexagon) {
        List<Hexagon> neighbors = new ArrayList<>(grid.getNeighborsOf(hexagon));
        Collections.shuffle(neighbors);

        // Check all neighbors for free hexagon
        for (Hexagon hex : neighbors) {
            if (!hex.getSatelliteData().isPresent()) {
                return hex.getAxialCoordinate();
            }
        }

        // No neighbors were free, get next free hexagon
        Set<Hexagon> hexagons = new HashSet<>();
        grid.getHexagons().forEach(hex -> {
            if (!getAdjacent(hex, false).isEmpty()) {
                hexagons.addAll(getAdjacent(hex, false));
            }
        });

        return hexagons.iterator().next().getAxialCoordinate();
    }

    private List<Hexagon> getAdjacent(Hexagon hexagon, boolean filled) {
        List<Hexagon> hexagons = new ArrayList<>(grid.getNeighborsOf(hexagon));
        List<Hexagon> result = hexagons.stream().filter(hex ->
                (hex.getSatelliteData().isPresent() == filled))
                .collect(Collectors.toList());

        Collections.shuffle(result);
        return result;
    }

    public void print() {
        int width = grid.getGridData().getGridWidth();
        int height = grid.getGridData().getGridHeight();

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Hexagon hexagon = grid.getByAxialCoordinate(convertToAxial(x, y)).get();

                if (hexagon.getSatelliteData().isPresent()) {
                    HexData data = (HexData) hexagon.getSatelliteData().get();
                    System.out.print(data.region + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public HexagonalGrid getGrid() {
        return grid;
    }
}
