package com.shadyaardvark.map;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class HexagonMap {
    private static final int[][] NEIGHBORS = {{1, 0}, {1, -1}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}};

    private ObjectMap<AxialCoordinate, Hexagon> map;
    private int width;
    private int height;
    private int hexRadius;

    public HexagonMap(int width, int height, int hexRadius) {
        this.width = width;
        this.height = height;
        this.hexRadius = hexRadius;

        map = new ObjectMap<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Hexagon hexagon = new Hexagon(x, y, hexRadius);
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
            int q = hexagon.getQ() + neighbor[0];
            int r = hexagon.getR() + neighbor[1];
            AxialCoordinate neighborCoordinate = new AxialCoordinate(q, r);
            if (map.containsKey(neighborCoordinate)) {
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

    public int getHexRadius() {
        return hexRadius;
    }
}
