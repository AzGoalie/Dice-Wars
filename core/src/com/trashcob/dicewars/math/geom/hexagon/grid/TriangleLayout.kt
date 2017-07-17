package com.trashcob.dicewars.math.geom.hexagon.grid

import com.trashcob.dicewars.math.geom.hexagon.Hexagon

class TriangleLayout : GridLayout {
    override fun generateLayout(width: Int, height: Int): List<Hexagon> {
        val hexagons = ArrayList<Hexagon>()

        for (q in 0..width) {
            (0..width - q).mapTo(hexagons) { Hexagon(q, it) }
        }

        return hexagons
    }
}