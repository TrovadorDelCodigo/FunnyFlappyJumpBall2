package fr.m2dl.todo.funnyflappyjumpball2.engine.impl

import android.graphics.Canvas
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameDrawingSurface
import java.lang.Exception

class GameEngineThread(
        private val gameDrawingSurface: GameDrawingSurface,
        private val gameEngine: GameEngineImpl
): Thread() {
    var running = false

    override fun run() {
        var canvas: Canvas? = null

        while (running) {
            try {
                canvas = gameDrawingSurface.lockAndGetCanvas()
                synchronized(gameDrawingSurface) {
                    gameEngine.updateGameObjects(16)
                    gameEngine.drawGameObjects(canvas!!)
                }
                sleep((1 / gameEngine.framesPerSecond).toLong())
            } catch (exp: Exception) {
                // Nothing here
            } finally {
                if (canvas != null) {
                    try {
                        gameDrawingSurface.unlockCanvas(canvas)
                    } catch (exp: Exception) {
                        exp.printStackTrace()
                    }
                }
            }
        }
    }
}
