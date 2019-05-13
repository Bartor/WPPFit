package various.coders.wppfit.model.database.types

enum class ActivityLevel(val ratio:Double,val description:String) {
    NONE(1.0,"No activity"),
    SEDENTARY(1.2,"Sedentary activity"),
    LIGHT(1.375,"One exercise a week"),
    MODERATE(1.465,"Several trainigs per week"),
    ACTIVE(1.550,"Training every day"),
    VERY_ACTIVE(1.650,"Michael Phelps mode");

    override fun toString(): String {
        return description
    }
}