package various.coders.wppfit.model.database.types

enum class ExerciseType(val calRatio:Double,val description:String) {
    RUNNING(0.02725,"Running"),
    WALKING(0.01125,"Walking"),
    SWIMMING(0.0238,"Swimming"),
    CYCLING(0.02041,"Cycling"),
    GYM(0.02041,"Gym"),
    OTHER(0.0,"Other");

    override fun toString(): String {
        return description
    }
}