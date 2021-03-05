package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.impl.GameObject
import kotlin.random.Random

/**
 * Represents a tile of the scrolling terrain.
 * @see Terrain More info on tiles.
 */
class TerrainTile(
        y: Float,
        private val color: Int
): GameObject(0.0f, y) {

    private var width = 0.0f
    private var height = 0.0f

    override fun init() {
        width = viewport.width
        height = viewport.height

        // TODO remove this test
        for (i in 0..Random.nextInt(10)) {
            addChild(CoolCircle(
                    Random.nextFloat() * viewport.width,
                    Random.nextFloat() * (viewport.height - 100f) + 50f,
                    Random.nextFloat() * 100f,
                    Color.YELLOW))
        }
    }

    override fun update(delta: Long) {
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color
        canvas.drawRect(globalX, globalY, globalX + width, globalY + height, paint)
    }
}
