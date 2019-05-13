package various.coders.wppfit.model.database.types

enum class ExerciseType(val calRatio:Double) {
    RUNNING(0.02725),
    WALKING(0.01125),
    SWIMMING(0.0238),
    CYCLING(0.02041),
    GYM(0.02041),
    OTHER(0.0)
}