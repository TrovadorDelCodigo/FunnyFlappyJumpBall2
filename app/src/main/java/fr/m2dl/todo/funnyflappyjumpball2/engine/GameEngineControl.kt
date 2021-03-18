package fr.m2dl.todo.funnyflappyjumpball2.engine

import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject
import fr.m2dl.todo.funnyflappyjumpball2.engine.signals.SignalManager

interface GameEngineControl {
    var framesPerSecond: Int
    val signalManager: SignalManager

    fun setSceneRoot(gameObject: GameObject)
    fun start()
    fun pause()
    fun resume()
    fun stop()
}
