package com.trashcob.dicewars.map

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.trashcob.dicewars.math.geom.hexagon.Layout

class GameBoardRenderer(val texture: Texture, val batch: SpriteBatch, val debugRenderer: ShapeRenderer? = null) {
    private val colors = listOf(Color.valueOf("AA2FFF"), Color.valueOf("01B0F0"), Color.valueOf("97FF7F"),
            Color.valueOf("E8A241"), Color.valueOf("FF5757"))

    fun render(gameBoard: GameBoard, layout: Layout, debug: Boolean = false) {
        batch.begin()

        for ((hexagons, team) in gameBoard.regions) {
            batch.color = colors[team]
            hexagons
                    .map { layout.hexagonToPoint(it).sub(texture.width / 2f, texture.height / 2f) }
                    .forEach { batch.draw(texture, it.x, it.y) }
        }

        batch.end()

        if (debug) {
            if (debugRenderer != null) {
                debugRenderer.color = Color.MAGENTA
                debugRenderer.begin(ShapeRenderer.ShapeType.Point)

                gameBoard.regions.flatMap { it.hexagons }
                        .flatMap { layout.hexagonCorners(it) }
                        .forEach { debugRenderer.point(it.x, it.y, 0f) }

                debugRenderer.end()
            }
        }
    }
}