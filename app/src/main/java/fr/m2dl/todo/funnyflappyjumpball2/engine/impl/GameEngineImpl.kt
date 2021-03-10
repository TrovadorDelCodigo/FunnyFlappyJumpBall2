package fr.m2dl.todo.funnyflappyjumpball2.engine.impl

import android.graphics.Canvas
import android.hardware.SensorManager
import fr.m2dl.todo.funnyflappyjumpball2.engine.AccelerometerEventListener
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameDrawingSurface
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameEngine
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameViewport
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.AccelerometerEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.GameInputEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

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
        if (gameObject is CollidableGameObject<*>) {
            gameObject.initInternals(this, this, viewport)
        } else {
            gameObject.initInternals(this, viewport)
        }
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

    override fun checkCollisions(collidableGameObject: CollidableGameObject<*>): List<GameObject> {
        val collisions = mutableListOf<GameObject>()
        checkCollision(collidableGameObject, gameObjectTree!!, collisions)
        return collisions
    }

    private fun checkCollision(collidableGameObject: CollidableGameObject<*>,
                               gameObject: GameObject,
                               collisions: MutableList<GameObject>) {
        if (collidableGameObject !== gameObject
                && gameObject is CollidableGameObject<*>
                && collidableGameObject.collider.collidesWith(gameObject.collider)) {
            collisions += gameObject
        }
        gameObject.children.forEach {
            checkCollision(collidableGameObject, it, collisions)
        }
    }

    override fun notifyEvent(event: GameInputEvent) {
        if (event is AccelerometerEvent) {
            notifyAccelerometerEvent(gameObjectTree!!, event)
        }
    }

    private fun notifyAccelerometerEvent(gameObject: GameObject, event: AccelerometerEvent) {
        if (gameObject is AccelerometerEventListener) {
            gameObject.onAccelerometerEvent(event)
        }
        gameObject.children.forEach {
            notifyAccelerometerEvent(it, event)
        }
    }

}
