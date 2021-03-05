package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.impl.GameObject
import kotlin.math.abs
import kotlin.math.cos

// TODO remove this test
class CoolCircle(
        x: Float,
        y: Float,
        val radius: Float,
        val color: Int
): GameObject(x, y) {

    private val resizeSpeed = 0.001f

    private var dynamicRadius = 0.0f
    private var totalTime = 0.0f

    override fun init() {
    }

    override fun update(delta: Long) {
        totalTime += delta * resizeSpeed
        dynamicRadius = abs(radius * cos(totalTime))
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color
        canvas.drawCircle(globalX, globalY, dynamicRadius, paint)
    }
}
