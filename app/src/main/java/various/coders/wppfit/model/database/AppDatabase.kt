package various.coders.wppfit.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import various.coders.wppfit.model.database.daos.MealDao
import various.coders.wppfit.model.database.daos.UserDao
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.model.database.entities.User

@Database(entities = [User::class, Meal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun mealDao() : MealDao
}