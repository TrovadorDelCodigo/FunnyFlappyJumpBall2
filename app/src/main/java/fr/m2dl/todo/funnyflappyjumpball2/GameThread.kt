package fr.m2dl.todo.funnyflappyjumpball2

import android.graphics.Canvas
import android.view.SurfaceHolder
import fr.m2dl.todo.funnyflappyjumpball2.widges.GameView
import java.lang.Exception

class GameThread(
    val surfaceHolder: SurfaceHolder,
    val gameView: GameView
): Thread() {
    var running = false
    private var canvas: Canvas? = null

    override fun run() {
        while (running) {
            canvas = null

            try {
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    this.gameView.update()
                    this.gameView.draw(canvas)
                }
            } catch (exp: Exception) {
                // Nothing here
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (exp: Exception) {
                        exp.printStackTrace()
                    }
                }
            }
            sleep(60)
        }
    }
}
