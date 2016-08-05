package com.shadyaardvark.hex;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Hexagon {
    private static List<Hexagon> directions = Arrays.asList(new Hexagon(1, 0, -1),
            new Hexagon(1, -1, 0),
            new Hexagon(0, -1, 1),
            new Hexagon(-1, 0, 1),
            new Hexagon(-1, 1, 0),
            new Hexagon(0, 1, -1));

    public final int q;
    public final int r;
    public final int s;
    private final Vector2 axialPos;

    public Hexagon(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;

        axialPos = new Vector2(q, r);

        if (q + r + s != 0) {
            throw new IllegalArgumentException("Hexagon positon must equal q + r + s = 0");
        }
    }

    public Hexagon(int q, int r) {
        this(q, r, -q - r);
    }

    public Hexagon add(Hexagon b) {
        return new Hexagon(q + b.q, r + b.r);
    }

    public Hexagon subtract(Hexagon b) {
        return new Hexagon(q - b.q, r - b.r);
    }

    public Hexagon scale(int k) {
        return new Hexagon(q * k, r * k);
    }

    public int length() {
        return (Math.abs(q) + Math.abs(r) + Math.abs(s)) / 2;
    }

    public int distance(Hexagon b) {
        return subtract(b).length();
    }

    public Array<Hexagon> getNeighbors() {
        Array<Hexagon> neighbors = new Array<>();
            for (Hexagon hex : directions) {
                neighbors.add(add(hex));
            }
        return neighbors;
    }

    public Vector2 getAxialPos() {
        return axialPos;
    }

    @Override
    public boolean equals(Object hex) {
        if (!(hex instanceof Hexagon)) {
            return false;
        }

        Hexagon other = (Hexagon) hex;
        return q == other.q && r == other.r && s == other.s;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + q;
        result = 31 * result + r;
        result = 31 * result + s;
        return result;
    }

    @Override
    public String toString() {
        return "hexagon( " + q + ", " + r + ", " + s + ")";
    }
}
