package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

class SimpleRect(
        initialX: Float,
        initialY: Float,
        private val width: Float,
        private val height: Float,
        private val color: Int
): GameObject(initialX, initialY) {

    private lateinit var paint: Paint

    override fun init() {
        paint = Paint()
        paint.color = color
    }

    override fun deinit() {
    }

    override fun update(delta: Long) {
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(globalX, globalY, globalX + width, globalY + height, paint)
    }
}