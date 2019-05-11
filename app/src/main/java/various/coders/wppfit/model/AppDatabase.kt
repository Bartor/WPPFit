package various.coders.wppfit.model

import androidx.room.Database
import androidx.room.RoomDatabase
import various.coders.wppfit.model.daos.UserDao
import various.coders.wppfit.model.entities.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}