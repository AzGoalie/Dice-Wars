package com.trashcob.dicewars.map

import com.trashcob.dicewars.math.geom.hexagon.grid.RectangleLayout

class GameBoard(width: Int, height: Int) {
    val regions: List<Region>

    init {
        val hexagons = MapGenerator(width, height, RectangleLayout()).generateMap()
        regions = RegionGenerator(hexagons).generateRegions()
    }
}