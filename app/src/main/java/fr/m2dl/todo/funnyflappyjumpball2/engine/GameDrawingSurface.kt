package fr.m2dl.todo.funnyflappyjumpball2.engine

import android.graphics.Canvas

interface GameDrawingSurface {
    val viewport: GameViewport
    fun lockAndGetCanvas(): Canvas?
    fun unlockCanvas(canvas: Canvas)
}
