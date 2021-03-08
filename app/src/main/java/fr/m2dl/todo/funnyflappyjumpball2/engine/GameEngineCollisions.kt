package fr.m2dl.todo.funnyflappyjumpball2.engine

import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.CollidableGameObject
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject

interface GameEngineCollisions {
    fun checkCollisions(collidableGameObject: CollidableGameObject<*>): List<GameObject>
}
