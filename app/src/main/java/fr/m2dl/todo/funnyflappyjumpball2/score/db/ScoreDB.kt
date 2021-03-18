package fr.m2dl.todo.funnyflappyjumpball2.score.db

import fr.m2dl.todo.funnyflappyjumpball2.score.Score

interface ScoreDB {
    fun registerScore(score: Score)
    fun getScores(scoresHandler: (List<Score>) -> Unit)
}
