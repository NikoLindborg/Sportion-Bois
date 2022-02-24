package fi.sportionbois.sportion.database

import androidx.lifecycle.LiveData
import androidx.room.*
import fi.sportionbois.sportion.entities.SportActivity

@Dao
interface SportActivityDao {
    @Query("SELECT * FROM sportactivity")
    fun getAll(): LiveData<List<SportActivity>>

    @Query("SELECT * FROM sportactivity WHERE sportactivity.user = :username")
    fun getUserActivities(username: String): LiveData<SportActivity>

    @Query("SELECT activityId FROM sportactivity WHERE sportactivity.sportType = :sportType ORDER BY activityId DESC LIMIT 1")
    fun getLatestActivityId(sportType: String): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sportActivity: SportActivity): Long
}