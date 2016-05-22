package com.shadyaardvark.geom;

import java.util.ArrayList;

public class Hexagon {
    static public ArrayList<Hexagon> directions = new ArrayList<Hexagon>() {{
        add(new Hexagon(1, 0, -1));
        add(new Hexagon(1, -1, 0));
        add(new Hexagon(0, -1, 1));
        add(new Hexagon(-1, 0, 1));
        add(new Hexagon(-1, 1, 0));
        add(new Hexagon(0, 1, -1));
    }};
    static public ArrayList<Hexagon> diagonals = new ArrayList<Hexagon>() {{
        add(new Hexagon(2, -1, -1));
        add(new Hexagon(1, -2, 1));
        add(new Hexagon(-1, -1, 2));
        add(new Hexagon(-2, 1, 1));
        add(new Hexagon(-1, 2, -1));
        add(new Hexagon(1, 1, -2));
    }};
    public final int q;
    public final int r;
    public final int s;

    public Hexagon(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    static public Hexagon add(Hexagon a, Hexagon b) {
        return new Hexagon(a.q + b.q, a.r + b.r, a.s + b.s);
    }

    static public Hexagon subtract(Hexagon a, Hexagon b) {
        return new Hexagon(a.q - b.q, a.r - b.r, a.s - b.s);
    }

    static public Hexagon scale(Hexagon a, int k) {
        return new Hexagon(a.q * k, a.r * k, a.s * k);
    }

    static public Hexagon direction(int direction) {
        return Hexagon.directions.get(direction);
    }

    static public Hexagon neighbor(Hexagon Hexagon, int direction) {
        return Hexagon.add(Hexagon, Hexagon.direction(direction));
    }

    static public Hexagon diagonalNeighbor(Hexagon Hexagon, int direction) {
        return Hexagon.add(Hexagon, Hexagon.diagonals.get(direction));
    }

    static public int length(Hexagon Hexagon) {
        return (int) ((Math.abs(Hexagon.q) + Math.abs(Hexagon.r) + Math.abs(Hexagon.s)) / 2);
    }

    static public int distance(Hexagon a, Hexagon b) {
        return Hexagon.length(Hexagon.subtract(a, b));
    }
}
