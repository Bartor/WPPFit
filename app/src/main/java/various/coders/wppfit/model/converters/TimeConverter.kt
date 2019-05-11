package various.coders.wppfit.model.converters

import androidx.room.TypeConverter
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
}