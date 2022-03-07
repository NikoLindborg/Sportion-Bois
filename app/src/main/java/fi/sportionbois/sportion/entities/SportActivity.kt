package fi.sportionbois.sportion.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.time.LocalDate

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
    val sportType: String,
    val startTime: Long?,
    val endTime: Long?,
    @PrimaryKey(autoGenerate = true)
    val activityId: Int,
) {
    override fun toString() = "$user activityId $activityId"
}