package fi.sportionbois.sportion.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fi.sportionbois.sportion.database.ActivityDB
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.repositories.GymDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GymViewModel(application: Application) : AndroidViewModel(application) {
    var selected: MutableLiveData<Boolean> = MutableLiveData(false)
    var reps: MutableLiveData<Long> = MutableLiveData(0)
    var weight: MutableLiveData<Long> = MutableLiveData(0)
    var sportType: MutableLiveData<String> = MutableLiveData("")

    private var _selectedItemCount: MutableLiveData<Int> = MutableLiveData(0)
    var selectedItemCount: LiveData<Int> = _selectedItemCount

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

    //  Gets the inserted activity's RPE-value
    fun getRpe(activityId: Int): LiveData<String> =
        gymDataRepository.getRpe(activityId)

    //  Updates the inserted activity's RPE-value
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

    //  Increase selectedItemCount by the inserted value
    //  Used to control the button disabled/enabled in the start tracking view
    fun increaseItemCount(count: Int) {
        _selectedItemCount.value = _selectedItemCount.value?.plus(count)
    }
    //  Decrease selectedItemCount by the inserted value
    fun decreaseItemCount(count: Int) {
        _selectedItemCount.value = _selectedItemCount.value?.minus(count)
    }

    //  Reset the selectedItemCount to 0
    fun resetItemCount() {
        _selectedItemCount.value = 0
    }
}
