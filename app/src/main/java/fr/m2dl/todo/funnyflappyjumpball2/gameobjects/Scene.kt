package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

/**
 * Root GameObject that represents the main game scene.
 */
class Scene: GameObject() {

    override fun init() {
        addChild(Terrain())
        // TODO remove this test
        addChild(CoolCircle(viewport.width / 2f, viewport.height / 2f, 100f, Color.MAGENTA))
    }

    override fun update(delta: Long) {
    }

    override fun draw(canvas: Canvas) {
    }
}
