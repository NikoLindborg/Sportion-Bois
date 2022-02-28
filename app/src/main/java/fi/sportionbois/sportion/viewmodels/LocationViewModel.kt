package fi.sportionbois.sportion.viewmodels

import android.app.Application
import android.location.Location
import androidx.compose.runtime.State
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fi.sportionbois.sportion.database.ActivityDB
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.entities.LocationDataPoint
import fi.sportionbois.sportion.entities.SportActivity
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private var _travelledDistance: MutableLiveData<Float> = MutableLiveData(0f)
    var travelledDistance: LiveData<Float> = _travelledDistance
    var selected: MutableLiveData<Boolean> = MutableLiveData(false)
    var reps: MutableLiveData<Long> = MutableLiveData(0)
    var weight: MutableLiveData<Long> = MutableLiveData(0)

    private var _locationData: MutableLiveData<MutableList<Location>> =
        MutableLiveData(mutableListOf())
    var locationData: MutableLiveData<MutableList<Location>> = _locationData

    //  Livedata variable for storing the activityId of current activity. Used for binding
    //  LocationDataPoint entities to SportActivity entity
    private var _currentActivityId: MutableLiveData<Int> = MutableLiveData(0)
    var currentActivityId: LiveData<Int> = _currentActivityId

    private val activityDB = ActivityDB.get(application)

    //  Returns a list of all the Sport Activities
    fun getAllSportActivities(username: String): LiveData<List<SportActivity>> =
        activityDB.sportActivityDao().getAll()

    //  Sorts all the SportActivities based on the activityId and returns the last one
    fun getLatestLocationActivity(sportType: String): LiveData<Int> =
        activityDB.sportActivityDao().getLatestActivityId(sportType)

    //  Returns LocationDataPoints for the given LocationActivity
    fun getDataPointsForId(activityId: Int): LiveData<MutableList<LocationDataPoint>> =
        activityDB.locationDataPointDao().getLocationDataPointsForId(activityId = activityId)

    //  Insert LocationActivity with username and activityId information
    fun insert(sportActivity: SportActivity) {
        viewModelScope.launch {
            activityDB.sportActivityDao().insert(sportActivity)
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

    //Insert endtime into activity
    fun insertEndTime(activityId: Int, endTime: Long){
        viewModelScope.launch {
            activityDB.sportActivityDao().insertEndTime(activityId, endTime)
        }
    }

    //Insert reps & weight to room
    fun insertGymData(gymData: GymData){
        viewModelScope.launch {
            activityDB.gymDataDao().insert(gymData)
        }
    }

}
