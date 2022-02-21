package fi.sportionbois.sportion.viewmodels

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private var _travelledDistance: MutableLiveData<Float> = MutableLiveData(0f)
    private var _locationData: MutableLiveData<MutableList<Location>> = MutableLiveData(mutableListOf())
    var travelledDistance: LiveData<Float> = _travelledDistance
    var locationData: MutableLiveData<MutableList<Location>> = _locationData

    fun updateLocationDistanceTo(distance: Float) {
        _travelledDistance.value = distance
    }

    fun updateLocationData(location: Location) {
        _locationData.value?.add(location)
    }
}