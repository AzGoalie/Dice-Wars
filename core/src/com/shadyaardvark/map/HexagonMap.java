package com.shadyaardvark.map;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class HexagonMap {
    private static final int[][] NEIGHBORS = {{1, 0}, {1, -1}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}};

    private Map<AxialCoordinate, Hexagon> map;
    private int width;
    private int height;
    private int hexSize;

    public HexagonMap(int width, int height, int hexSize) {
        this.width = width;
        this.height = height;
        this.hexSize = hexSize;

        map = new HashMap<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Hexagon hexagon = new Hexagon(x, y, hexSize);
                map.put(AxialCoordinate.offsetToAxial(x, y), hexagon);
            }
        }
    }

    public Hexagon getHexagon(AxialCoordinate pos) {
        return map.get(pos);
    }

    public Array<Hexagon> getHexagons() {
        Array<Hexagon> hexagons = new Array<>();

        for (Hexagon hexagon : map.values()) {
            hexagons.add(hexagon);
        }

        return hexagons;
    }

    public Array<Hexagon> getNeighborsOf(Hexagon hexagon) {
        Array<Hexagon> neighbors = new Array<>();
        for (int[] neighbor : NEIGHBORS) {
            Hexagon hex;
            float q = hexagon.getQ() + neighbor[0];
            float r = hexagon.getR() + neighbor[1];
            AxialCoordinate neighborCoordinate = new AxialCoordinate(q, r);
            if (map.keySet()
                    .contains(neighborCoordinate)) {
                hex = map.get(neighborCoordinate);
                neighbors.add(hex);
            }
        }
        return neighbors;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHexSize() {
        return hexSize;
    }
}
