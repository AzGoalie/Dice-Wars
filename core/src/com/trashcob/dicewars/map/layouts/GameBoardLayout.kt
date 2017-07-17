package com.trashcob.dicewars.map.layouts

import com.trashcob.dicewars.math.geom.hexagon.Hexagon

interface GameBoardLayout {
    fun generateLayout(width: Int, height: Int): List<Hexagon>
}