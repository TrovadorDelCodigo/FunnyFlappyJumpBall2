package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import fr.m2dl.todo.funnyflappyjumpball2.engine.forEachOptimized
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

/**
 * Represents the scrolling terrain of the game.
 * The terrain contains the obstacles the player must dodge.
 *
 * The terrain is made out of 3 rotating tiles that make an
 * infinite scrolling terrain.
 */
class Terrain: GameObject() {
    private val scrollSpeed = 0.30f

    private lateinit var tiles: Array<TerrainTile>

    private val stopTerrainHandler: (Any) -> Unit = {
        // TODO : stop tiles
    }

    override fun init() {
        tiles = arrayOf<TerrainTile>(
            TerrainTile(-viewport.height),
            TerrainTile(0.0f),
            TerrainTile(viewport.height)
        )
        tiles.forEach(this::addChild)

        // We put obstacles on the first tile so that
        // the player can begin the game on the second tile
        // without dying... Obstacles will be put on the
        // third tile on the next tile rotation
        tiles[0].arrangeObstacles()

        signalManager.subscribe("lostInAGloryHoleSignal", stopTerrainHandler)
    }

    override fun deinit() {
    }

    override fun update(delta: Long) {
        tiles.forEachOptimized {
            it.moveTo(it.x, it.y + delta * scrollSpeed)
        }

        rotateTilesIfLastOneIsOutOfViewport()
    }

    private fun rotateTilesIfLastOneIsOutOfViewport() {
        val lastTile = tiles[2]
        if (lastTile.y > viewport.height) {
            rotateTiles()
        }
    }

    private fun rotateTiles() {
        val lastTile = tiles[2]
        tiles[2] = tiles[1]
        tiles[1] = tiles[0]
        tiles[0] = lastTile
        lastTile.moveTo(lastTile.x, tiles[1].y - viewport.height)
        lastTile.arrangeObstacles()
    }

    override fun draw(canvas: Canvas) {
    }
}
