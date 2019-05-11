package various.coders.wppfit.model.daos

import androidx.room.Dao
import androidx.room.Query
import various.coders.wppfit.model.entities.Meal

@Dao
interface MealDao {
    @Query("SELECT * FROM meal WHERE user = :userId AND time >= date('now', 'start of day')")
    fun getDayMeals(userId: Int): List<Meal>
}