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
data class SportActivity(
    val user: String,
    @PrimaryKey(autoGenerate = true)
    val activityId: Int,
    val sportType: String
) {
    override fun toString() = "$user activityId $activityId"
}