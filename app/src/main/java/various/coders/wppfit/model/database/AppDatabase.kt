package various.coders.wppfit.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import various.coders.wppfit.model.database.converters.ActivityLevelConverter
import various.coders.wppfit.model.database.converters.ExerciseTypeConverter
import various.coders.wppfit.model.database.converters.TimeConverter
import various.coders.wppfit.model.database.daos.ExerciseDao
import various.coders.wppfit.model.database.daos.MealDao
import various.coders.wppfit.model.database.daos.UserDao
import various.coders.wppfit.model.database.entities.Exercise
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.model.database.entities.User

@Database(entities = [User::class, Meal::class, Exercise::class], version = 2)
@TypeConverters(TimeConverter::class, ExerciseTypeConverter::class, ActivityLevelConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun mealDao() : MealDao
    abstract fun exerciseDao(): ExerciseDao
}