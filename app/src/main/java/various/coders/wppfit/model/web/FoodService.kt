package various.coders.wppfit.model.web

import retrofit2.Call
import retrofit2.http.*
import various.coders.wppfit.model.web.POJO.FoodResult
import various.coders.wppfit.model.web.POJO.NutrientsBody

interface FoodService {
    @GET("/api/food-database/parser")
    fun foodQuery(@Query("ingr") query: String, @Query("app_id") appId: String, @Query("app_key") appKey: String): Call<FoodResult>

    @POST("/api/food-database/nutrients")
    fun nutrientsQuert(@Query("app_id") appId: String, @Query("app_key") appKey: String, @Body nutrientsBody: NutrientsBody)
}