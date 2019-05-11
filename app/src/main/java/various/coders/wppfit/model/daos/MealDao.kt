package various.coders.wppfit.model.daos

import android.arch.persistence.room.*
import various.coders.wppfit.model.entities.Meal
import java.util.*

@Dao
interface MealDao {
    @Query("SELECT * FROM meal WHERE user = :userId AND time >= :fromDate")
    suspend fun getFromDate(userId: Int, fromDate: Date): List<Meal>

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Insert
    suspend fun insertMeal(meal: Meal)
}