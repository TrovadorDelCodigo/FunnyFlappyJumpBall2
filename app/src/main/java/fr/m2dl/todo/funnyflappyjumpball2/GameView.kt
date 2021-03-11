package fr.m2dl.todo.funnyflappyjumpball2

import android.app.Activity
import android.view.SurfaceHolder
import android.view.SurfaceView
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.AccelerometerEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameEngine
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.TouchScreenEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.impl.GameDrawingSurfaceImpl
import fr.m2dl.todo.funnyflappyjumpball2.engine.impl.GameEngineImpl
import fr.m2dl.todo.funnyflappyjumpball2.gameobjects.Scene

class GameView(
    private val activity: Activity
) : SurfaceView(activity), SurfaceHolder.Callback {

    private val defaultFps = 60
    private var gameEngine: GameEngine? = null

    init {
        holder.addCallback(this)
        isFocusable = true
    }

    private fun populateGameWorld() {
        gameEngine?.setSceneRoot(Scene())
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        startGame()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                stopGame()
            } catch (exp: InterruptedException) {
                exp.printStackTrace()
            }
            retry = false
        }
    }

    private fun startGame() {
        gameEngine = GameEngineImpl(defaultFps, GameDrawingSurfaceImpl(this))
        populateGameWorld()
        gameEngine?.start()
    }

    private fun stopGame() {
        gameEngine?.stop()
    }

    fun notifyEvent(event: AccelerometerEvent) {
        gameEngine?.notifyEvent(event)
    }

    fun notifyEvent(event: TouchScreenEvent) {
        gameEngine?.notifyEvent(event)
    }
}
