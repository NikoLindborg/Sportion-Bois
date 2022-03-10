package fi.sportionbois.sportion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fi.sportionbois.sportion.entities.SportActivity

/**
 * DAO for SportActivity
 **/

@Dao
interface SportActivityDao {
    //  Receive all SportActivites in a descending order to show newest first
    @Query("SELECT * FROM sportactivity ORDER BY activityId DESC")
    fun getAll(): LiveData<List<SportActivity>>

    //  Receive all SportActivites based on the user
    @Query("SELECT * FROM sportactivity WHERE sportactivity.user = :username")
    fun getUserActivities(username: String): LiveData<SportActivity>

    //  Receive latest SportActivity based on inserted activity sport type
    @Query("SELECT activityId FROM sportactivity WHERE sportactivity.sportType = :sportType ORDER BY activityId DESC LIMIT 1")
    fun getLatestLocationActivityId(sportType: String): LiveData<Int>

    // Receive latest SportActivity id
    @Query("SELECT activityId FROM sportactivity ORDER BY activityId DESC LIMIT 1")
    fun getLatestActivityId(): LiveData<Int>

    // Insert SportActivity to Room database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sportActivity: SportActivity): Long

    // Insert end time on existing entity
    @Query("UPDATE sportactivity SET endTime=:newEndTime where activityId = :activityID")
    suspend fun insertEndTime(activityID: Int, newEndTime: Long)

}