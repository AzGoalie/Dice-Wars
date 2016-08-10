package com.shadyaardvark.hex.layouts;

import com.badlogic.gdx.math.Vector2;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.Layout;

import java.util.HashMap;

public class TriangleLayout implements Layout {
    @Override
    public HashMap<Vector2, Hexagon> getMap(int width, int height) {
        HashMap<Vector2, Hexagon> map = new HashMap<>();

        for (int q = 0; q <= width; q++) {
            for (int r = 0; r <= width - q; r++) {
                map.put(new Vector2(q, r), new Hexagon(q, r));
            }
        }

        return map;
    }
}
