package fi.sportionbois.sportion.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fi.sportionbois.sportion.entities.LocationDataPoint
import fi.sportionbois.sportion.entities.SportActivity
import fi.sportionbois.sportion.entities.User

@Database(entities = [(SportActivity::class), (User::class), (LocationDataPoint::class)], version = 1)
abstract class ActivityDB : RoomDatabase() {
    abstract fun sportActivityDao(): SportActivityDao
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
                        ActivityDB::class.java, "activities.db")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return sInstance!!
        }
    }
}