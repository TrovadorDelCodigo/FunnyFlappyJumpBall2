package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

private const val FPS_TEXT_MARGIN = 4f
private const val FPS_TEXT_FONT_SIZE = 16f
private const val FPS_TEXT_FORMAT = "FPS: %.2f"
private const val FPS_UPDATE_MILLIS = 250L

class FPSCounter: GameObject() {

    private var lastDrawTimeNano = 0L
    private var timeSinceFpsUpdate = 0L
    private var mustUpdateFps = true
    private var fpsString = "FPS:"
    private lateinit var paint: Paint

    override fun init() {
        paint = Paint()
    }

    override fun deinit() {
    }

    override fun update(delta: Long) {
        timeSinceFpsUpdate += delta
        mustUpdateFps = timeSinceFpsUpdate > FPS_UPDATE_MILLIS
    }

    override fun draw(canvas: Canvas) {
        val timeNano = System.nanoTime()
        val elapsedMillis = (timeNano - lastDrawTimeNano) / 1_000_000f
        val fps = (1000 / elapsedMillis)

        // We don't create the FPS string 60 times per second.
        if (mustUpdateFps) {
            fpsString = FPS_TEXT_FORMAT.format(fps)
            timeSinceFpsUpdate = 0L
        }

        paint.color = Color.BLACK
        canvas.drawRect(globalX, globalY, globalX + 100f,
                globalY + FPS_TEXT_FONT_SIZE + 2 * FPS_TEXT_MARGIN, paint)

        paint.color = Color.WHITE
        paint.textSize = FPS_TEXT_FONT_SIZE
        canvas.drawText(fpsString, globalX + FPS_TEXT_MARGIN,
                globalY + FPS_TEXT_MARGIN + FPS_TEXT_FONT_SIZE, paint)

        lastDrawTimeNano = timeNano
    }
}
