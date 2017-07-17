package com.trashcob.dicewars

import com.badlogic.gdx.Screen
import com.trashcob.dicewars.screens.GameScreen
import com.trashcob.dicewars.screens.MainMenuScreen
import ktx.app.KtxGame

class DiceWars : KtxGame<Screen>() {
    override fun create() {
        addScreen(MainMenuScreen(this))
        addScreen(GameScreen())
//        setScreen<MainMenuScreen>()
        setScreen<GameScreen>()
    }
}
