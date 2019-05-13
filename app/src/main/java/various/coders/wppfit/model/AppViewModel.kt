package various.coders.wppfit.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.os.AsyncTask
import dagger.internal.DaggerCollections
import kotlinx.coroutines.*
import various.coders.wppfit.model.database.AppDatabase
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.model.database.entities.User
import various.coders.wppfit.model.di.DBModule
import various.coders.wppfit.model.di.DaggerDBComponent
import java.lang.Exception
import javax.inject.Inject

class AppViewModel: ViewModel() {
    @Inject lateinit var dbModule: DBModule
    private lateinit var db: AppDatabase

    lateinit var currentUser: User
    lateinit var meals: List<Meal>
        private set

    init {
        DaggerDBComponent.builder().build().inject(this)
    }

    fun getDb(context: Context) {
        db = dbModule.provideDb(context)
    }

    fun setCurrentUser(id: Int) {
        GlobalScope.launch {
            val user = db.userDao().getUser(id)
            if (user.isNotEmpty()) currentUser = user[0]
            else throw Exception("The user doesn't exist")
        }
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