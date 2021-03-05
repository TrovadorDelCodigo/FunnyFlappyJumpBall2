package fr.m2dl.todo.funnyflappyjumpball2.engine.impl

import android.graphics.Canvas
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameDrawingSurface
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameEngine
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameViewport

class GameEngineImpl(
        override var framesPerSecond: Int,
        private val gameDrawingSurface: GameDrawingSurface,
): GameEngine {

    override val viewport: GameViewport
        get() = gameDrawingSurface.viewport

    private lateinit var gameEngineThread: GameEngineThread

    var gameObjectTree: GameObject? = null

    override fun setSceneRoot(gameObject: GameObject) {
        gameObjectTree = gameObject
        initGameObject(gameObject)
    }

    override fun start() {
        if (gameObjectTree != null) {
            gameEngineThread = GameEngineThread(gameDrawingSurface, this)
            gameEngineThread.running = true
            gameEngineThread.start()
        } else {
            throw IllegalStateException("Set GameObjects tree root before calling start().")
        }
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        gameEngineThread.running = false
        gameEngineThread.join()
    }

    override fun initGameObject(gameObject: GameObject) {
        gameObject.initInternals(this, viewport)
        gameObject.init()
    }

    fun updateGameObjects(delta: Long) {
        updateGameObject(gameObjectTree!!, delta)
    }

    private fun updateGameObject(gameObject: GameObject, delta: Long) {
        gameObject.update(delta)
        gameObject.children.forEach {
            updateGameObject(it, delta)
        }
    }

    fun drawGameObjects(canvas: Canvas) {
        drawGameObject(gameObjectTree!!, canvas)
    }

    private fun drawGameObject(gameObject: GameObject, canvas: Canvas) {
        gameObject.draw(canvas)
        gameObject.children.forEach {
            drawGameObject(it, canvas)
        }
    }
}
