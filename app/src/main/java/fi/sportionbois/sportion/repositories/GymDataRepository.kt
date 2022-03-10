package fi.sportionbois.sportion.repositories

import androidx.lifecycle.LiveData
import fi.sportionbois.sportion.database.GymDataDao
import fi.sportionbois.sportion.entities.GymData

/**
 * Repository for gymdata entity, handling basic database actions
 **/

class GymDataRepository(private val gymDataDao: GymDataDao) {

    suspend fun insertGymData(gymData: GymData) =
        gymDataDao.insert(gymData)

    fun updateRpe(rpe: String, activityId: Int) = gymDataDao.updateRpe(rpe, activityId)

    fun getDataById(id: Int) =
        gymDataDao.getGymDataById(id)

    fun getRpe(activityId: Int): LiveData<String> = gymDataDao.getRpe(activityId)

    fun getAllData(): LiveData<List<GymData>> = gymDataDao.getAllGymData()

}