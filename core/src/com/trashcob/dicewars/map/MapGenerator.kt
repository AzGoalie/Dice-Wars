package com.trashcob.dicewars.map

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.trashcob.dicewars.math.geom.hexagon.Hexagon
import com.trashcob.dicewars.math.geom.hexagon.grid.GridLayout

class MapGenerator(private val width: Int, private val height: Int, layout: GridLayout) {
    private val PERCENT_FILLED = 0.6
    private val availableHexagons = layout.generateLayout(width, height)
    private val map = Array<Hexagon>()

    fun generateMap(): List<Hexagon> {
        map.clear()

        var currentHexagon = availableHexagons.find { it.q == width / 2 && it.r == height / 2 } ?: availableHexagons.first()
        map.add(currentHexagon)
        while (map.size < width * height * PERCENT_FILLED) {
            currentHexagon = findNextAvailableHexagon(currentHexagon)
            map.add(currentHexagon)
        }

        return map.toList()
    }

    private tailrec fun findNextAvailableHexagon(hexagon: Hexagon): Hexagon {
        if (noFreeNeighbors(hexagon))
            return findNextAvailableHexagon(map.random())

        val next = hexagon.neighbor(MathUtils.random(0, Hexagon.directions.size - 1))
        if (hexagonAlreadyExistsOrOutOfBounds(next))
            return findNextAvailableHexagon(hexagon)
        else
            return next
    }

    private fun noFreeNeighbors(hexagon: Hexagon): Boolean =
            (0 until Hexagon.directions.size - 1)
                    .map { hexagon.neighbor(it) }
                    .none { !map.contains(it) and availableHexagons.contains(it) }

    private fun hexagonAlreadyExistsOrOutOfBounds(hexagon: Hexagon): Boolean
            = map.contains(hexagon) or !availableHexagons.contains(hexagon)
}