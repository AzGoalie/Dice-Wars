package com.trashcob.dicewars

import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication

fun main(argv: Array<String>) {
    val pool = NSAutoreleasePool()
    UIApplication.main<UIApplication, IOSLauncher>(argv, null, IOSLauncher::class.java)
    pool.close()
}

class IOSLauncher : IOSApplication.Delegate() {
    override fun createApplication(): IOSApplication {
        val config = IOSApplicationConfiguration()
        return IOSApplication(DiceWars(), config)
    }
}