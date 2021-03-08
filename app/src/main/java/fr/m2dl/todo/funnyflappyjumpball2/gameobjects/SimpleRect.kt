package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.RectCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject

class SimpleRect(
        initialX: Float,
        initialY: Float,
        private val width: Float,
        private val height: Float,
        private val color: Int
): CollidableGameObject<RectCollider>(initialX, initialY, RectCollider()) {

    override fun init() {
    }

    override fun update(delta: Long) {
        collider.let {
            it.globalX = globalX
            it.globalY = globalY
            it.width = width
            it.height = height
        }
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color

        val collisions = checkCollisions()
        if (collisions.isNotEmpty()) {
            paint.color = Color.BLACK
        }

        canvas.drawRect(globalX, globalY, globalX + width, globalY + height, paint)
    }
}