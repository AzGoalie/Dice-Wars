package com.trashcob.dicewars.math.geom.hexagon.grid

import com.trashcob.dicewars.math.geom.hexagon.Hexagon


class RectangleLayout : GridLayout {
    override fun generateLayout(width: Int, height: Int): List<Hexagon> {
        val hexagons = ArrayList<Hexagon>()

        for (r in 0..height - 1) {
            val rOffset = Math.floor(r / 2.0).toInt()
            (-rOffset..width - rOffset - 1).mapTo(hexagons) { Hexagon(it, r) }
        }

        return hexagons
    }

}