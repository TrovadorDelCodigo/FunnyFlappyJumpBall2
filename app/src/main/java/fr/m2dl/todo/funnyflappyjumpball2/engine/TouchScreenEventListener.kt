package fr.m2dl.todo.funnyflappyjumpball2.engine

import fr.m2dl.todo.funnyflappyjumpball2.engine.events.TouchScreenEvent

interface TouchScreenEventListener {
    fun onTouchScreenEvent(event: TouchScreenEvent)
}
