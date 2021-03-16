package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Looper
import android.view.MotionEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.AccelerometerEventListener
import fr.m2dl.todo.funnyflappyjumpball2.engine.TouchScreenEventListener
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.CircleCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.AccelerometerEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.TouchScreenEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject
import android.os.Handler
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject
import kotlin.math.abs
import kotlin.math.sin

private const val JUMP_DURATION_MILIS =  250L

const val BALL_RADIUS = 50f

class Ball(
        x: Float,
        y: Float,
        private val radius: Float,
        private val sensibility: Int,
        color: Int
): CollidableGameObject<CircleCollider>(x, y, CircleCollider()),
        AccelerometerEventListener, TouchScreenEventListener {
    // TODO : A mettre dans le terrain ??
    private var leftSideBorder: Float = 0F
    private var rightSideBorder: Float = 0F

    private val borderLength = radius + 20F

    private var dynamicRadius = radius
    private var jumping: Boolean = false

    private var isLongTouch = false
    private val longTouchDelay = 3000L
    private lateinit var longTouchHandler: Handler

    private lateinit var currentCollisions: List<GameObject>

    override fun init() {
        leftSideBorder = 0F + borderLength
        rightSideBorder = viewport.width - borderLength
        longTouchHandler = Handler(Looper.getMainLooper())
    }

    override fun deinit() {
        //TODO("Not yet implemented")
    }

    private var paint = Paint(color)
    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, dynamicRadius, paint)
    }

    override fun update(delta: Long) {
        updateCollider()
        currentCollisions = checkCollisions()
        blockIfWallCollision()
        fallOutIfHoleCollision(delta)
        jumpWhenTouchScreen(delta)
    }

    private fun updateCollider() {
        collider.let {
            it.globalX = globalX
            it.globalY = globalY
            it.radius = radius
        }
    }

    private fun blockIfWallCollision() {
        val wallCollisions = currentCollisions.filterIsInstance<Wall>()
        if (wallCollisions.isNotEmpty()) {
            val newY = wallCollisions[0].globalY + dynamicRadius + WALL_HEIGHT
            moveTo(x, newY)
        }
    }

    private val resizeSpeed = 0.003F
    private var totalTime = 0F
    private fun fallOutIfHoleCollision(delta: Long) {
        val gloryHoleCollisions = currentCollisions.filterIsInstance<Hole>()
        /** test
        if (gloryHoleCollisions.isNotEmpty()) {
            totalTime += delta * resizeSpeed
            dynamicRadius = abs(dynamicRadius * cos(totalTime))
            moveTo(x, gloryHoleCollisions[0].globalY)
        }
        */
    }

    private fun jumpWhenTouchScreen(delta: Long) {
        if (jumping) {
            dynamicRadius = abs(radius + radius * sin(totalTime))
            totalTime += delta * resizeSpeed
            if (sin(totalTime) <= 0F) {
                jumping = false
            }
        }
    }

    override fun onAccelerometerEvent(event: AccelerometerEvent) {
        // TODO : ajoute constante d'estabilité
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
            MotionEvent.ACTION_DOWN -> {
                isLongTouch = false
                jumping = true
                totalTime = 0F
                /**
                longTouchHandler.postDelayed({
                    jumping = false
                    dynamicRadius = radius
                    totalTime = 0F
                }, longTouchDelay)
                */
            }
            /**
            MotionEvent.ACTION_UP -> {
                isLongTouch = false
                jumping = false
                timeSinceFpsUpdate = JUMP_DURATION_MILIS
            }
            */
        }
    }
}
