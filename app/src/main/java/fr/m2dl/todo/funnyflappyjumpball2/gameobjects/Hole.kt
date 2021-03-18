package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.*
import fr.m2dl.todo.funnyflappyjumpball2.R
import fr.m2dl.todo.funnyflappyjumpball2.engine.cache.bitmap.BitmapCache
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.RectCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject

class Hole(
        initialX: Float,
        initialY: Float,
        var width: Float,
        var height: Float
): CollidableGameObject<RectCollider>(initialX, initialY, RectCollider()) {

    private lateinit var holeBitmap: Bitmap
    private lateinit var paint: Paint

    private val holeBitmapY
        get() = globalY + height / 2f - holeBitmap.height / 2f

    override fun init() {
        holeBitmap = BitmapCache.getCachedBitmap(width.toInt(), resources,
                R.drawable.hole01, true)
        paint = Paint()
    }

    override fun deinit() {
    }

    override fun update(delta: Long) {
        val holeBitmapHeight = holeBitmap.height.toFloat()
        collider.let {
            it.globalX = globalX
            it.globalY = holeBitmapY - holeBitmapHeight / 4f
            it.width = width
            it.height = holeBitmapHeight / 2f
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(holeBitmap, globalX, holeBitmapY, paint)
    }
}
