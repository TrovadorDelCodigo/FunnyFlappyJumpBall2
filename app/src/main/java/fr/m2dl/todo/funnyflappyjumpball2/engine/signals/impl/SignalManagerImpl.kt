package fr.m2dl.todo.funnyflappyjumpball2.engine.signals.impl

import fr.m2dl.todo.funnyflappyjumpball2.engine.signals.SignalManager

class SignalManagerImpl: SignalManager {

    private val handlers = mutableMapOf<String, MutableSet<(Any) -> Unit>>()

    override fun subscribe(signalName: String, signalHandler: (Any) -> Unit) {
        if (handlers[signalName] == null) {
            handlers[signalName] = mutableSetOf()
        }
        handlers[signalName]?.add(signalHandler)
    }

    override fun unsubscribe(signalName: String, signalHandler: (Any) -> Unit) {
        handlers[signalName]?.remove(signalHandler)
    }

    override fun sendSignal(signalName: String, signalData: Any) {
        handlers[signalName]?.forEach {
            it(signalData)
        }
    }
}
