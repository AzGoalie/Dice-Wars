package com.shadyaardvark.map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Hexagon {
    private static final int INVALID = -1;

    private int team = INVALID;
    private int region = INVALID;
    private boolean highlight = false;
    private final AxialCoordinate position;
    private int size = INVALID;

    private final float hexWidth;
    private final float hexHeight;

    public Hexagon(int x, int y, int size) {
        position = AxialCoordinate.offsetToAxial(x, y);
        setSize(size);

        hexWidth = (float) (sqrt(3) * size);
        hexHeight = size * 3 / 2;
    }

    public Array<Vector2> getPoints() {
        Array<Vector2> points = new Array<>();

        points.add(getCenter());
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * (i + 0.5);
            float x = (float) (getCenter().x + size * cos(angle));
            float y = (float) (getCenter().y + size * sin(angle));
            points.add(new Vector2(x, y));
        }
        return points;
    }

    public int getTeam() {
        return team;
    }

    public int getRegion() {
        return region;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public AxialCoordinate getPosition() {
        return position;
    }

    public int getQ() {
        return position.getQ();
    }

    public int getR() {
        return position.getR();
    }

    public int getSize() {
        return size;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isValid() {
        return team != INVALID && region != INVALID && position != null;
    }

    public Vector2 getCenter() {
        float x = position.getQ() * hexWidth + position.getR() * hexWidth / 2 + hexWidth / 2;
        float y = position.getR() * hexHeight + size;
        return new Vector2(x, y);
    }
}
