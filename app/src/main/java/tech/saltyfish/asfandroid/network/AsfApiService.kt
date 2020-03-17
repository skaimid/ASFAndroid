package tech.saltyfish.asfandroid.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

private const val BASE_URL =
    "https://steam.saltyfish.art/Api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsfApiService {

    @GET("ASF")

    fun getProperties(@Header("Authentication") password:String):
            Call<String>
}

object AsfApi {
    val retrofitService : AsfApiService by lazy {
        retrofit.create(AsfApiService::class.java) }
}