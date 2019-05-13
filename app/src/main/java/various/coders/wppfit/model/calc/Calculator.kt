package various.coders.wppfit.model.calc

import various.coders.wppfit.model.database.entities.Exercise
import various.coders.wppfit.model.database.entities.User
import java.time.LocalDate
import android.R.attr.duration
import various.coders.wppfit.model.database.types.ExerciseType
import java.util.concurrent.TimeUnit


class CaloriesCalc {
companion object{
    private const val KG_CALORIES:Double = 7800.0
    private const val CALORIES_WEIGHT_RATIO = 8.58
    fun getCaloricIntake(user: User):Double{
        val now = LocalDate.now().year
        return (user.activity.ratio
                *when(user.gender){
                    true -> 13.397*user.weight + 4.799*user.height - 5.677*(now - user.age.year) + 88.362
                    false -> 9.247*user.weight + 3.098*user.height - 4.330*(now - user.age.year) + 447.593
                }
        )
    }
    fun getCaloricOffset(user:User,daysToTarget:Int,targetWeight:Double):Double{
        val weightDiff = targetWeight - user.weight
        val caloriesDiff = KG_CALORIES * weightDiff
        return caloriesDiff/daysToTarget
    }
    fun getCalLossFromActivity(exercise:Exercise,user:User):Double{
        val duration = exercise.endTime.time - exercise.startTime.time
        val durationMinutes = TimeUnit.MILLISECONDS.toMinutes(duration)
        return(
                durationMinutes
                *user.weight
                *exercise.type.calRatio
        )
    }
}




}