package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject
import kotlin.random.Random

private const val TILE_ROW_COUNT = 3
private const val TILE_COLUMN_COUNT = 3

/**
 * Represents a tile of the scrolling terrain.
 * @see Terrain More info on tiles.
 */
class TerrainTile(
        y: Float,
        private val color: Int
): GameObject(0.0f, y) {

    private var width = 0f
    private var height = 0f
    private var rowHeight = 0f
    private var columnWidth = 0f
    private val obstacleRows = mutableListOf<List<GameObject>>()

    override fun init() {
        width = viewport.width
        height = viewport.height
        rowHeight = height / TILE_ROW_COUNT
        columnWidth = width / TILE_COLUMN_COUNT
    }

    override fun update(delta: Long) {
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color
        canvas.drawRect(globalX, globalY, globalX + width, globalY + height, paint)
    }

    /**
     * Removes old obstacles and put new ones randomly.
     * @see Terrain To see when it is called.
     */
    fun arrangeObstacles() {
        obstacleRows.removeAll { true }
        removeChildren()

        for (row in 1..TILE_ROW_COUNT) {
            val rowObstacles = mutableListOf<GameObject>()

            for (column in 1..Random.nextInt(1, TILE_COLUMN_COUNT + 1)) {
                val columnCenterX = columnWidth * (column - 1) + columnWidth / 2
                val rowCenterY = rowHeight * (row - 1) + rowHeight / 2

                rowObstacles += chooseObstacle(columnCenterX, rowCenterY, rowObstacles)
            }
            obstacleRows += rowObstacles
        }

        for (row in obstacleRows) {
            for (obstacle in row) {
                addChild(obstacle)
            }
        }
    }

    private fun chooseObstacle(columnCenterX: Float, rowCenterY: Float, rowObstacles: List<GameObject>): GameObject {
        val useHole = Random.nextBoolean()
        // If there is already two walls on the row then we need a hole so we can go past the row
        val needHole = rowObstacles.filterIsInstance<Wall>().size == 2

        return when {
            (useHole || needHole) -> Hole(columnCenterX , rowCenterY)
            else                  -> Wall(columnCenterX - 50f, rowCenterY - 50f)
        }
    }
}
