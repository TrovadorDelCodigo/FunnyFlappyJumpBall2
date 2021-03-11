package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.AccelerometerEventListener
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.CircleCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.AccelerometerEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject
import kotlin.properties.Delegates

class Ball(
        x: Float,
        y: Float,
        private val radius: Float,
        private val sensibility: Int,
        color: Int
): CollidableGameObject<CircleCollider>(x, y, CircleCollider()), AccelerometerEventListener {
    private var paint = Paint(color)
    private var LEFT_SIDE_BORDER: Float = 0F
    private var RIGHT_SIDE_BORDER: Float = 0F
    private val BORDER_LENGHT = radius + 20F

    override fun init() {
        LEFT_SIDE_BORDER = 0F + BORDER_LENGHT
        RIGHT_SIDE_BORDER = viewport.width - BORDER_LENGHT
    }

    override fun update(delta: Long) {
        // TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, radius, paint)
    }

    override fun onAccelerometerEvent(event: AccelerometerEvent) {
        var motion = reverseDirection(event.x) * sensibility
        var newX = calculateNewX(x + motion)
        moveTo(newX, y)
    }

    private fun reverseDirection(x: Float) = x * -1F

    private fun calculateNewX(value: Float): Float {
        return when {
            value <= LEFT_SIDE_BORDER -> LEFT_SIDE_BORDER
            value >= RIGHT_SIDE_BORDER -> RIGHT_SIDE_BORDER
            else -> value
        }
    }

}