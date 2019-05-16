package various.coders.wppfit.model.web.schema

import com.google.gson.annotations.SerializedName

data class NutrientsResult (
    @SerializedName("totalNutrients")
    val nutrient: NutrientResult
)

data class NutrientResult(
    @SerializedName("ENERC_KCAL")
    val calories: Nutrient?,
    @SerializedName("FAT")
    val fat: Nutrient?,
    @SerializedName("CHOCDF")
    val carbs: Nutrient?,
    @SerializedName("PROCNT")
    val protein: Nutrient?
)

data class Nutrient(
    @SerializedName("label")
    val label: String,
    @SerializedName("quantity")
    val quantity: Float,
    @SerializedName("unit")
    val unit: String
)