package fi.sportionbois.sportion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fi.sportionbois.sportion.entities.User

/**
 * DAO for User
 **/

@Dao
interface UserDao {
    //  Get all users
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    //  Get LocationActivities based on inserted username
    @Query("SELECT * FROM user WHERE user.username = :username")
    fun getUserLocationActivities(username: String): LiveData<User>

    //  Get inserted user (Application let's you create only one user so it will be the first)
    @Query("SELECT username FROM user ORDER BY username DESC LIMIT 1")
    fun getInsertedUser(): LiveData<String>

    //  Insert user to Room Database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User): Long
}