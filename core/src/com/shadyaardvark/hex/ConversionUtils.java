package com.shadyaardvark.hex;

import com.badlogic.gdx.math.Vector2;

public class ConversionUtils {
    public static Vector2 offsetToAxial(int x, int y) {
        return new Vector2(x - (y + (y % 2)) / 2, y);
    }
}
