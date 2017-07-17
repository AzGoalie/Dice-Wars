package com.trashcob.dicewars.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.actors.onClick
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.label
import ktx.scene2d.table
import ktx.scene2d.textButton

class MainMenuScreen(val game: KtxGame<Screen>) : KtxScreen {
    val stage = Stage()

    init {
        Scene2DSkin.defaultSkin = Skin(Gdx.files.internal("uiskin.json"))

        val root = table {
            setFillParent(true)
            label("Dice Wars")
            row()

            textButton("Start") {
                onClick {
                    game.setScreen<GameScreen>()
                }
            }
            row()

            textButton("Exit") {
                onClick {
                    Gdx.app.exit()
                }
            }
        }

        stage.addActor(root)
    }


    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun dispose() {
        Scene2DSkin.defaultSkin.dispose()
        stage.dispose()
    }

    override fun render(delta: Float) {
        stage.act(delta)
        stage.draw()
    }
}