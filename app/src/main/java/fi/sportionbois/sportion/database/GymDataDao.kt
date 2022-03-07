package fi.sportionbois.sportion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.entities.LocationDataPoint

@Dao
interface GymDataDao {
    @Query("SELECT * FROM gymdata WHERE gymdata.activity = :activityId")
    fun getGymDataById(activityId: Int): LiveData<GymData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gymData: GymData): Long

    @Query("SELECT * FROM gymdata")
    fun getAllGymData(): LiveData<List<GymData>>
}