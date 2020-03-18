package tech.saltyfish.asfandroid.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL =
    "https://steam.saltyfish.art/Api/"

// use moshi to phrase json
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface AsfApiService {

    @GET("ASF")

    fun getProperties(@Header("Authentication") password:String):
            Deferred<AsfProperty>
}

object AsfApi {
    val retrofitService : AsfApiService by lazy {
        retrofit.create(AsfApiService::class.java) }
}