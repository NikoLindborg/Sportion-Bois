package fi.sportionbois.sportion.repositories

import androidx.lifecycle.LiveData
import fi.sportionbois.sportion.database.SportActivityDao
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.entities.SportActivity

class SportActivityRepository(private val sportActivityDao: SportActivityDao) {
    fun getAllData(): LiveData<List<SportActivity>> = sportActivityDao.getAll()
}