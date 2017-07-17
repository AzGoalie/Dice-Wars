package com.trashcob.dicewars.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.trashcob.dicewars.DiceWars

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(DiceWars(), config)
}
