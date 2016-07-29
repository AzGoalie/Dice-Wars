package com.shadyaardvark.hex.layouts;

import static com.badlogic.gdx.math.MathUtils.floor;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.Layout;

public class RectangleLayout implements Layout {

    @Override
    public HashMap<Vector2, Hexagon> getMap(int width, int height) {
        HashMap<Vector2, Hexagon> map = new HashMap<>();

        for (int r = 0; r < height; r++) {
            int rOffset = floor(r / 2);
            for (int q = -rOffset; q < width - rOffset; q++) {
                map.put(new Vector2(q, r), new Hexagon(q, r));
            }
        }

        return map;
    }
}
