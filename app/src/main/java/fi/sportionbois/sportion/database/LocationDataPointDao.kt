package fi.sportionbois.sportion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fi.sportionbois.sportion.entities.LocationDataPoint

/**
 * DAO for LocationDataPoint
 **/

@Dao
interface LocationDataPointDao {
    //  Receive LocationDataPoints from the database based on inserted activityId value
    @Query("SELECT * FROM locationdatapoint WHERE locationdatapoint.activity = :activityId")
    fun getLocationDataPointsForId(activityId: Int): LiveData<MutableList<LocationDataPoint>>

    //  Receive average speed value based on inserted activityId value
    @Query("SELECT AVG(speed) FROM locationdatapoint WHERE locationdatapoint.activity = :activityId ")
    fun getLocationAvgSpeed(activityId: Int): LiveData<Float>

    //  Insert LocationDataPoints to Room database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationDataPoint: LocationDataPoint): Long
}