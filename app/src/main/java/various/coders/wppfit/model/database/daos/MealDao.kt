package various.coders.wppfit.model.database.daos

import android.arch.persistence.room.*
import various.coders.wppfit.model.database.entities.Meal
import java.util.*

@Dao
interface MealDao {
    @Query("SELECT * FROM meal WHERE user = :userId AND time >= :fromDate")
    fun getFromDate(userId: Int, fromDate: Date): List<Meal>

    @Delete
    fun deleteMeal(meal: Meal)

    @Insert
    fun insertMeal(meal: Meal)
}