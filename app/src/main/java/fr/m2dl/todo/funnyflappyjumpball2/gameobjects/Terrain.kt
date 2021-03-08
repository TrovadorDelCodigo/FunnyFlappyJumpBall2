package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
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

    override fun init() {
        tiles = arrayOf<TerrainTile>(
            TerrainTile(-viewport.height, Color.RED),
            TerrainTile(0.0f, Color.GREEN),
            TerrainTile(viewport.height, Color.BLUE)
        )
        tiles.forEach(this::addChild)
    }

    override fun update(delta: Long) {
        tiles.forEach {
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
    }

    override fun draw(canvas: Canvas) {
    }
}
