package fr.m2dl.todo.funnyflappyjumpball2

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import fr.m2dl.todo.funnyflappyjumpball2.engine.events.AccelerometerEvent

class MainActivity : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        setupSensorManagerAndSensors()
        gameView = GameView(this)
        setContentView(gameView)
    }

    private fun setupWindow() {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
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
}