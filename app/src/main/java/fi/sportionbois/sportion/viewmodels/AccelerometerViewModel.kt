package fi.sportionbois.sportion.viewmodels

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import java.lang.Math.sqrt


class AccelerometerViewModel(application: Application) : AndroidViewModel(application) {
    private val accelerometerLiveData = AccelerometerLiveData()
    val acceleration = mutableListOf<Float>()
    val accelerationX = mutableListOf<Float>()
    val accelerationY = mutableListOf<Float>()
    val accelerationZ = mutableListOf<Float>()


    fun listen() {
        accelerometerLiveData.activate()
    }

    fun stopListening() {
        accelerometerLiveData.pause()
    }

    inner class AccelerometerLiveData : LiveData<FloatArray>(), SensorEventListener {
        private val sensorManager =
            getApplication<Application>().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        private val accelerometer =
            sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        override fun onActive() {
            sensorManager.unregisterListener(this)
        }

        fun activate() {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        fun pause() {
            sensorManager.unregisterListener(this)
            Log.d("acc x", accelerationX.toString())
        }

        override fun onInactive() {
            sensorManager.unregisterListener(this)
        }

        override fun onSensorChanged(p0: SensorEvent?) {

            p0.let {
                if (it != null) {
                    value = it.values
                    acceleration.add(sqrt((it.values[0] * it.values[0] + it.values[1] * it.values[1] + it.values[2] * it.values[2]).toDouble()).toFloat())
                    accelerationX.add(it.values[0])
                    accelerationY.add(it.values[1])
                    accelerationZ.add(it.values[2])
                }
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            //no use for this
        }

    }

}