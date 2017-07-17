package com.trashcob.dicewars.math.geom.hexagon.grid

import com.trashcob.dicewars.math.geom.hexagon.Hexagon

interface GridLayout {
    fun generateLayout(width: Int, height: Int): List<Hexagon>
}