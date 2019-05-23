package various.coders.wppfit.model.calc

import various.coders.wppfit.model.NutritionInfo
import various.coders.wppfit.model.database.entities.User
import java.time.LocalDate
import various.coders.wppfit.model.database.types.ExerciseType
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.min


class CaloriesCalc {
    companion object {
        private const val KG_CALORIES: Double = 7800.0
        private const val PROTEIN_CALORIES_PER_GRAM = 4
        private const val FAT_CALORIES_PER_GRAM = 9
        private const val CARBS_CALORIES_PER_GRAM = 4
        fun getCaloricIntake(user: User): Double {
            val now = LocalDate.now().year
            return (user.activity.ratio * when (user.gender) {
                true -> 13.397 * user.weight + 4.799 * user.height - 5.677 * (now - user.age.year) + 88.362
                false -> 9.247 * user.weight + 3.098 * user.height - 4.330 * (now - user.age.year) + 447.593
            })
        }

        fun getCaloricOffset(user: User, daysToTarget: Int, targetWeight: Double): Double {
            if (daysToTarget < 0 || targetWeight < 0) return 0.0
            val weightDiff = targetWeight - user.weight
            val caloriesDiff = KG_CALORIES * weightDiff
            return caloriesDiff / daysToTarget
        }

        fun getCalLossFromActivity(start: Date, end: Date, type: ExerciseType, user: User): Float {
            val duration = end.time - start.time
            val durationMinutes = TimeUnit.MILLISECONDS.toMinutes(duration)
            return (durationMinutes * user.weight * type.calRatio).toFloat()
        }

        fun getMacroNutrientsRatio(user: User, daysToTarget: Int, targetWeight: Double): NutritionInfo {
            val offset = getCaloricOffset(user, daysToTarget, targetWeight)
            val intake = getCaloricIntake(user)
            val calRatio = intake / offset
            var proteinCal = 0.8 * user.weight

            if (calRatio < 0) proteinCal *= min(
                10 * abs(calRatio),
                2.0
            ) //increasing protein intake depending on calories deficit
            val fatCal = 0.3 * intake
            val carbCal = intake - proteinCal - fatCal

            val fatGram = fatCal / FAT_CALORIES_PER_GRAM
            val proteinGram = proteinCal / PROTEIN_CALORIES_PER_GRAM
            val carbGram = carbCal / CARBS_CALORIES_PER_GRAM
            return NutritionInfo(fat = fatGram, protein = proteinGram, carbs = carbGram)
        }
    }


}