package various.coders.wppfit.model.web.pojo

import com.google.gson.annotations.SerializedName

data class FoodResult(
    @SerializedName("hints")
    val foods: List<FoodMeasure>
)

data class FoodMeasure(
    val food: Food,
    val measure: Measure
)

data class Food(
    @SerializedName("foodId")
    val foodId: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("nutrients")
    val nutrients: Nutrients
)

data class Nutrients(
    @SerializedName("ENERC_KCAL")
    val calories: Float,
    @SerializedName("PROCNT")
    val proteins: Float,
    @SerializedName("FAT")
    val fat: Float,
    @SerializedName("CHOCDF")
    val carbs: Float
)

data class Measure(
    @SerializedName("uri")
    val uri: String,
    @SerializedName("label")
    val label: String
)