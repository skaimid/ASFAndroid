package tech.saltyfish.asfandroid.network

import android.util.Log
import androidx.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import tech.saltyfish.asfandroid.MainActivity
import tech.saltyfish.asfandroid.basicAuthorization

private val sharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(MainActivity.context /* Activity context */)
val baseUrl = sharedPreferences.getString("asfUrl", "")

private val BASE_URL =
    baseUrl + "Api/"

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
    fun getAsfPropertiesAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String
    ):
            Deferred<AsfProperty>

    @POST("Command")
    fun getCommandResultAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Body command: CommandPostProperty
    ):
            Deferred<CommandProperty>

    @GET("Bot/{botNames}")
    fun getBotPropertiesAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Path("botNames") botNames: String = "asf"
    ):
            Deferred<BotProperty>

    @GET("ASF")
    fun testApi(
        @Header("Authentication") password: String?,
        @Header("Authorization") basicAuthorization: String?
    ):
            Call<AsfProperty>
}

object AsfApi {
    val retrofitService: AsfApiService by lazy {
        retrofit.create(AsfApiService::class.java)
    }
}

fun newUrl() {
    retrofit.newBuilder()
        .baseUrl(sharedPreferences.getString("asfUrl", "") + "Api/")
    Log.d("newUrl","urlChanged: {${retrofit.baseUrl()}}")
}