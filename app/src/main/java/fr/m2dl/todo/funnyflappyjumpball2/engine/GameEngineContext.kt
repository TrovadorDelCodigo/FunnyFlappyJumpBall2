package fr.m2dl.todo.funnyflappyjumpball2.engine

import android.content.res.Resources
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject
import fr.m2dl.todo.funnyflappyjumpball2.engine.signals.SignalManager

interface GameEngineContext {
    val viewport: GameViewport
    val resources: Resources
    val signalManager: SignalManager
    fun initGameObject(gameObject: GameObject)
    fun deinitGameObject(gameObject: GameObject)
}
