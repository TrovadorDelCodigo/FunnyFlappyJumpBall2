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

        // TODO remove this test
        /*for (i in 0..Random.nextInt(10)) {
            if (Random.nextBoolean()) {
                addChild(Hole(Random.nextFloat() * viewport.width,
                        Random.nextFloat() * (viewport.height - 100f) + 50f))
            } else {
                addChild(Wall(Random.nextFloat() * (viewport.width - 100f),
                        Random.nextFloat() * (viewport.height - 75f)))
            }
        }*/

        arrangeObstacles()
        for (row in obstacleRows) {
            for (obstacle in row) {
                addChild(obstacle)
            }
        }
    }

    override fun update(delta: Long) {
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color
        canvas.drawRect(globalX, globalY, globalX + width, globalY + height, paint)
    }

    private fun arrangeObstacles() {
        for (row in 1..TILE_ROW_COUNT) {
            val rowObstacles = mutableListOf<GameObject>()

            for (column in 1..Random.nextInt(1, TILE_COLUMN_COUNT + 1)) {
                val columnCenterX = columnWidth * (column - 1) + columnWidth / 2
                val rowCenterY = rowHeight * (row - 1) + rowHeight / 2

                rowObstacles += chooseObstacle(columnCenterX, rowCenterY, rowObstacles)
            }
            obstacleRows += rowObstacles
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
