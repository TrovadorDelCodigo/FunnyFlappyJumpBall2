package fr.m2dl.todo.funnyflappyjumpball2.engine.impl

import android.graphics.Canvas
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameEngineContext
import fr.m2dl.todo.funnyflappyjumpball2.engine.GameViewport

abstract class GameObject(
    initialX: Float = 0.0f,
    initialY: Float = 0.0f
) {
    var x: Float = initialX
        private set
    var y: Float = initialY
        private set

    /** Make sure this X coordinate to drawing functions */
    val globalX: Float
        get() = x + (parent?.x ?: 0f)

    /** Make sure this Y coordinate to drawing functions */
    val globalY: Float
        get() = y + (parent?.y ?: 0f)

    lateinit var viewport: GameViewport
        private set

    private lateinit var gameEngineContext: GameEngineContext

    var parent: GameObject? = null

    private val mutableChildren = mutableListOf<GameObject>()
    val children: List<GameObject> = mutableChildren

    fun initInternals(gameEngineContext: GameEngineContext, viewport: GameViewport) {
        this.viewport = viewport
        this.gameEngineContext = gameEngineContext
    }

    fun moveTo(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun addChild(gameObject: GameObject) {
        gameObject.parent = this
        mutableChildren += gameObject
        gameEngineContext.initGameObject(gameObject)
        // We place it relative to its parent by default
        gameObject.moveTo(gameObject.x, gameObject.y)
    }

    /**
     * Called on GameObject creation.
     * Use it to setup GameObject children.
     * Be careful on the order you add children or the scene will not render properly.
     */
    abstract fun init()

    /**
     * Called periodically to update GameObject state
     * @param delta Time since last invocation
     */
    abstract fun update(delta: Long)

    /**
     * Called each frame to draw the GameObject
     * @param canvas The canvas to draw on
     */
    abstract fun draw(canvas: Canvas)
}
