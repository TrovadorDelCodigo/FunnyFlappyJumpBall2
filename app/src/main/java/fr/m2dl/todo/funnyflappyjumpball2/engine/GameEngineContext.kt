package fr.m2dl.todo.funnyflappyjumpball2.engine

import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

interface GameEngineContext {
    val viewport: GameViewport
    fun initGameObject(gameObject: GameObject)
}
