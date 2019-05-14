package various.coders.wppfit.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import kotlinx.coroutines.*
import various.coders.wppfit.model.database.AppDatabase
import various.coders.wppfit.model.database.entities.Exercise
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.model.database.entities.User
import various.coders.wppfit.model.di.DBModule
import various.coders.wppfit.model.di.DaggerDBComponent
import java.util.*
import javax.inject.Inject

class AppViewModel: ViewModel() {
    @Inject lateinit var dbModule: DBModule
    private lateinit var db: AppDatabase

    var targetDays = -1
    var targetWeight = -1f

    lateinit var currentUser: LiveData<User>
        private set
    lateinit var meals: LiveData<List<Meal>>
        private set
    lateinit var exercises: LiveData<List<Exercise>>
        private set

    init {
        DaggerDBComponent.builder().build().inject(this)
    }

    fun getMeals(user: Int, date: Date) {
        meals = db.mealDao().getFromDate(user, date)
    }

    fun getExercises(user: Int, date: Date) {
        exercises = db.exerciseDao().getFromDate(user, date)
    }

    fun getDb(context: Context) {
        db = dbModule.provideDb(context)
//        eliminate null references
//        meals = db.mealDao().getFromDate(-1, Date())
//        exercises = db.exerciseDao().getFromDate(-1, Date())
    }

    fun getNewestUser(): LiveData<User> {
        return db.userDao().getNewestUser()
    }

    fun setCurrentUser(id: Int) {
        currentUser = db.userDao().getUser(id)
    }

    fun insertMeal(meal: Meal) {
        GlobalScope.launch {
            db.mealDao().insertMeal(meal)
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