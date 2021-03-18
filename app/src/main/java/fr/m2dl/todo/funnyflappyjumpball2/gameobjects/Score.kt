package fr.m2dl.todo.funnyflappyjumpball2.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fr.m2dl.todo.funnyflappyjumpball2.engine.gameobjects.GameObject
import kotlin.math.min

private const val SCORE_WIDTH_PERCENT = 50f
private const val SCORE_PADDING = 4f
private const val SCORE_ALPHA = 0.5f

private const val SCORE_TEXT_FONT_SIZE = 24f
private const val SCORE_TEXT_FORMAT = "Score: %d"

const val SCORE_ADD_POINTS_SIGNAL = "score-add-points"

class Score: GameObject() {

    var score = 0
    private var scoreText = SCORE_TEXT_FORMAT.format(score)

    private val backgroundPaint = Paint()
    private val textPaint = Paint()
    private var width = 0f
    private var height = 0f

    private val scoreAddPointsSignalHandler: (Any) -> Unit = { points ->
        if (points is Int) {
            score += points
            scoreText = SCORE_TEXT_FORMAT.format(score)
        }
    }

    override fun init() {
        backgroundPaint.color = Color.BLACK
        backgroundPaint.alpha = (0xff * min(1.0f, SCORE_ALPHA)).toInt()

        textPaint.color = Color.WHITE
        textPaint.textSize = SCORE_TEXT_FONT_SIZE
        textPaint.isAntiAlias = true

        width = viewport.width * (SCORE_WIDTH_PERCENT / 100f)
        height = SCORE_TEXT_FONT_SIZE + 2f * SCORE_PADDING
        moveTo(viewport.width - width, 0f)

        signalManager.subscribe(SCORE_ADD_POINTS_SIGNAL, scoreAddPointsSignalHandler)
    }

    override fun deinit() {
    }

    override fun update(delta: Long) {
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(globalX, globalY, globalX + width, globalY + height, backgroundPaint)
        canvas.drawText(scoreText, globalX + SCORE_PADDING,
                globalY + SCORE_PADDING + SCORE_TEXT_FONT_SIZE, textPaint)
    }
}
