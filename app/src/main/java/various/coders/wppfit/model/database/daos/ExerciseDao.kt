package various.coders.wppfit.model.database.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import various.coders.wppfit.model.database.entities.Exercise
import java.util.*

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise WHERE user = :userId AND start_time >= :fromDate")
    fun getFromDate(userId: Int, fromDate: Date): LiveData<List<Exercise>>

    @Delete
    fun deleteExercise(exercise: Exercise)

    @Insert
    fun insertExercise(exercise: Exercise)
}