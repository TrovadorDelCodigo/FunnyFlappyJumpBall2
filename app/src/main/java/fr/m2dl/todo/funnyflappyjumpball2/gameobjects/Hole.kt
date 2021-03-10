package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.CircleCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject

private const val HOLE_RADIUS = 50f
private const val HOLE_INNER_OFFSET = 4f

class Hole(
        initialX: Float,
        initialY: Float
): CollidableGameObject<CircleCollider>(initialX, initialY, CircleCollider()) {

    override fun init() {
    }

    override fun update(delta: Long) {
        collider.let {
            it.globalX = globalX
            it.globalY = globalY
            it.radius = HOLE_RADIUS
        }
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()

        paint.color = Color.GRAY
        canvas.drawCircle(globalX, globalY, HOLE_RADIUS, paint)
        paint.color = Color.BLACK
        canvas.drawCircle(globalX, globalY + HOLE_INNER_OFFSET,
                HOLE_RADIUS - HOLE_INNER_OFFSET, paint)
    }
}
