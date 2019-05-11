package various.coders.wppfit.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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