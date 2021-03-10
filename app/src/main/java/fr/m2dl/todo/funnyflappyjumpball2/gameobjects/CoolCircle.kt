package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.CircleCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject
import kotlin.math.abs
import kotlin.math.cos

// TODO remove this test
class CoolCircle(
        x: Float,
        y: Float,
        private val radius: Float,
        private val color: Int
): CollidableGameObject<CircleCollider>(x, y, CircleCollider()) {

    private val resizeSpeed = 0.002f

    private var dynamicRadius = 0.0f
    private var totalTime = 0.0f

    override fun init() {
    }

    override fun update(delta: Long) {
        totalTime += delta * resizeSpeed
        dynamicRadius = abs(radius * cos(totalTime))

        collider.let {
            it.globalX = globalX
            it.globalY = globalY
            it.radius  = dynamicRadius
        }
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color

        val collisions = checkCollisions()
        if (collisions.isNotEmpty()) {
            paint.color = Color.BLACK
        }

        canvas.drawCircle(globalX, globalY, dynamicRadius, paint)
    }
}
