package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

/**
 * Root GameObject that represents the main game scene.
 */
class Scene : GameObject() {

    override fun init() {
        addChild(Terrain())
        addChild(Ball(viewport.width / 2f, viewport.height / 2f, 50F, 10, Color.MAGENTA))

        // TODO remove these tests
        addChild((FPSCounter()))
        addChild(CollisionWarning())
    }

    override fun deinit() {
    }

    override fun update(delta: Long) {
    }

    override fun draw(canvas: Canvas) {
    }
}
