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
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject
import fr.m2dl.todo.funnyflappyjumpball2.engine.signals.SignalManager
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.sin

private const val ROLL_THRESHOLD = 0.5F
private const val RESIZE_SPEED = 0.003F

class Ball(
        x: Float,
        y: Float,
        private val radius: Float,
        private val sensibility: Int,
        color: Int
): CollidableGameObject<CircleCollider>(x, y, CircleCollider()),
        AccelerometerEventListener, TouchScreenEventListener {
    // TODO : A mettre dans le terrain ??
    // TODO : Si pas de flemme, faire un automate de l'état de la balle
    private var leftSideBorder: Float = 0F
    private var rightSideBorder: Float = 0F
    private val borderLength = radius + 20F

    private var dynamicRadius = radius
    private var jumping: Boolean = false
    private var isInGloryHole = false
    private lateinit var collisions: List<GameObject>
    private var totalTime = 0F

    override fun init() {
        leftSideBorder = borderLength
        rightSideBorder = viewport.width - borderLength
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
        collisions = checkCollisions()
        blockIfObstacle()
        fallOutIfHole(delta)
        jumpWhenTouchScreen(delta)
    }

    private fun updateCollider() {
        collider.let {
            it.globalX = globalX
            it.globalY = globalY
            it.radius = radius
        }
    }

    private fun blockIfObstacle() {
        val wallCollisions = collisions.filterIsInstance<Wall>()
        if (wallCollisions.isNotEmpty()) {
            val newY = wallCollisions[0].globalY + dynamicRadius + WALL_HEIGHT
            moveTo(x, newY)
        }
    }

    private var tt = 1F
    private fun fallOutIfHole(delta: Long) {
        val gloryHoleCollisions = collisions.filterIsInstance<Hole>()
        if (!jumping && gloryHoleCollisions.isNotEmpty()) {
            isInGloryHole = true
            signalManager.sendSignal("lostInAGloryHoleSignal", true)
        }
        if (isInGloryHole) {
            tt += delta * RESIZE_SPEED
            dynamicRadius = radius - exp(tt)
        }
    }

    private fun jumpWhenTouchScreen(delta: Long) {
        if (jumping) {
            dynamicRadius = abs(radius + radius * sin(totalTime))
            totalTime += delta * RESIZE_SPEED
            if (isInGround()) {
                jumping = false
            }
        }
    }

    private fun isInGround(): Boolean {
        return sin(totalTime) <= 0F
    }

    override fun onAccelerometerEvent(event: AccelerometerEvent) {
        var motion = parse(event.x)
        var newX = calculateVerticalPosition(x + motion)
        moveTo(newX, y)
    }

    private fun parse(verticalMotion: Float): Float {
        return when (verticalMotion) {
            in -ROLL_THRESHOLD..ROLL_THRESHOLD -> 0F
            else -> verticalMotion * sensibility * -1F
        }
    }

    private fun calculateVerticalPosition(value: Float): Float {
        return when {
            value <= leftSideBorder -> leftSideBorder
            value >= rightSideBorder -> rightSideBorder
            else -> value
        }
    }

    override fun onTouchScreenEvent(event: TouchScreenEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            jumping = true
            totalTime = 0F
        }
    }
}
