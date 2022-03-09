package fi.sportionbois.sportion.database

import androidx.lifecycle.LiveData
import androidx.room.*
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.entities.LocationDataPoint

@Dao
interface GymDataDao {
    @Query("SELECT * FROM gymdata WHERE gymdata.activity = :activityId")
    fun getGymDataById(activityId: Int): LiveData<GymData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gymData: GymData): Long

    @Query("UPDATE gymdata SET rpe = :newRpe WHERE gymdata.activity = :activityId")
    fun updateRpe(newRpe: String, activityId: Int)

    @Query("SELECT rpe FROM gymdata WHERE gymdata.activity = :activityId")
    fun getRpe(activityId: Int): LiveData<String>

    @Query("SELECT * FROM gymdata")
    fun getAllGymData(): LiveData<List<GymData>>
}