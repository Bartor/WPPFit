package various.coders.wppfit.model.entities

import androidx.room.*
import various.coders.wppfit.model.converters.TimeConverter
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
data class Meal(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "user") val user: Int,

    @ColumnInfo(name = "time")
    @TypeConverters(TimeConverter::class)
    val time: Date,

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "carbs") val carbs: Float,
    @ColumnInfo(name = "calories") val calories: Float,
    @ColumnInfo(name = "protein") val protein: Float,
    @ColumnInfo(name = "fat") val fat: Float,
    @ColumnInfo(name = "weight") val weight: Float
)