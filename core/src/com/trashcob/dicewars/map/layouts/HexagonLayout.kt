package com.trashcob.dicewars.map.layouts

import com.trashcob.dicewars.math.geom.hexagon.Hexagon


class HexagonLayout : GameBoardLayout {
    override fun generateLayout(width: Int, height: Int): List<Hexagon> {
        val hexagons = ArrayList<Hexagon>()

        for (q in -width..width) {
            val r1 = Math.max(-width, -q - width)
            val r2 = Math.min(width, -q + width)
            (r1..r2).mapTo(hexagons) { Hexagon(q, it) }
        }

        return hexagons
    }
}