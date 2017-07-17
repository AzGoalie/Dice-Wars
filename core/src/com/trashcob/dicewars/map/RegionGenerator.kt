package com.trashcob.dicewars.map

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.trashcob.dicewars.map.layouts.GameBoardLayout
import com.trashcob.dicewars.math.geom.hexagon.Hexagon

class RegionGenerator(val width: Int, val height: Int, val layout: GameBoardLayout) {

    fun generateRegions(): List<Region> {
        val hexagons = generateHexagons() as MutableList<Hexagon>
        val regions = ArrayList<Region>()

        val region = Region(hexagons, 1)
        regions.add(region)

        return regions
    }

    private fun generateHexagons(): List<Hexagon> {
        val availableHexagons: List<Hexagon> = layout.generateLayout(width, height)
        val hexagons = Array<Hexagon>()

        hexagons.add(availableHexagons[MathUtils.random(0, availableHexagons.size - 1)])
        while (hexagons.size < width * height * 1) {
            hexagons.add(findNextAvailableHexagon(hexagons))
        }

        return hexagons.toList()
    }

    private fun findNextAvailableHexagon(hexagons: Array<Hexagon>): Hexagon {
        val hexagon = hexagons.random()
        val next = hexagon.neighbor(MathUtils.random(0, Hexagon.directions.size - 1))
        if (hexagons.contains(next)) findNextAvailableHexagon(hexagons)

        return next
    }
}