package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.AccelerometerEventListener
import fr.m2dl.todo.funnyflappyjumpball2.engine.TouchScreenEventListener
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.CircleCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.AccelerometerEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.TouchScreenEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject
import kotlin.math.abs
import kotlin.math.sin

class Ball(
        x: Float,
        y: Float,
        private val radius: Float,
        private val sensibility: Int,
        color: Int
): CollidableGameObject<CircleCollider>(x, y, CircleCollider()),
        AccelerometerEventListener, TouchScreenEventListener {
    private var leftSideBorder: Float = 0F
    private var rightSideBorder: Float = 0F
    private val borderLength = radius + 20F

    private var jumping: Boolean = false
    private var paint = Paint(color)
    private var dynamicRadius = radius

    override fun init() {
        leftSideBorder = 0F + borderLength
        rightSideBorder = viewport.width - borderLength
    }

    override fun deinit() {
        //TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, dynamicRadius, paint)
    }

    private val resizeSpeed = 0.001F
    private var totalTime = 0F
    override fun update(delta: Long) {
        collider.let {
            it.globalX = globalX
            it.globalY = globalY
            it.radius = radius
        }

        // Manage collisions
        var collisions = checkCollisions()
        val rectangleCollisions = collisions.filter { it is SimpleRect }
        val circleCollisions = collisions.filter { it is CoolCircle }
        if (rectangleCollisions.isNotEmpty()) {
            // paint.color = Color.RED
        }
        if (circleCollisions.isNotEmpty()) {
            // paint.color = Color.YELLOW
        }

        // Manage jumping
        if (jumping) {
            totalTime += delta * resizeSpeed
            dynamicRadius = abs(radius + radius * sin(totalTime))
        }
    }

    override fun onAccelerometerEvent(event: AccelerometerEvent) {
        var motion = reverseDirection(event.x) * sensibility
        var newX = calculateNewX(x + motion)
        moveTo(newX, y)
    }

    private fun reverseDirection(x: Float) = x * -1F

    private fun calculateNewX(value: Float): Float {
        return when {
            value <= leftSideBorder -> leftSideBorder
            value >= rightSideBorder -> rightSideBorder
            else -> value
        }
    }

    override fun onTouchScreenEvent(event: TouchScreenEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> jumping = true // Faire un handler
            MotionEvent.ACTION_UP -> jumping = false
        }
    }
}
