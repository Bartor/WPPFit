package various.coders.wppfit.model.database.entities

import android.arch.persistence.room.*
import various.coders.wppfit.model.database.converters.ExerciseTypeConverter
import various.coders.wppfit.model.database.converters.TimeConverter
import various.coders.wppfit.model.database.types.ExerciseType
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["user"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "user") val user: Int,

    @ColumnInfo(name = "start_time")
    @TypeConverters(TimeConverter::class)
    val startTime: Date,

    @ColumnInfo(name = "end_time")
    @TypeConverters(TimeConverter::class)
    val endTime: Date,

    @ColumnInfo(name = "type")
    @TypeConverters(ExerciseTypeConverter::class)
    val type: ExerciseType,

    @ColumnInfo(name = "calories") val calories: Int
)