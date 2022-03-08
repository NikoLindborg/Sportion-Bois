package fi.sportionbois.sportion.database

import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.room.*
import fi.sportionbois.sportion.entities.SportActivity

@Dao
interface SportActivityDao {
    @Query("SELECT * FROM sportactivity ORDER BY activityId DESC")
    fun getAll(): LiveData<List<SportActivity>>

    @Query("SELECT * FROM sportactivity WHERE sportactivity.user = :username")
    fun getUserActivities(username: String): LiveData<SportActivity>

    @Query("SELECT activityId FROM sportactivity WHERE sportactivity.sportType = :sportType ORDER BY activityId DESC LIMIT 1")
    fun getLatestLocationActivityId(sportType: String): LiveData<Int>

    @Query("SELECT activityId FROM sportactivity ORDER BY activityId DESC LIMIT 1")
    fun getLatestActivityId(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sportActivity: SportActivity): Long

    @Query("UPDATE sportactivity SET endTime=:newEndTime where activityId = :activityID")
    suspend fun insertEndTime(activityID: Int, newEndTime: Long)

}