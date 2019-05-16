package various.coders.wppfit.model.web

import retrofit2.Call
import retrofit2.http.*
import various.coders.wppfit.model.web.schema.FoodResult
import various.coders.wppfit.model.web.schema.NutrientsBody
import various.coders.wppfit.model.web.schema.NutrientsResult

interface FoodService {
    @GET("/api/food-database/parser")
    fun foodQuery(@Query("ingr") query: String, @Query("app_id") appId: String, @Query("app_key") appKey: String): Call<FoodResult>

    @Headers("Content-Type: application/json")
    @POST("/api/food-database/nutrients")
    fun nutrientsQuery(@Query("app_id") appId: String, @Query("app_key") appKey: String, @Body nutrientsBody: NutrientsBody): Call<NutrientsResult>
}