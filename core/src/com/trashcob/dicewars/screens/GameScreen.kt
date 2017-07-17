package com.trashcob.dicewars.screens

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.trashcob.dicewars.map.GameBoard
import com.trashcob.dicewars.map.GameBoardRenderer
import com.trashcob.dicewars.math.geom.hexagon.Layout
import com.trashcob.dicewars.math.geom.hexagon.Orientation
import ktx.app.KtxScreen

private val hexSize = 16f
private val width = 5
private val height = 5

class GameScreen : KtxScreen {
    val spriteBatch = SpriteBatch()
    val shapeRenderer = ShapeRenderer()
    val texture = Texture("hexagon.png")

    val layout = Layout(Orientation.POINTY, Vector2(hexSize, hexSize), Vector2(hexSize, hexSize))
    val map = GameBoard(width, height)
    val mapRender = GameBoardRenderer(texture, spriteBatch, shapeRenderer)

    override fun dispose() {
        texture.dispose()
        spriteBatch.dispose()
        shapeRenderer.dispose()
    }

    override fun render(delta: Float) {
        mapRender.render(map, layout, true)
    }
}