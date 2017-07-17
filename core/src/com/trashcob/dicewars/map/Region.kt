package com.trashcob.dicewars.map

import com.trashcob.dicewars.math.geom.hexagon.Hexagon

data class Region(val hexagons: List<Hexagon>, val team: Int, val dice: Int = 1)
