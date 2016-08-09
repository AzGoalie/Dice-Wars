package com.shadyaardvark.map;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.hex.Hexagon;

public class Region {
    private final int id;
    private int team;
    private boolean highlight;
    private Set<Integer> neighboringRegions;
    private Array<Hexagon> hexagons;
    private int dice;

    public Region(int id) {
        this.id = id;
        neighboringRegions = new HashSet<>();
        hexagons = new Array<>();
        dice = 0;
    }

    public int getId() {
        return id;
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

    public int getDice() {
        return dice;
    }

    public void setDice(int dice) {
        this.dice = dice;
        if (this.dice > 6) {
            this.dice = 6;
        }

        if (this.dice < 1) {
            this.dice = 1;
        }
    }

    public boolean isValid() {
        return id != 0;
    }
}
