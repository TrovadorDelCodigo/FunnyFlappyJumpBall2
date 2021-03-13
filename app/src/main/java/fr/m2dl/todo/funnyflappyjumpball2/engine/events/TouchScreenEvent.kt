package fr.m2dl.todo.funnyflappyjumpball2.engine.events

data class TouchScreenEvent(
        val x: Float,
        val y: Float,
        val action: Int
): GameInputEvent
