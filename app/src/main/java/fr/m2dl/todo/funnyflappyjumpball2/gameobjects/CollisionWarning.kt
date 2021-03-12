package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

private const val WIDTH = 30f
private const val HEIGHT = 30f

class CollisionWarning: GameObject() {

    private var handler = Handler()
    private var collisionDetected = false
    private lateinit var paint: Paint

    private val resetCollision = Runnable {
        collisionDetected = false
    }

    private val collisionHandler: (Any) -> Unit = {
        collisionDetected = true
        handler.removeCallbacks(resetCollision)
        handler.postDelayed(resetCollision, 2000)
    }

    override fun init() {
        paint = Paint()
        moveTo(viewport.width - WIDTH, 0f)
        signalManager.subscribe("any-collision", collisionHandler)
    }

    override fun deinit() {
        signalManager.unsubscribe("any-collision", collisionHandler)
    }

    override fun update(delta: Long) {
    }

    override fun draw(canvas: Canvas) {
        paint.color = if (collisionDetected) Color.BLACK else Color.WHITE
        canvas.drawRect(globalX, globalY, globalX + WIDTH, globalY + HEIGHT, paint)
    }
}
