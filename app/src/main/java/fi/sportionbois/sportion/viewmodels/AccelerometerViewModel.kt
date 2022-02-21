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


class AccelerometerViewModel(application: Application) : AndroidViewModel(application) {
    private val accelerometerLiveData = AccelerometerLiveData()
    val acceleration = Transformations.map(accelerometerLiveData) {
        it[0].let { _ ->
            String.format("x ${it[0]} y ${it[1]} z ${it[2]}")
        }
    }

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
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        override fun onActive() {
            sensorManager.unregisterListener(this)
        }

        fun activate() {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        fun pause() {
            sensorManager.unregisterListener(this)
        }

        override fun onInactive() {
            sensorManager.unregisterListener(this)
        }

        override fun onSensorChanged(p0: SensorEvent?) {

            p0.let {
                if (it != null) {
                    value = it.values
                }
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            //no use for this
        }

    }

}