package tech.saltyfish.asfandroid.network

import androidx.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import tech.saltyfish.asfandroid.BuildConfig
import tech.saltyfish.asfandroid.MainActivity


private val sharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(MainActivity.context /* Activity context */)

interface AsfApiService {

    @GET("ASF")
    fun getAsfPropertiesAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String
    ):
            Deferred<AsfProperty>

    @POST("ASF")
    fun updateAsfConfigAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Body() globalConfig: GlobalConfig
    ):
            Deferred<ExeResult>

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

    @POST("Bot/{botNames}/Pause")
    fun pauseBotAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Path("botNames") botNames: String,
        @Body command: CommandInfo
    ):
            Deferred<ExeResult>

    @POST("Bot/{botNames}/Resume")
    fun resumeBotAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Path("botNames") botNames: String
    ):
            Deferred<ExeResult>

    @POST("Bot/{botNames}/Start")
    fun stopBotAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Path("botNames") botNames: String
    ):
            Deferred<ExeResult>


    @POST("Bot/{botNames}/Start")
    fun startBotAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Path("botNames") botNames: String
    ):
            Deferred<ExeResult>


    @DELETE("Bot/{botNames}")
    fun deleteBotAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Path("botNames") botNames: String
    ):
            Deferred<ExeResult>


    @POST("Bot/{botNames}")
    @Headers("Content-Type: application/json")
    fun changeBotConfigAsync(
        @Header("Authentication") password: String,
        @Header("Authorization") basicAuthorization: String,
        @Path("botNames") botNames: String,
        @Body configBody: RequestBody
    ):
            Deferred<MultiExeResult>


    // test if service is usable or configuration is right
    @GET("ASF")
    fun testApi(
        @Header("Authentication") password: String?,
        @Header("Authorization") basicAuthorization: String?
    ):
            Call<AsfProperty>
}

object AsfApi {

    fun retrofitService(): AsfApiService {
        var baseUrl = sharedPreferences.getString("asfUrl", "") ?: "https://baidu.com/"

        if (baseUrl == "") {
            baseUrl = "https://baidu.com/"
        }

        val BASE_URL =
            //"https://saltyfish.tech/api/"
            baseUrl + "Api/"

        // use moshi to phrase json
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)
        }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .build()

        return retrofit.create(AsfApiService::class.java)
    }
}
