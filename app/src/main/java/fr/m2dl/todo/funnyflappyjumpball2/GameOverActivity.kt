package fr.m2dl.todo.funnyflappyjumpball2

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import fr.m2dl.todo.funnyflappyjumpball2.dialogs.PlayerNameDialog
import fr.m2dl.todo.funnyflappyjumpball2.score.Score
import fr.m2dl.todo.funnyflappyjumpball2.score.ScoreAdapter

class GameOverActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        setContentView(R.layout.activity_game_over)
        displayScore(intent.getIntExtra("score", 0))
        displayHighScores()
    }

    private fun setupWindow() {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    private fun displayScore(score: Int) {
        val scoreTextView = findViewById<TextView>(R.id.scoreText)
        val scoreText = getString(R.string.score)
        scoreTextView.text = "$scoreText $score"
    }

    private fun displayHighScores() {
        // TODO remove test
        val scores = listOf(
            Score("NICE", 314159265),
            Score("DUKE", 1337),
            Score("JOHN", 42)
        )

        val scoreAdapter = ScoreAdapter(this, R.layout.score_item, scores)
        val scoreListView = findViewById<ListView>(R.id.scoresListView)
        scoreListView.adapter = scoreAdapter

        val playerNameDialog = PlayerNameDialog()
        playerNameDialog.show(supportFragmentManager, "player_name")
    }
}
