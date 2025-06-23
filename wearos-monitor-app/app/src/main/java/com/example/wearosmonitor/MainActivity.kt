package com.example.wearosmonitor

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import kotlin.math.roundToInt

class MainActivity : Activity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var heartRateSensor: Sensor? = null

    private var stepCount: Int = 0
    private var exerciseStartTime: Long = 0L
    private var isExercising: Boolean = false

    private lateinit var stepsTextView: TextView
    private lateinit var heartRateTextView: TextView
    private lateinit var durationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stepsTextView = findViewById(R.id.stepsTextView)
        heartRateTextView = findViewById(R.id.heartRateTextView)
        durationTextView = findViewById(R.id.durationTextView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        exerciseStartTime = SystemClock.elapsedRealtime()
        isExercising = true
    }

    override fun onResume() {
        super.onResume()
        stepCounterSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        heartRateSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        isExercising = false
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_STEP_COUNTER -> {
                    stepCount = it.values[0].roundToInt()
                    stepsTextView.text = "Steps: $stepCount"
                }
                Sensor.TYPE_HEART_RATE -> {
                    val heartRate = it.values[0].roundToInt()
                    heartRateTextView.text = "Heart Rate: $heartRate bpm"
                }
            }
            if (isExercising) {
                val elapsedMillis = SystemClock.elapsedRealtime() - exerciseStartTime
                val seconds = (elapsedMillis / 1000) % 60
                val minutes = (elapsedMillis / 1000) / 60
                durationTextView.text = String.format("Duration: %02d:%02d", minutes, seconds)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No action needed
    }
}
