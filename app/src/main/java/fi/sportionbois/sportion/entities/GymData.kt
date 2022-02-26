package fi.sportionbois.sportion.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = SportActivity::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = ["activityId"],
        childColumns = ["activity"]
    )]
)
data class GymData(
    val activity: Int,
    val weight: Long?,
    val reps: Long?,
    val rpe: Long?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    override fun toString() = "$activity activityId"
}