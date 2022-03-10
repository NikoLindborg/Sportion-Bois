package fi.sportionbois.sportion.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fi.sportionbois.sportion.database.ActivityDB
import fi.sportionbois.sportion.entities.LocationDataPoint
import fi.sportionbois.sportion.entities.SportActivity
import fi.sportionbois.sportion.repositories.SportActivityRepository
import kotlinx.coroutines.launch

/**
 * Viewmodel for location & sportactivity data
 **/

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private var _travelledDistance: MutableLiveData<Float> = MutableLiveData(0f)
    var travelledDistance: LiveData<Float> = _travelledDistance
    var selected: MutableLiveData<Boolean> = MutableLiveData(false)
    var reps: MutableLiveData<Long> = MutableLiveData(0)
    var weight: MutableLiveData<Long> = MutableLiveData(0)
    var sportType: MutableLiveData<String> = MutableLiveData("")

    private var _locationData: MutableLiveData<MutableList<Location>> =
        MutableLiveData(mutableListOf())

    //  Livedata variable for storing the activityId of current activity. Used for binding
    //  LocationDataPoint entities to SportActivity entity
    private var _currentActivityId: MutableLiveData<Int> = MutableLiveData(0)
    var currentActivityId: LiveData<Int> = _currentActivityId

    private val activityDB = ActivityDB.get(application)
    private var allData: LiveData<List<SportActivity>>
    private val sportAcivityRepository: SportActivityRepository

    init {
        val saDB = ActivityDB.get(application).sportActivityDao()
        sportAcivityRepository = SportActivityRepository(saDB)
        allData = sportAcivityRepository.getAllData()
    }

    //  Returns LocationDataPoints for the given LocationActivity
    fun getDataPointsForId(activityId: Int): LiveData<MutableList<LocationDataPoint>> =
        activityDB.locationDataPointDao().getLocationDataPointsForId(activityId = activityId)

    //  Receive the routes average speed calculated in the DAO
    fun getLocationAvgSpeed(activityId: Int): LiveData<Float> =
        activityDB.locationDataPointDao().getLocationAvgSpeed(activityId)

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

    //  Update distance to that will be used in the TrackingActive view
    fun updateLocationDistanceTo(distance: Float) {
        _travelledDistance.value = distance
    }

    //  Update new location to the locationData
    fun updateLocationData(location: Location) {
        _locationData.value?.add(location)
    }

    //  Update the current activityId
    fun updateCurrentActivityId(activityId: Int) {
        _currentActivityId.value = activityId
    }

    //  Insert endtime into activity
    fun insertEndTime(activityId: Int, endTime: Long) {
        viewModelScope.launch {
            activityDB.sportActivityDao().insertEndTime(activityId, endTime)
        }
    }

    //  Get latest activity id
    fun getLatestActivityId(): LiveData<Int> =
        activityDB.sportActivityDao().getLatestActivityId()

    //  Get all sport activity data
    fun getAllData(): LiveData<List<SportActivity>> =
        allData

    //  Get activity by id
    fun getActivityById(activityId: Int): LiveData<SportActivity> =
        sportAcivityRepository.getActivityById(activityId)

    //  Insert heart rate to database
    fun insertAvgHeartRate(activityId: Int, avgHeartRate: Float) {
        viewModelScope.launch {
            activityDB.sportActivityDao().insertAvgHeartRate(activityId, avgHeartRate)
        }
    }

    //  Get average heart rate by activity id
    fun getAvgHeartRateById(activityId: Int): LiveData<Float> =
        activityDB.sportActivityDao().getAvgHeartRateById(activityId)
}
