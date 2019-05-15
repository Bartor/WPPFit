package various.coders.wppfit.model.web

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIClient {
    companion object {
        fun getService(): FoodService {
            val retrofit = Retrofit.Builder().apply {
                baseUrl("https://api.edamam.com")
                addConverterFactory(GsonConverterFactory.create())
                client(OkHttpClient())
            }.build()
            return retrofit.create(FoodService::class.java)
        }
    }
}