package various.coders.wppfit.model.database.converters

import android.arch.persistence.room.TypeConverter
import various.coders.wppfit.model.database.types.ExerciseType

class ExerciseTypeConverter {
    @TypeConverter
    fun fromTypeToInt(type: ExerciseType): Int {
        return type.ordinal
    }

    @TypeConverter
    fun fromIntToType(int: Int): ExerciseType {
        return ExerciseType.values().find {
            it.ordinal == int
        } ?: ExerciseType.OTHER
    }
}