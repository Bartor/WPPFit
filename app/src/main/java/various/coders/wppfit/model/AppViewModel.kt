package various.coders.wppfit.model

import android.arch.lifecycle.ViewModel
import android.content.Context
import dagger.internal.DaggerCollections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import various.coders.wppfit.model.database.AppDatabase
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.model.database.entities.User
import various.coders.wppfit.model.di.DBModule
import various.coders.wppfit.model.di.DaggerDBComponent
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
        CoroutineScope(Dispatchers.IO).launch {
            db = dbModule.provideDb(context)
            println(db.toString())
        }
    }
}