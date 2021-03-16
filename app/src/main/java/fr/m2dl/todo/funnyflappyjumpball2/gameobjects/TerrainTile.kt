package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.R
import fr.m2dl.todo.funnyflappyjumpball2.engine.cache.bitmap.BitmapCache
import fr.m2dl.todo.funnyflappyjumpball2.engine.forEachOptimized
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject
import kotlin.random.Random

private const val TILE_ROW_COUNT = 3
private const val TILE_COLUMN_COUNT = 3

private data class TerrainRow(
        val obstacles: List<GameObject>,
        var crossed: Boolean
)

/**
 * Represents a tile of the scrolling terrain.
 * @see Terrain More info on tiles.
 */
class TerrainTile(y: Float): GameObject(0.0f, y) {

    private var width = 0f
    private var height = 0f
    private var rowHeight = 0f
    private var columnWidth = 0f
    private val obstaclesRows = mutableListOf<TerrainRow>()
    private var ball: Ball? = null

    private val paint = Paint()
    private lateinit var backgroundBitmap: Bitmap

    override fun init() {
        width = viewport.width
        height = viewport.height
        rowHeight = height / TILE_ROW_COUNT
        columnWidth = width / TILE_COLUMN_COUNT

        backgroundBitmap = BitmapCache.getCachedBitmap(viewport.width.toInt(),resources,
                R.drawable.background01, false)
    }

    override fun deinit() {
    }

    override fun update(delta: Long) {
        if (ball == null) {
            ball = parent!!.parent!!.children.filterIsInstance<Ball>()[0]
        }

        obstaclesRows.forEachOptimized {
            if (!it.crossed && it.obstacles.isNotEmpty()
                    && it.obstacles[0].globalY > ball!!.globalY + BALL_RADIUS) {
                signalManager.sendSignal(SCORE_ADD_POINTS_SIGNAL, 50)
                it.crossed = true
            }
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(backgroundBitmap, globalX, globalY, paint)
    }

    /**
     * Removes old obstacles and put new ones randomly.
     * @see Terrain To see when it is called.
     * @TODO Create an obstacles pool ?
     */
    fun arrangeObstacles() {
        obstaclesRows.removeAll { true }
        removeChildren()

        for (row in 1..TILE_ROW_COUNT) {
            val obstacles = mutableListOf<GameObject>()

            for (column in 1..Random.nextInt(1, TILE_COLUMN_COUNT + 1)) {
                val columnX = columnWidth * (column - 1)
                val rowY = rowHeight * (row - 1)

                obstacles += chooseObstacle(columnX, rowY, obstacles)
            }
            obstaclesRows += TerrainRow(obstacles, false)
        }

        for (row in obstaclesRows) {
            for (obstacle in row.obstacles) {
                addChild(obstacle)
            }
        }
    }

    private fun chooseObstacle(columnX: Float, rowY: Float, rowObstacles: List<GameObject>): GameObject {
        val useHole = Random.nextBoolean()
        // If there is already two walls on the row then we need a hole so we can go past the row
        val needHole = rowObstacles.filterIsInstance<Wall>().size == 2

        return when {
            (useHole || needHole) ->
                Hole(columnX, rowY, columnWidth, rowHeight)
            else ->
                Wall(columnX, rowY + rowHeight / 2f - 50f, columnWidth)
        }
    }
}
