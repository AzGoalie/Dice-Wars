package com.shadyaardvark.map;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.shadyaardvark.hex.Hexagon;

public class Region implements Comparable<Region> {
    private final int id;
    private int team;
    private boolean highlight;
    private Array<Region> neighboringRegions;
    private Array<Hexagon> hexagons;
    private int dice;

    public Region(int id) {
        this.id = id;
        neighboringRegions = new Array<>();
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

    public Array<Region> getNeighboringRegions() {
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

    public void addNeighborRegion(Region neighbor) {
        ObjectSet<Region> regionSet = new ObjectSet<>();
        regionSet.addAll(neighboringRegions);
        regionSet.add(neighbor);

        neighboringRegions = regionSet.iterator().toArray();
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

    @Override
    public int compareTo(Region o) {
        return dice - o.dice;
    }
}
