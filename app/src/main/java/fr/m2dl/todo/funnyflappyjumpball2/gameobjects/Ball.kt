package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Paint

class Ball(
        var cx: Float,
        var cy: Float,
        private val color: Int
) {
    private var size = 100F

    fun draw(canvas: Canvas?) {
        var paint = Paint()
        paint.setColor(color)
        canvas?.drawCircle(cx, cy, size, paint)
    }

}