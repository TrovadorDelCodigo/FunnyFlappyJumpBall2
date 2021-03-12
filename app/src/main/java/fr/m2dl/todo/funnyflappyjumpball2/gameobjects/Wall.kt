package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.RectCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject

private const val WALL_HEIGHT = 75f

private const val WALL_SIDE_OFFSET = 10f

class Wall(
        initialX: Float,
        initialY: Float,
        initialWidth: Float
): CollidableGameObject<RectCollider>(initialX, initialY, RectCollider()) {

    var width = initialWidth
    private lateinit var paint: Paint

    override fun init() {
        paint = Paint()
    }

    override fun deinit() {
    }

    override fun update(delta: Long) {
        collider.let {
            it.globalX = globalX
            it.globalY = globalY
            it.width = width
            it.height = WALL_HEIGHT
        }
    }

    override fun draw(canvas: Canvas) {
        paint.color = Color.LTGRAY
        canvas.drawRect(globalX, globalY, globalX + width,
                globalY + WALL_HEIGHT, paint)
        paint.color = Color.DKGRAY
        canvas.drawRect(globalX, globalY + WALL_SIDE_OFFSET,
                globalX + width, globalY + WALL_HEIGHT, paint)
    }
}
