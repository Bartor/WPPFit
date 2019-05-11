package various.coders.wppfit.model

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import various.coders.wppfit.model.daos.MealDao
import various.coders.wppfit.model.daos.UserDao
import javax.inject.Singleton

const val NAME = "WPPFit_database"

@Module
class DBModule {
    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, NAME).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideMealDao(appDatabase: AppDatabase): MealDao {
        return appDatabase.mealDao()
    }
}