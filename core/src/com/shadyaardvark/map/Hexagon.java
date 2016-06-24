package com.shadyaardvark.map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Hexagon {
    private static final int INVALID = -1;

    private int team = INVALID;
    private int region = INVALID;
    private boolean highlight = false;
    private Vector2 position = new Vector2(INVALID, INVALID);
    private int size = INVALID;

    public Hexagon(int x, int y, int size) {
        setX(x);
        setY(y);
        setSize(size);
    }

    public Array<Vector2> getPoints() {
        Array<Vector2> points = new Array<>();

        points.add(position);
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * (i + 0.5f);
            float x = (float) (position.x + size * cos(angle));
            float y = (float) (position.y + size * sin(angle));
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

    public Vector2 getPosition() {
        return position;
    }

    public int getX() {
        return (int) position.x;
    }

    public int getY() {
        return (int) position.y;
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

    public void setX(int x) {
        position.x = x;
    }

    public void setY(int y) {
        position.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isValid() {
        return team != INVALID && region != INVALID && position.x != INVALID
                && position.y != INVALID;
    }
}
