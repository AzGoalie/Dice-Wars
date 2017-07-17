package com.trashcob.dicewars.math.geom.hexagon

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

data class Layout(val orientation: Orientation, val size: Vector2, val origin: Vector2) {
    fun hexagonToPoint(hexagon: Hexagon): Vector2 {
        val x = (orientation.f0 * hexagon.q + orientation.f1 * hexagon.r) * size.x
        val y = (orientation.f2 * hexagon.q + orientation.f3 * hexagon.r) * size.y
        return Vector2(x + origin.x, y + origin.y)
    }

    fun pointToHexagon(point: Vector2): Hexagon {
        val q = orientation.b0 * point.x + orientation.b1 * point.y
        val r = orientation.b2 * point.x + orientation.b3 * point.y
        return closestHexagon(q, r, -q - r)
    }

    fun hexagonCorners(hexagon: Hexagon): List<Vector2> {
        val corners = mutableListOf<Vector2>()
        val center = hexagonToPoint(hexagon)

        (0 until 6)
                .map { hexagonCornerOffset(it) }
                .mapTo(corners) { Vector2(center.x + it.x, center.y + it.y) }

        return corners
    }

    private fun closestHexagon(q: Float, r: Float, s: Float): Hexagon {
        val rq = Math.round(q)
        val rr = Math.round(r)
        val rs = Math.round(s)

        val qDiff = Math.abs(rq - q)
        val rDiff = Math.abs(rr - r)
        val sDiff = Math.abs(rs - s)

        when {
            (qDiff > rDiff) and (qDiff > sDiff) -> return Hexagon(-rr - rs, rr, rs)
            rDiff > sDiff -> return Hexagon(rq, -rq - rs, rs)
            else -> return Hexagon(rq, rs, -rq - rr)
        }
    }

    private fun hexagonCornerOffset(corner: Int): Vector2 {
        val angle = 2f * MathUtils.PI * (orientation.startAngle + corner) / 6f
        return Vector2(size.x * MathUtils.cos(angle), size.y * MathUtils.sin(angle))
    }
}