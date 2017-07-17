package com.trashcob.dicewars.map

import com.trashcob.dicewars.map.layouts.RectangleLayout

class GameBoard(width: Int, height: Int) {
    val regions: List<Region> = RegionGenerator(width, height, RectangleLayout()).generateRegions()
}