package fi.sportionbois.sportion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fi.sportionbois.sportion.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE user.username = :username")
    fun getUserLocationActivities(username: String): LiveData<User>

    @Query("SELECT username FROM user ORDER BY username DESC LIMIT 1")
    fun getInsertedUser(): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User): Long
}