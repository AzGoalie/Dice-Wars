package com.shadyaardvark.map;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class HexagonMap {
    private static final int[][] NEIGHBORS = {{1, 0}, {1, -1}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}};

    private Map<Vector2, Hexagon> map;
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
                Vector2 position = new Vector2(x, y);
                Hexagon hexagon = new Hexagon(x, y, hexSize);
                map.put(position, hexagon);
            }
        }
    }

    public Hexagon getHexagon(Vector2 pos) {
        return map.get(pos);
    }

    public Hexagon getHexagon(int x, int y) {
        return map.get(new Vector2(x, y));
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
            Hexagon retHex;
            int x = hexagon.getX() + neighbor[0];
            int y = hexagon.getY() + neighbor[1];
            Vector2 position = new Vector2(x, y);
            if (map.keySet()
                    .contains(position)) {
                neighbors.add(map.get(position));
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
