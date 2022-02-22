package fi.sportionbois.sportion.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        onDelete = CASCADE,
        parentColumns = ["username"],
        childColumns = ["user"]
    )]
)
data class LocationActivity(
    val user: String,
    @PrimaryKey(autoGenerate = true)
    val activityId: Int
) {
    override fun toString() = "$user activityId $activityId"
}