package various.coders.wppfit.model.database.converters

import android.arch.persistence.room.TypeConverter
import java.lang.Exception
import java.util.*

class TimeConverter {
    @TypeConverter
    public fun fromTimestamp(timestamp: Long): Date {
        return try {
            Date(timestamp)
        } catch (e: Exception) {
            Date()
        }
    }

    @TypeConverter
    public fun toTimestamp(date: Date): Long {
        return date.time
    }
}