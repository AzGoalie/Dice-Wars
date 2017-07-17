package com.trashcob.dicewars.math.geom.hexagon.grid

import com.trashcob.dicewars.math.geom.hexagon.Hexagon

class ParallelogramLayout : GridLayout {
    override fun generateLayout(width: Int, height: Int): List<Hexagon> {
        val hexagons = ArrayList<Hexagon>()

        for (q in 0 until width) {
            (0 until height).mapTo(hexagons) { Hexagon(q, it) }
        }

        return hexagons
    }
}