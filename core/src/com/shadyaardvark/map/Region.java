package com.shadyaardvark.map;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.hex.Hexagon;

public class Region {
    private int region;
    private int team;
    private boolean highlight;
    private Set<Integer> neighboringRegions = new HashSet<>();
    private Array<Hexagon> hexagons = new Array<>();

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public Set<Integer> getNeighboringRegions() {
        return neighboringRegions;
    }

    public Array<Hexagon> getHexagons() {
        return hexagons;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void addHexagon(Hexagon hexagon) {
        hexagons.add(hexagon);
    }

    public void addNeighborRegion(int neighbor) {
        neighboringRegions.add(neighbor);
    }

    public boolean isValid() {
        return region != 0;
    }
}
