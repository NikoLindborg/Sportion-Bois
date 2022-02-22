package fi.sportionbois.sportion.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = LocationActivity::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = ["activityId"],
        childColumns = ["activity"]
    )]
)
data class LocationDataPoint(
    var activity: Int,
    var lat: Float,
    var lon: Float,
    var speed: Float,
    @PrimaryKey
    var totalDistance: Float
) {
    override fun toString() = "$activity activityId"
}