package fr.m2dl.todo.funnyflappyjumpball2.engine

import fr.m2dl.todo.funnyflappyjumpball2.engine.events.AccelerometerEvent

interface AccelerometerEventListener {
    fun onAccelerometerEvent(event: AccelerometerEvent)
}
