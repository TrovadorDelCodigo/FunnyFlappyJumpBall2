package fr.m2dl.todo.funnyflappyjumpball2

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.Window
import android.view.WindowManager
import java.lang.Exception

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(GameView(this))
    }
}

class GameView(
        private val context: MainActivity
) : SurfaceView(context), SurfaceHolder.Callback {
    private var thread: GameThread
    private var x_axis: Float = 0F
    private var y_axis: Float = 0F

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        isFocusable = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread.running = true
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // TODO : Finish
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                stopThread()
            } catch (exp: InterruptedException) {
                exp.printStackTrace()
            }
            retry = false
        }
    }

    private fun stopThread() {
        thread.running = false
        thread.join()
    }

    fun update() {
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas != null) {
            canvas.drawColor(Color.WHITE)
        }
    }

}

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
                    }catch (exp: Exception) {
                        exp.printStackTrace()
                    }
                }
            }
            sleep(60)
        }
    }
}
