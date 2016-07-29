package com.shadyaardvark.map;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

public class Region {
    private final int region;

    private int team;
    private boolean highlight;
    private Set<Integer> neighboringRegions = new HashSet<>();
    private FloatArray points = new FloatArray();

    public Region(int region) {
        this.region = region;
    }

    public int getRegion() {
        return region;
    }

    public int getTeam() {
        return team;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public Set<Integer> getNeighboringRegions() {
        return neighboringRegions;
    }

    public FloatArray getPoints() {
        return points;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void addPoints(Array<Vector2> newPoints) {
        for (Vector2 point : newPoints) {
            points.add(point.x);
            points.add(point.y);
        }
    }

    public void addNeighborRegion(int neighbor) {
        neighboringRegions.add(neighbor);
    }

    public boolean isValid() {
        return region != 0;
    }
}
