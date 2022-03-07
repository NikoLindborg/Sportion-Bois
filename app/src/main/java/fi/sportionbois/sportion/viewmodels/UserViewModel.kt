package fi.sportionbois.sportion.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import fi.sportionbois.sportion.database.ActivityDB
import fi.sportionbois.sportion.entities.User
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val activityDB = ActivityDB.get(application)

    //  For possible future use
    fun getAll(): LiveData<List<User>> = activityDB.userDao().getAll()

    //  Gets the only username in Room
    fun getInsertedUser(): LiveData<String> = activityDB.userDao().getInsertedUser()

    //  Inserts a user to database
    fun insert(user: User) {
        viewModelScope.launch {
            activityDB.userDao().insert(user = user)
        }
    }
}