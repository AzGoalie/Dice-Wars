package com.shadyaardvark.map;

import static com.shadyaardvark.Settings.HEX_HEIGHT;
import static com.shadyaardvark.Settings.HEX_WIDTH;
import static com.shadyaardvark.Settings.MAP_HEIGHT;
import static com.shadyaardvark.Settings.MAP_WIDTH;
import static com.shadyaardvark.hex.Orientation.POINTY_TOP;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.shadyaardvark.hex.ConversionUtils;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.HexagonMap;
import com.shadyaardvark.hex.HexagonMapBuilder;
import com.shadyaardvark.hex.layouts.RectangleLayout;

public class GameBoard {
    private static final float PERCENT_FILLED = 0.75f;
    private IntMap<Region> regionMap;
    private ObjectMap<Hexagon, Integer> hexToRegionMap;
    private HexagonMap hexagonMap;

    private int numPlayers;
    private int currentPlayer;

    public GameBoard(int numPlayers) {
        regionMap = new IntMap<>();
        hexToRegionMap = new ObjectMap<>();
        currentPlayer = 0;
        this.numPlayers = numPlayers;

        createMap(numPlayers);
    }

    public void highlightNeighbors(Region region, boolean highlight) {
        for (int neighbor : region.getNeighboringRegions()) {
            Region neighboringRegion = regionMap.get(neighbor);
            if (neighboringRegion.getTeam() != region.getTeam()) {
                neighboringRegion.setHighlight(highlight);
            }
        }
    }

    public Region getRegion(float x, float y) {
        Hexagon hexagon = hexagonMap.pixelToHexagon(x, y);
        Integer i = hexToRegionMap.get(hexagon);
        if (i != null) {
            return regionMap.get(i);
        } else {
            return null;
        }
    }

    public int calcLongestChain(int team) {
        int max = 0;

        for (Region region : regionMap.values()) {
            if (region.getTeam() == team) {
                Array<Region> checked = new Array<>();
                checked.add(region);

                int depth = regionDepth(region, checked);
                if (depth > max) {
                    max = depth;
                }
            }
        }

        return max;
    }

    public boolean attack(Region attacker, Region defender) {
        if (attacker.getDice() == 1 ||
                !attacker.getNeighboringRegions().contains(defender.getId())) {
            return false;
        }

        int attackTotal = 0;
        int defendTotal = 0;

        for (int i = 0; i < attacker.getDice(); i++) {
            attackTotal += MathUtils.random(1,6);
        }

        for (int i = 0; i < defender.getDice(); i++) {
            defendTotal += MathUtils.random(1,6);
        }

        if (attackTotal > defendTotal) {
            defender.setTeam(attacker.getTeam());
            defender.setDice(attacker.getDice()-1);
            attacker.setDice(1);
            return true;
        }

        attacker.setDice(1);
        return false;
    }

    public void endTurn() {
        currentPlayer = (currentPlayer + 1) % numPlayers;

        if (currentPlayer == 0) {
            //TODO: End of round, distribute dice
        }
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    private int regionDepth(Region region, Array<Region> checked) {
        int result = 1;
        for (int i : region.getNeighboringRegions()) {
            Region neighbor = regionMap.get(i);
            if (neighbor.getTeam() == region.getTeam() && !checked.contains(neighbor, false)) {
                checked.add(neighbor);
                result += regionDepth(neighbor, checked);
            }
        }
        return result;
    }

    private void createMap(int numPlayers) {
        hexagonMap = new HexagonMapBuilder()
                .setGridSize(MAP_WIDTH, MAP_HEIGHT)
                .setHexSize(HEX_WIDTH, HEX_HEIGHT)
                .setLayout(new RectangleLayout())
                .setOrientation(POINTY_TOP)
                .buid();
        regionMap.clear();

        Array<Hexagon> available = getAvailableHexagons(hexagonMap);
        generateRegions(available, numPlayers);
    }

    private Array<Hexagon> getAvailableHexagons(HexagonMap map) {
        Array<Hexagon> available = new Array<>();

        int numFilled = 0;
        Vector2 currentPos = ConversionUtils.offsetToAxial(MAP_WIDTH / 2, MAP_HEIGHT / 2);
        while (numFilled <= MAP_WIDTH * MAP_HEIGHT * PERCENT_FILLED) {
            Hexagon hexagon = map.getHexagon(currentPos);
            if (!available.contains(hexagon, false)) {
                available.add(hexagon);
                numFilled++;
            }

            Array<Hexagon> valid = new Array<>();
            for (Hexagon neighbor : hexagon.getNeighbors()) {
                if (map.getHexagons().contains(neighbor, false)) {
                    valid.add(neighbor);
                }
            }

            currentPos = valid.random().getAxialPos();
        }

        return available;
    }

    private void generateRegions(Array<Hexagon> hexagons, int numPlayers) {
        int regionId = 1;
        while (hexagons.size > 0) {
            Hexagon hexagon = hexagons.random();
            hexagons.removeValue(hexagon, false);

            Region region = new Region(regionId);
            region.setTeam(regionId % numPlayers);
            region.addHexagon(hexagon);
            region.setDice(MathUtils.random(1, 3));
            hexToRegionMap.put(hexagon, regionId);

            Array<Hexagon> neighbors = hexagon.getNeighbors();
            neighbors.shuffle();
            for (Hexagon neighbor : neighbors) {
                if (hexagons.contains(neighbor, false) && MathUtils.random() >= .75f) {
                    region.addHexagon(neighbor);
                    hexToRegionMap.put(neighbor, regionId);
                    hexagons.removeValue(neighbor, false);
                }
            }

            regionMap.put(regionId++, region);
        }

        addNeighboringRegions();
    }

    private void addNeighboringRegions() {
        for (Region region : regionMap.values()) {
            Array<Hexagon> regionHexagons = region.getHexagons();
            for (Hexagon hexagon : regionHexagons) {
                Array<Hexagon> neighboringHexagons = hexagon.getNeighbors();
                for (Hexagon neighbor : neighboringHexagons) {
                    if (!regionHexagons.contains(neighbor, false) && hexToRegionMap.containsKey(neighbor)) {
                        region.addNeighborRegion(hexToRegionMap.get(neighbor));
                    }
                }
            }
        }
    }

    IntMap<Region> getRegionMap() {
        return regionMap;
    }

    HexagonMap getHexagonMap() {
        return hexagonMap;
    }
}
