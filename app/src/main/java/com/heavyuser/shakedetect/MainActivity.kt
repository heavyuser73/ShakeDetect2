package com.heavyuser.shakedetect

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager : SensorManager
    lateinit var accelerormeterSensor : Sensor

    var lastTime : Long = 0
    var speed : Float = 0.0F
    var lastX : Float = 0.0F
    var lastY : Float = 0.0F
    var lastZ : Float = 0.0F
    var x : Float = 0.0F
    var y : Float = 0.0F
    var z : Float = 0.0F

    var SHAKE_THRESHOLD = 800;
    var DATA_X = SensorManager.DATA_X;
    var DATA_Y = SensorManager.DATA_Y;
    var DATA_Z = SensorManager.DATA_Z;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)



    }

    override fun onStart() {
        super.onStart()
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }

    override fun onStop() {
        super.onStop()
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                var currentTime = System.currentTimeMillis();
                var gabOfTime = (currentTime - lastTime);
                if (gabOfTime > 100) {
                    lastTime = currentTime;
                    x = event.values[SensorManager.DATA_X];
                    y = event.values[SensorManager.DATA_Y];
                    z = event.values[SensorManager.DATA_Z];

                    speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                    if (speed > SHAKE_THRESHOLD) {
                        // 이벤트발생!!
                    }

                    lastX = event.values[DATA_X];
                    lastY = event.values[DATA_Y];
                    lastZ = event.values[DATA_Z];

                    if(speed > 0)
                        Log.d("ZZZ", "speed : " + speed)
                }

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        if(sensor != null) {

        }
    }

}