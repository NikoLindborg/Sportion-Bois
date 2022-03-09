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
import fi.sportionbois.sportion.repositories.GymDataRepository
import fi.sportionbois.sportion.repositories.SportActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GymViewModel(application: Application) : AndroidViewModel(application) {
    var selected: MutableLiveData<Boolean> = MutableLiveData(false)
    var reps: MutableLiveData<Long> = MutableLiveData(0)
    var weight: MutableLiveData<Long> = MutableLiveData(0)
    var sportType: MutableLiveData<String> = MutableLiveData("")

    //  Livedata variable for storing the activityId of current activity. Used for binding
    //  LocationDataPoint entities to SportActivity entity
    private var _currentActivityId: MutableLiveData<Int> = MutableLiveData(0)
    var currentActivityId: LiveData<Int> = _currentActivityId

    private val gymDataRepository: GymDataRepository
    private var allGymData: LiveData<List<GymData>>

    init{
        val gymDB = ActivityDB.get(application).gymDataDao()
        gymDataRepository = GymDataRepository(gymDB)
        allGymData = gymDataRepository.getAllData()
    }

    //Insert reps & weight to room
    fun insertGymData(gymData: GymData){
        viewModelScope.launch (Dispatchers.IO){
            gymDataRepository.insertGymData(gymData)
        }
    }

    fun getRpe(activityId: Int): LiveData<String> =
        gymDataRepository.getRpe(activityId)


    fun updateRpe(rpe: String, activityId: Int) {
        viewModelScope.launch (Dispatchers.IO){
            gymDataRepository.updateRpe(rpe, activityId)
        }
    }

    //return all gym data
    fun getAllGymData(): LiveData<List<GymData>> =
        allGymData

    //Get gym data by id
    fun getGymDataById(id: Int): LiveData<GymData> =
        gymDataRepository.getDataById(id)

}
