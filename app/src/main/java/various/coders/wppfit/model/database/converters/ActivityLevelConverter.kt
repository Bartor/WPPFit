package various.coders.wppfit.model.database.converters

import android.arch.persistence.room.TypeConverter
import various.coders.wppfit.model.database.types.ActivityLevel
import various.coders.wppfit.model.database.types.ExerciseType

class ActivityLevelConverter {
    @TypeConverter
    fun fromLevelToDouble(level: ActivityLevel): Int {
        return level.ordinal
    }

    @TypeConverter
    fun fromDoubleToLevel(int: Int): ActivityLevel {
        return ActivityLevel.values().find {
            it.ordinal == int
        } ?: ActivityLevel.NONE
    }

}