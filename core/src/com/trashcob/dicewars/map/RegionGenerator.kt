package com.trashcob.dicewars.map

import com.trashcob.dicewars.math.geom.hexagon.Hexagon

class RegionGenerator(private val hexagons: List<Hexagon>) {
    fun generateRegions(): List<Region> {
        val availableHexagons = ArrayList<Hexagon>(hexagons)
        val regions = ArrayList<Region>()

        var team = 0
        for (hexagon in hexagons) {
            if (availableHexagons.contains(hexagon)) {
                availableHexagons.remove(hexagon)

                val regionHexagons = ArrayList<Hexagon>()
                regionHexagons.add(hexagon)

                for (neighbor in getHexagonNeighbors(hexagon)) {
                    if (availableHexagons.contains(neighbor)) {
                        availableHexagons.remove(neighbor)
                        regionHexagons.add(neighbor)
                    }
                }

                regions.add(Region(regionHexagons, team++ % 5))
            }
        }

        return regions
    }

    private fun getHexagonNeighbors(hexagon: Hexagon): List<Hexagon> {
        return (0 until Hexagon.directions.size)
                .map { hexagon.neighbor(it) }
                .toList()
    }
}