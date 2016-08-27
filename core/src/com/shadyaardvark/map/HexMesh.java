package com.shadyaardvark.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class HexMesh {
    public static final Color[] COLORS =
            {Color.valueOf("AA2FFF"), Color.valueOf("01B0F0"), Color.valueOf("97FF7F"), Color.valueOf("E8A241"), Color.valueOf("FF5757")};
    private static final short[] INDEX = {0, 1, 2, 0, 2, 3, 0, 3, 4, 0, 4, 5, 0, 5, 6, 0, 6, 1};

    public static void draw(ShapeRenderer shapeRenderer, Array<Vector2> points) {
        for (int i = 0; i < INDEX.length; i += 3) {
            shapeRenderer.triangle(points.get(INDEX[i]).x,
                    points.get(INDEX[i]).y,
                    points.get(INDEX[i + 1]).x,
                    points.get(INDEX[i + 1]).y,
                    points.get(INDEX[i + 2]).x,
                    points.get(INDEX[i + 2]).y);
        }
    }
}
