package various.coders.wppfit.model.database.entities

import android.arch.persistence.room.*
import various.coders.wppfit.model.database.converters.TimeConverter
import java.io.Serializable
import java.util.*

@Entity(
    indices = [Index(value = ["uid"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "gender") val gender: Boolean, //true for M, false for F

    @ColumnInfo(name = "age")
    @TypeConverters(TimeConverter::class)
    val age: Date,

    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "height") val height: Int
): Serializable