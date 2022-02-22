package fi.sportionbois.sportion.viewmodels

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.TypeConverter
import fi.sportionbois.sportion.database.ActivityDB
import fi.sportionbois.sportion.entities.LocationActivity
import fi.sportionbois.sportion.entities.LocationDataPoint
import fi.sportionbois.sportion.entities.User
import kotlinx.coroutines.launch
import java.util.*

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private var _travelledDistance: MutableLiveData<Float> = MutableLiveData(0f)
    var travelledDistance: LiveData<Float> = _travelledDistance

    private var _locationData: MutableLiveData<MutableList<Location>> =
        MutableLiveData(mutableListOf())
    var locationData: MutableLiveData<MutableList<Location>> = _locationData

    //  Livedata variable for storing the activityId of current activity. Used for binding
    //  LocationDataPoint entities to LocationActivity entity
    private var _currentActivityId: MutableLiveData<Int> = MutableLiveData(0)
    var currentActivityId: LiveData<Int> = _currentActivityId

    private val activityDB = ActivityDB.get(application)

    //  Returns a list of all the Location Activities
    fun getAllLocationActivities(username: String): LiveData<List<LocationActivity>> =
        activityDB.locationActivityDao().getAll()

    //  Sorts all the LocationActivities based on the activityId and returns the last one
    fun getLatestLocationActivity(): LiveData<Int> =
        activityDB.locationActivityDao().getLatestActivityId()

    //  Returns LocationDataPoints for the given LocationActivity
    fun getDataPointsForId(activityId: Int): LiveData<List<LocationDataPoint>> =
        activityDB.locationDataPointDao().getLocationDataPointsForId(activityId = activityId)

    //  Insert LocationActivity with username and activityId information
    fun insert(locationActivity: LocationActivity) {
        viewModelScope.launch {
            activityDB.locationActivityDao().insert(locationActivity = locationActivity)
        }
    }

    //  Insert LocationDataPoints which includes geolocation and speed data from a location
    fun insertLocationDataPoints(locationDataPoint: LocationDataPoint) {
        viewModelScope.launch {
            activityDB.locationDataPointDao().insert(locationDataPoint = locationDataPoint)
        }
    }

    fun updateLocationDistanceTo(distance: Float) {
        _travelledDistance.value = distance
    }

    fun updateLocationData(location: Location) {
        _locationData.value?.add(location)
    }

    fun updateCurrentActivityId(activityId: Int) {
        _currentActivityId.value = activityId
    }
}
