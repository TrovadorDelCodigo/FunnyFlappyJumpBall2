package fr.m2dl.todo.funnyflappyjumpball2.engine

import android.content.res.Resources
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

interface GameEngineContext {
    val viewport: GameViewport
    val resources: Resources
    fun initGameObject(gameObject: GameObject)
}
