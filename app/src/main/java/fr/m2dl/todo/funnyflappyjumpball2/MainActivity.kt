package fr.m2dl.todo.funnyflappyjumpball2

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import fr.m2dl.todo.funnyflappyjumpball2.widgets.GameView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(GameView(this))
    }
}