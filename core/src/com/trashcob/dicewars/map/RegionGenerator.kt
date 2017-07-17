package com.trashcob.dicewars.map

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.trashcob.dicewars.math.geom.hexagon.Hexagon
import com.trashcob.dicewars.math.geom.hexagon.grid.GridLayout

class RegionGenerator(private val width: Int, private val height: Int, layout: GridLayout) {
    private val availableHexagons: List<Hexagon> = layout.generateLayout(width, height)
    private val PERCENT_FILLED = 0.6

    fun generateRegions(): List<Region> {
        val hexagons = generateHexagons() as MutableList<Hexagon>
        val regions = ArrayList<Region>()

        val region = Region(hexagons, 1)
        regions.add(region)

        return regions
    }

    private fun generateHexagons(): List<Hexagon> {
        val hexagons = Array<Hexagon>()

        hexagons.add(availableHexagons[MathUtils.random(0, availableHexagons.size - 1)])
        while (hexagons.size < width * height * PERCENT_FILLED) {
            hexagons.add(findNextAvailableHexagon(hexagons))
        }

        return hexagons.toList()
    }

    private tailrec fun findNextAvailableHexagon(hexagons: Array<Hexagon>): Hexagon {
        val hexagon = hexagons.random()
        val next = hexagon.neighbor(MathUtils.random(0, Hexagon.directions.size - 1))

        if (hexagons.contains(next) or !availableHexagons.contains(next))
            return findNextAvailableHexagon(hexagons)
        else
            return next
    }
}