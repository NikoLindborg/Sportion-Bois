package fi.sportionbois.sportion.database

import androidx.lifecycle.LiveData
import androidx.room.*
import fi.sportionbois.sportion.entities.LocationActivity
import fi.sportionbois.sportion.entities.User

@Dao
interface LocationActivityDao {
    @Query("SELECT * FROM locationactivity")
    fun getAll(): LiveData<List<LocationActivity>>

    @Query("SELECT * FROM locationactivity WHERE locationactivity.user = :username")
    fun getUserActivities(username: String): LiveData<LocationActivity>

    @Query("SELECT activityId FROM locationactivity ORDER BY activityId DESC LIMIT 1")
    fun getLatestActivityId(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationActivity: LocationActivity): Long
}