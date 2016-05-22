package com.shadyaardvark.geom;

public enum Orientation {
    POINTY(Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0, Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0, 0.5),
    FLAT(3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0), 2.0 / 3.0, 0.0, -1.0 / 3.0, Math.sqrt(3.0) / 3.0, 0.0);

    final double f0;
    final double f1;
    final double f2;
    final double f3;
    final double b0;
    final double b1;
    final double b2;
    final double b3;
    final double start_angle;

    Orientation(double f0, double f1, double f2, double f3, double b0, double b1, double b2, double b3, double start_angle) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.start_angle = start_angle;
    }
}
