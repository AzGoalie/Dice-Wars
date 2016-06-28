package com.shadyaardvark.map;

import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MapGenerator {
    private static final int GENERATING = -2;
    private static final float PERCENT_FILLED = 0.75f;
    private static final int RADIUS = 32;

    private int width;
    private int height;
    private HexagonMap map;

    public MapGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public HexagonMap generate(int numPlayers) {
        map = new HexagonMap(width, height, RADIUS);

        // Generate Map
        int numFilled = 0;
        AxialCoordinate currentPos = AxialCoordinate.offsetToAxial(width / 2, height / 2);
        while (numFilled <= width * height * PERCENT_FILLED) {
            Hexagon hexagon = map.getHexagon(currentPos);
            hexagon.setTeam(GENERATING);
            hexagon.setRegion(GENERATING);
            numFilled++;
            currentPos = getNextFree(hexagon);
        }

        // Generate Regions and teams
        Random random = new Random();
        int region = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                AxialCoordinate coordinate = AxialCoordinate.offsetToAxial(x, y);
                Hexagon hexagon = map.getHexagon(coordinate);
                if (hexagon.isValid() && hexagon.getRegion() == GENERATING) {
                    hexagon.setRegion(region);
                    hexagon.setTeam(region % numPlayers);
                    Array<Hexagon> adj = getAdjacent(hexagon, true);
                    for (Hexagon hex : adj) {
                        if (random.nextFloat() <= .75f && hex.getRegion() == GENERATING) {
                            hex.setRegion(region);
                            hex.setTeam(region % numPlayers);
                        }
                    }
                    region++;
                }
            }
        }
        return map;
    }

    private AxialCoordinate getNextFree(Hexagon hexagon) {
        Array<Hexagon> neighbors = map.getNeighborsOf(hexagon);
        neighbors.shuffle();

        // Check all neighbors for free hexagon
        for (Hexagon hex : neighbors) {
            if (!hex.isValid()) {
                return hex.getPosition();
            }
        }

        // No neighbors were free, get next free hexagon
        Set<Hexagon> hexagons = new HashSet<>();
        for (Hexagon hex : map.getHexagons()) {
            if (hex.isValid()) {
                hexagons.addAll(Arrays.asList(getAdjacent(hex, false).toArray()));
            }
        }

        return hexagons.iterator()
                .next()
                .getPosition();
    }

    private Array<Hexagon> getAdjacent(Hexagon hexagon, boolean filled) {
        Array<Hexagon> hexagons = map.getNeighborsOf(hexagon);
        Array<Hexagon> result = new Array<>();

        for (Hexagon hex : hexagons) {
            if (hex.isValid() == filled) {
                result.add(hex);
            }
        }

        result.shuffle();
        return result;
    }

    public void print() {
        int width = map.getWidth();
        int height = map.getHeight();

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                AxialCoordinate coordinate = AxialCoordinate.offsetToAxial(x, y);
                Hexagon hexagon = map.getHexagon(coordinate);

                if (hexagon.isValid()) {
                    System.out.print(hexagon.getRegion() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
