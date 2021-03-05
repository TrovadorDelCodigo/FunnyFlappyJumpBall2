package fr.m2dl.todo.funnyflappyjumpball2.engine

import fr.m2dl.todo.funnyflappyjumpball2.engine.impl.GameObject

interface GameEngineContext {
    val viewport: GameViewport
    fun initGameObject(gameObject: GameObject)
}
