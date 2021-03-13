package fr.m2dl.todo.funnyflappyjumpball2

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.os.Handler
import android.view.SurfaceHolder
import android.view.SurfaceView
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameEngine
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.*
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject
import fr.m2dl.todo.funnyflappyjumpball2.engine.impl.GameDrawingSurfaceImpl
import fr.m2dl.todo.funnyflappyjumpball2.engine.impl.GameEngineImpl
import fr.m2dl.todo.funnyflappyjumpball2.gameobjects.CoolCircle
import fr.m2dl.todo.funnyflappyjumpball2.gameobjects.FPSCounter
import fr.m2dl.todo.funnyflappyjumpball2.gameobjects.Scene
import fr.m2dl.todo.funnyflappyjumpball2.gameobjects.SimpleRect

class GameView(
    private val activity: Activity
) : SurfaceView(activity), SurfaceHolder.Callback {

    private val defaultFps = 60
    private var gameEngine: GameEngine? = null

    init {
        holder.addCallback(this)
        isFocusable = true
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
        gameEngine = GameEngineImpl(defaultFps, GameDrawingSurfaceImpl(this), activity.resources)
        populateGameWorld()
        gameEngine?.start()
    }

    private fun stopGame() {
        gameEngine?.stop()
    }

    private fun populateGameWorld() {
        gameEngine?.setSceneRoot(Scene())
    }

    private fun populateGameWorldTestChangeScene() {
        gameEngine?.setSceneRoot(Scene())

        // test
        val handler = Handler()
        handler.postDelayed({
            gameEngine?.setSceneRoot(object : GameObject() {
                override fun init() {
                    addChild(SimpleRect(0f, 0f, width.toFloat(), height.toFloat(), Color.BLACK))
                    addChild(CoolCircle(width / 2f, height / 2f, 100f, Color.YELLOW))
                    addChild(FPSCounter())
                }

                override fun deinit() {
                }

                override fun update(delta: Long) {
                }

                override fun draw(canvas: Canvas) {
                }

            })

            handler.postDelayed({
                gameEngine?.setSceneRoot(Scene())

            }, 3000)
        }, 3000)
    }

    fun notifyEvent(event: AccelerometerEvent) {
        gameEngine?.notifyEvent(event)
    }

    fun notifyEvent(event: TouchScreenEvent) {
        gameEngine?.notifyEvent(event)
    }
}