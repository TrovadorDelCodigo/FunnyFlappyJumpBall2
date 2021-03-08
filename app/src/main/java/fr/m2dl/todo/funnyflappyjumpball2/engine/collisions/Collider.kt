package fr.m2dl.todo.funnyflappyjumpball2.engine.collisions

interface Collider {
    fun collidesWith(collider: Collider): Boolean
}
