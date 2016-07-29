package com.shadyaardvark.hex;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class HexagonMap {
    private final Map<Vector2, Hexagon> hexagons;
    private final Orientation orientation;
    private final Vector2 origin;
    private final Vector2 hexSize;

    private HexagonMap() {
        hexagons = null;
        orientation = null;
        origin = null;
        hexSize = null;
    }

    HexagonMap(Map<Vector2, Hexagon> hexagons, Orientation orientation, Vector2 origin,
            Vector2 hexSize) {
        this.hexagons = hexagons;
        this.orientation = orientation;
        this.origin = origin;
        this.hexSize = hexSize;
    }

    public Hexagon getHexagon(Vector2 axialPosition) {
        return hexagons.get(axialPosition);
    }

    public Array<Hexagon> getHexagons() {
        Array<Hexagon> result = new Array<>();

        for (Hexagon hexagon : hexagons.values()) {
            result.add(hexagon);
        }

        return result;
    }

    public Vector2 getHexCenter(Hexagon h) {
        double x = (orientation.f0 * h.q + orientation.f1 * h.r) * hexSize.x;
        double y = (orientation.f2 * h.q + orientation.f3 * h.r) * hexSize.y;
        return new Vector2((float) x + origin.x, (float) y + origin.y);
    }

    public Hexagon pixelToHexagon(Vector2 p) {
        Vector2 pt = new Vector2((p.x - origin.x) / hexSize.x, (p.y - origin.y) / hexSize.y);
        double q = orientation.b0 * pt.x + orientation.b1 * pt.y;
        double r = orientation.b2 * pt.x + orientation.b3 * pt.y;
        double s = -q - r;

        int qq = (int) round(q);
        int rr = (int) round(r);
        int ss = (int) round(s);
        double q_diff = abs(qq - q);
        double r_diff = abs(rr - r);
        double s_diff = abs(ss - s);
        if (q_diff > r_diff && q_diff > s_diff) {
            qq = -rr - ss;
        } else if (r_diff > s_diff) {
            rr = -qq - ss;
        }
        return new Hexagon(qq, rr);
    }

    public Array<Vector2> getHexCorners(Hexagon h) {
        Array<Vector2> corners = new Array<>();

        Vector2 center = getHexCenter(h);
        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI * (orientation.startAngle - i) / 6;

            Vector2 Vector2 = new Vector2(center.x + hexSize.x * (float) cos(angle),
                    center.y + hexSize.y * (float) sin(angle));
            corners.add(Vector2);
        }

        return corners;
    }
}
