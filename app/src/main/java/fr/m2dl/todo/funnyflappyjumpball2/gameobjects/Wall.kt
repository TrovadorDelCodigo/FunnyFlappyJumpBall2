package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.RectCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject

private const val WALL_WIDTH = 100f
private const val WALL_HEIGHT = 75f

private const val WALL_SIDE_OFFSET = 10f

class Wall(
        initialX: Float,
        initialY: Float
): CollidableGameObject<RectCollider>(initialX, initialY, RectCollider()) {

    override fun init() {
    }

    override fun update(delta: Long) {
        collider.let {
            it.globalX = globalX
            it.globalY = globalY
            it.width = WALL_WIDTH
            it.height = WALL_HEIGHT
        }
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()

        paint.color = Color.LTGRAY
        canvas.drawRect(globalX, globalY, globalX + WALL_WIDTH,
                globalY + WALL_HEIGHT, paint)
        paint.color = Color.DKGRAY
        canvas.drawRect(globalX, globalY + WALL_SIDE_OFFSET,
                globalX + WALL_WIDTH, globalY + WALL_HEIGHT, paint)
    }
}
