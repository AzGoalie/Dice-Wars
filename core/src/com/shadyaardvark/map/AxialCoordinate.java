package com.shadyaardvark.map;

import com.badlogic.gdx.math.GridPoint2;

public class AxialCoordinate extends GridPoint2 {
    public AxialCoordinate() {
        super();
    }

    public AxialCoordinate(int q, int r) {
        super(q, r);
    }

    public int getQ() {
        return x;
    }

    public int getR() {
        return y;
    }

    public static AxialCoordinate offsetToAxial(int x, int y) {
        int q = x - y / 2;
        return new AxialCoordinate(q, y);
    }
}
