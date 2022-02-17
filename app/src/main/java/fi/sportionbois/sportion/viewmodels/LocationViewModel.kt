package fi.sportionbois.sportion.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private var _travelledDistance: MutableLiveData<Float> = MutableLiveData(0f)
    var travelledDistance: LiveData<Float> = _travelledDistance

    fun updateLocationData(distance: Float) {
        //_travelledDistance.value = (travelledDistance.value?.plus(distance))
        _travelledDistance.value = distance
    }
}