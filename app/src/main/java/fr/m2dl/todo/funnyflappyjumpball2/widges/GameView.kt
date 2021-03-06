package fr.m2dl.todo.funnyflappyjumpball2.widges

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder
import android.view.SurfaceView
import fr.m2dl.todo.funnyflappyjumpball2.GameThread

class GameView(
        context: Context
) : SurfaceView(context), SurfaceHolder.Callback {
    private var thread: GameThread
    private var cxAccelerometerMotion = 0F

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
        canvas?.drawColor(Color.WHITE)
        var cx = canvas!!.width / 2F
        var cy = canvas!!.height - 100F
        Ball(cx + cxAccelerometerMotion, cy, Color.RED).draw(canvas)
    }

    fun setAccelerometerMotion(cxMotion: Float) {
        this.cxAccelerometerMotion = cxMotion
    }
}
