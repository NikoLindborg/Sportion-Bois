package fi.sportionbois.sportion.repositories

import androidx.lifecycle.LiveData
import fi.sportionbois.sportion.database.SportActivityDao
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.entities.SportActivity

/**
 * Repository for sport activity entity, handling basic database actions
 **/

class SportActivityRepository(private val sportActivityDao: SportActivityDao) {
    fun getAllData(): LiveData<List<SportActivity>> = sportActivityDao.getAll()

    fun getActivityById(activityID: Int): LiveData<SportActivity> = sportActivityDao.getActivityById(activityID)
}