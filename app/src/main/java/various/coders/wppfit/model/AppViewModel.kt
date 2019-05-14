package various.coders.wppfit.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.os.AsyncTask
import dagger.internal.DaggerCollections
import kotlinx.coroutines.*
import various.coders.wppfit.model.database.AppDatabase
import various.coders.wppfit.model.database.entities.Exercise
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.model.database.entities.User
import various.coders.wppfit.model.di.DBModule
import various.coders.wppfit.model.di.DaggerDBComponent
import java.lang.Exception
import javax.inject.Inject

class AppViewModel: ViewModel() {
    @Inject lateinit var dbModule: DBModule
    private lateinit var db: AppDatabase

    lateinit var currentUser: LiveData<User>
        private set
    lateinit var meals: LiveData<List<Meal>>
        private set
    lateinit var exercises: LiveData<List<Exercise>>
        private set

    init {
        DaggerDBComponent.builder().build().inject(this)
    }

    fun getDb(context: Context) {
        db = dbModule.provideDb(context)
    }

    fun getNewestUser(): LiveData<User> {
        return db.userDao().getNewestUser()
    }

    fun setCurrentUser(id: Int) {
        currentUser = db.userDao().getUser(id)
    }

    fun insertUser(user: User) {
        GlobalScope.launch {
            db.userDao().newUser(user)
        }
    }

    fun updateUser(user: User) {
        GlobalScope.launch {
            db.userDao().updateUser(user)
        }
    }
}