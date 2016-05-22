package com.shadyaardvark.geom;

public class OffsetCoord {
    public static final int EVEN = 1;
    public static final int ODD = -1;
    public final int x;
    public final int y;

    public OffsetCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static public OffsetCoord qoffsetFromCube(int offset, Hexagon h) {
        int col = h.q;
        int row = h.r + (h.q + offset * (h.q & 1)) / 2;
        return new OffsetCoord(col, row);
    }


    static public Hexagon qoffsetToCube(int offset, OffsetCoord h) {
        int q = h.x;
        int r = h.y - (h.x + offset * (h.x & 1)) / 2;
        int s = -q - r;
        return new Hexagon(q, r, s);
    }


    static public OffsetCoord roffsetFromCube(int offset, Hexagon h) {
        int col = h.q + (h.r + offset * (h.r & 1)) / 2;
        int row = h.r;
        return new OffsetCoord(col, row);
    }


    static public Hexagon roffsetToCube(int offset, OffsetCoord h) {
        int q = h.x - (h.y + offset * (h.y & 1)) / 2;
        int r = h.y;
        int s = -q - r;
        return new Hexagon(q, r, s);
    }
}
