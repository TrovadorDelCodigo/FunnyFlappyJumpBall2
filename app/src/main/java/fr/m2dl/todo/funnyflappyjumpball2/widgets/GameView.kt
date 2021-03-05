package fr.m2dl.todo.funnyflappyjumpball2.widgets

import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder
import android.view.SurfaceView
import fr.m2dl.todo.funnyflappyjumpball2.GameThread
import fr.m2dl.todo.funnyflappyjumpball2.MainActivity

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
