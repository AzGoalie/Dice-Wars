package com.trashcob.dicewars.math.geom.hexagon

enum class Orientation(val f0: Float, val f1: Float, val f2: Float, val f3: Float,
                       val b0: Float, val b1: Float, val b2: Float, val b3: Float,
                       val startAngle: Float) {
    POINTY(Math.sqrt(3.0).toFloat(), Math.sqrt(3.0).toFloat() / 2f, 0f, 3f / 2f,
            Math.sqrt(3.0).toFloat() / 3f, -1f / 3f, 0f, 2f / 3f,
            0.5f),
    FLAT(3f / 2f, 0f, Math.sqrt(3.0).toFloat() / 2f, Math.sqrt(3.0).toFloat(),
            2f / 3f, 0f, -1f / 3f, Math.sqrt(3.0).toFloat() / 3f,
            0f)
}
