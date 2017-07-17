package com.trashcob.dicewars.math.geom.hexagon

data class Hexagon(val q: Int, val r: Int, val s: Int = -q - r) {
    companion object {
        val directions = arrayOf(Hexagon(1, 0, -1), Hexagon(1, -1, 0), Hexagon(0, -1, 1),
                Hexagon(-1, 0, 1), Hexagon(-1, 1, 0), Hexagon(0, 1, -1))
    }

    operator fun plus(b: Hexagon) = Hexagon(q + b.q, r + b.r, s + b.s)
    operator fun minus(b: Hexagon) = Hexagon(q - b.q, r - b.r, s - b.s)
    operator fun times(k: Int) = Hexagon(q * k, r * k, s * k)

    fun length() = (Math.abs(q) + Math.abs(r) + Math.abs(s)) / 2
    fun distance(b: Hexagon) = (this - b).length()

    fun neighbor(direction: Int): Hexagon {
        if ((direction < 0) or (direction > directions.size))
            throw IllegalArgumentException("direction must be between 0 and ${directions.size - 1} inclusive")
        return this + directions[direction]
    }
}
