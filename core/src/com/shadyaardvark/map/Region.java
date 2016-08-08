package com.shadyaardvark.map;

import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.hex.Hexagon;

import java.util.HashSet;
import java.util.Set;

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

    public void addDice(int diceToAdd) {
        dice += diceToAdd;
        if (dice > 6) {
            dice = 6;
        }
    }

    public boolean isValid() {
        return id != 0;
    }
}
