package various.coders.wppfit.model.entities

import androidx.room.*
import various.coders.wppfit.model.converters.TimeConverter
import java.util.*

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "gender") val gender: Boolean, //true for M, false for F

    @ColumnInfo(name = "age")
    @TypeConverters(TimeConverter::class)
    val age: Date,

    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "height") val height: Int
)