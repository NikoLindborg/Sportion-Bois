package fi.sportionbois.sportion.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fi.sportionbois.sportion.entities.LocationActivity
import fi.sportionbois.sportion.entities.LocationDataPoint
import fi.sportionbois.sportion.entities.User

@Database(entities = [(LocationActivity::class), (User::class), (LocationDataPoint::class)], version = 1)
abstract class ActivityDB : RoomDatabase() {
    abstract fun locationActivityDao(): LocationActivityDao
    abstract fun locationDataPointDao(): LocationDataPointDao
    abstract fun userDao(): UserDao

    companion object {
        private var sInstance: ActivityDB? = null

        @Synchronized
        fun get(context: Context): ActivityDB {
            if (sInstance == null) {
                sInstance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ActivityDB::class.java, "activities.db"
                    ).build()
            }
            return sInstance!!
        }
    }
}