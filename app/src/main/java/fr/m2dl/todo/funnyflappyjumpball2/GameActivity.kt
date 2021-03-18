package fr.m2dl.todo.funnyflappyjumpball2

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.AccelerometerEvent
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.TouchScreenEvent
import kotlin.random.Random

class GameActivity : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var gameView: GameView

    private lateinit var musicIds: Array<Int>
    private val mediaPlayers = mutableListOf<MediaPlayer>()
    private val startMusicHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        setupSensorManagerAndSensors()
        gameView = GameView(this)
        setContentView(gameView)
        setupMediaPlayers()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyMediaPlayers()
    }

    private fun setupWindow() {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    private fun setupMediaPlayers() {
        musicIds = listOf(
                R.raw.elevator_music,
                R.raw.bossa_nova_muzak,
                R.raw.nice_recorder,
                R.raw.hold_on_a_minute,
                R.raw.elevator_music,
                R.raw.bossa_nova_muzak,
                R.raw.nice_recorder,
                R.raw.hold_on_a_minute,
        ).shuffled().toTypedArray()

        musicIds.forEach {
            val mediaPlayer = MediaPlayer.create(this, it)
            mediaPlayers += mediaPlayer
        }
        mediaPlayers.forEachIndexed { i, mediaPlayer ->
            mediaPlayer.setNextMediaPlayer(mediaPlayers[(i + 1) % musicIds.size])
            mediaPlayer.setOnCompletionListener {
                it.seekTo(0)
            }
        }
        startMusicHandler.postDelayed({ mediaPlayers[0].start() }, 1000)
    }

    private fun destroyMediaPlayers() {
        mediaPlayers.forEach {
            it.stop()
            it.release()
        }
    }

    private fun setupSensorManagerAndSensors() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val values = event.values
        gameView.notifyEvent(AccelerometerEvent(values[0], values[1], values[2]))
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
         // TODO("Not yet implemented")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gameView.notifyEvent(TouchScreenEvent(event!!.x, event.y, event.action))
        return true
    }
}