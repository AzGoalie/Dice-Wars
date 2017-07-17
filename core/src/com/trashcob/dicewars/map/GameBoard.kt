package com.trashcob.dicewars.map

import com.trashcob.dicewars.math.geom.hexagon.grid.RectangleLayout

class GameBoard(width: Int, height: Int) {
    val regions: List<Region> = RegionGenerator(width, height, RectangleLayout()).generateRegions()
}