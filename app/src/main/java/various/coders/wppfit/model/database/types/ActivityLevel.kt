package various.coders.wppfit.model.database.types

enum class ActivityLevel(val ratio:Double) {
    NONE(1.0),
    SEDENTARY(1.2),
    LIGHT(1.375),
    MODERATE(1.465),
    ACTIVE(1.550),
    VERY_ACTIVE(1.650)
}