package fi.sportionbois.sportion.repositories

import androidx.lifecycle.LiveData
import fi.sportionbois.sportion.database.GymDataDao
import fi.sportionbois.sportion.entities.GymData

class GymDataRepository(private val gymDataDao: GymDataDao) {

    suspend fun insertGymData(gymData: GymData) =
        gymDataDao.insert(gymData)

    fun getDataById(id: Int) =
        gymDataDao.getGymDataById(id)

    fun getAllData(): LiveData<List<GymData>> = gymDataDao.getAllGymData()

}