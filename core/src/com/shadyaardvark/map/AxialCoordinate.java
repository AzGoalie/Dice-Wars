package com.shadyaardvark.map;

import com.badlogic.gdx.math.Vector2;

public class AxialCoordinate extends Vector2 {
    public AxialCoordinate() {
        super();
    }

    public AxialCoordinate(float q, float r) {
        super(q, r);
    }

    public float getQ() {
        return x;
    }

    public float getR() {
        return y;
    }

    public static AxialCoordinate offsetToAxial(int x, int y) {
        int q = x - y / 2;
        return new AxialCoordinate(q, y);
    }
}
