package various.coders.wppfit.model.web.POJO

import com.google.gson.annotations.SerializedName

data class NutrientsBody(
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>
)

data class Ingredient(
    @SerializedName("quantity")
    val quantity: Float,
    @SerializedName("measureURI")
    val measureUri: String,
    @SerializedName("foodId")
    val foodId: String
)