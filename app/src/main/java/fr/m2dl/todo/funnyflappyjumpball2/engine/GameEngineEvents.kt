package fr.m2dl.todo.funnyflappyjumpball2.engine

import fr.m2dl.todo.funnyflappyjumpball2.engine.events.GameInputEvent

interface GameEngineEvents {
    fun notifyEvent(event: GameInputEvent)
}