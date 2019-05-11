package various.coders.wppfit.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import various.coders.wppfit.model.daos.MealDao
import various.coders.wppfit.model.daos.UserDao
import various.coders.wppfit.model.entities.Meal
import various.coders.wppfit.model.entities.User

@Database(entities = [User::class, Meal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun mealDao() : MealDao
}