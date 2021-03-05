package fr.m2dl.todo.funnyflappyjumpball2.engine

import fr.m2dl.todo.funnyflappyjumpball2.engine.impl.GameObject

interface GameEngineControl {
    var framesPerSecond: Int

    fun setSceneRoot(gameObject: GameObject)
    fun start()
    fun pause()
    fun resume()
    fun stop()
}
