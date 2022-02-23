package fi.sportionbois.sportion.entities

import androidx.room.Embedded
import androidx.room.Relation

class UserLocationActivities {
    @Embedded
    var user: User? = null

    @Relation(parentColumn = "uid", entityColumn = "user")
    var activities: List<UserLocationActivities>? = null
}