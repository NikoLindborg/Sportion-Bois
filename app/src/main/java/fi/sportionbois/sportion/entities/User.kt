package fi.sportionbois.sportion.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val username: String,
    val firstname: String,
    val lastname: String
) {
    override fun toString() = "$firstname $lastname ($username)"
}