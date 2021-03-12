package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.content.res.Resources
import android.graphics.*
import fr.m2dl.todo.funnyflappyjumpball2.R
import fr.m2dl.todo.funnyflappyjumpball2.engine.collisions.impl.RectCollider
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject

private const val HOLE_RADIUS = 50f

class Hole(
        initialX: Float,
        initialY: Float,
        var width: Float,
        var height: Float
): CollidableGameObject<RectCollider>(initialX, initialY, RectCollider()) {

    companion object {
        private var holeBitmap: Bitmap? = null
        private var scaledWidth: Int? = null

        fun getHoleBitmap(scaledWidth: Int, resources: Resources): Bitmap {
            if (holeBitmap == null || this.scaledWidth != scaledWidth) {
                val nonScaledBitmap = BitmapFactory.decodeResource(resources, R.drawable.hole01)
                val ratio = scaledWidth / nonScaledBitmap.width.toFloat()
                val scaledHeight = (ratio * nonScaledBitmap.height).toInt()
                holeBitmap = Bitmap.createScaledBitmap(nonScaledBitmap, scaledWidth, scaledHeight, false)
            }
            return holeBitmap!!
        }
    }

    private lateinit var holeBitmap: Bitmap
    private lateinit var paint: Paint

    private val holeBitmapY
        get() = globalY + height / 2f - holeBitmap.height / 2f

    override fun init() {
        holeBitmap = getHoleBitmap(width.toInt(), resources)
        paint = Paint()
    }

    override fun update(delta: Long) {
        collider.let {
            it.globalX = globalX
            it.globalY = holeBitmapY
            it.width = width
            it.height = holeBitmap.height.toFloat()
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(holeBitmap, globalX, holeBitmapY, paint)
    }
}
