package com.shadyaardvark.geom;

import java.util.ArrayList;

public class Layout {
    public final Orientation orientation;
    public final Point size;
    public final Point origin;

    public Layout(Orientation orientation, Point size, Point origin) {
        this.orientation = orientation;
        this.size = size;
        this.origin = origin;
    }

    public Point hexToPixel(Hexagon h) {
        double x = (orientation.f0 * h.q + orientation.f1 * h.r) * size.x;
        double y = (orientation.f2 * h.q + orientation.f3 * h.r) * size.y;
        return new Point((int) x + origin.x, (int) y + origin.y);
    }

    public Point hexCornerOffset(int corner) {
        double angle = 2.0 * Math.PI * (corner + orientation.start_angle) / 6;
        return new Point((int) (size.x * Math.cos(angle)), (int) (size.y * Math.sin(angle)));
    }

    public ArrayList<Point> polygonCorners(Hexagon h) {
        ArrayList<Point> corners = new ArrayList<Point>();
        Point center = hexToPixel(h);
        for (int i = 0; i < 6; i++) {
            Point offset = hexCornerOffset(i);
            corners.add(new Point(center.x + offset.x, center.y + offset.y));
        }
        return corners;
    }
}
