package com.shadyaardvark.hex.layouts;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.Layout;

public class HexagonLayout implements Layout {
    @Override
    public HashMap<Vector2, Hexagon> getMap(int width, int height) {
        HashMap<Vector2, Hexagon> map = new HashMap<>(1000, 0.75f);

        for (int q = -width; q <= width; q++) {
            int r1 = max(-width, -q - width);
            int r2 = min(width, -q + width);
            for (int r = r1; r <= r2; r++) {
                map.put(new Vector2(q, r), new Hexagon(q, r));
            }
        }

        return map;
    }
}
