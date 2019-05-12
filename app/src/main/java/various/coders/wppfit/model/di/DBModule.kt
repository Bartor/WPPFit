package various.coders.wppfit.model.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import various.coders.wppfit.model.database.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

const val NAME = "WPPFit_database"

@Module
class DBModule @Inject constructor() {
    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, NAME).fallbackToDestructiveMigration().build()
    }
}