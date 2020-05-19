package tech.saltyfish.asfandroid.editBot

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tech.saltyfish.asfandroid.basicAuthorization
import tech.saltyfish.asfandroid.network.AsfApi
import tech.saltyfish.asfandroid.network.Bot
import tech.saltyfish.asfandroid.network.MultiExeResult


class EditBotViewModel(botName: String, application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context /* Activity context */)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    // status
    private val _bot = MutableLiveData<Bot>()
    val bot: LiveData<Bot>
        get() = _bot

    private val _result = MutableLiveData<MultiExeResult>()
    val result: LiveData<MultiExeResult>
        get() = _result

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    init {
        getBotInfo(botName)

    }

    private fun getBotInfo(botName: String) {

        _loading.value = true

        coroutineScope.launch {
            _loading.value = true
            val url = sharedPreferences.getString("asfUrl", "")
            if (url == null) {
                Log.e("getSystemInfo", "url error")
            } else {

                val getBotPropertyDeferred =
                    AsfApi.retrofitService().getBotPropertiesAsync(
                        sharedPreferences.getString("asfIpcPass", "") ?: "",
                        basicAuthorization(
                            sharedPreferences.getString("basicAuthUsername", "") ?: "",
                            sharedPreferences.getString("basicAuthPass", "") ?: ""
                        ), botName
                    )


                try {

                    val rs = getBotPropertyDeferred.await()
                    _bot.value = rs.result.values.toList()[0]
                } catch (e: Exception) {
                    Log.e("getBotInfo", e.message.toString())
                    _loading.value = false
                }
            }
        }
    }

    fun changeBorConfig(botName: String, botConfig: String) {
        val CONTENT_TYPE: MediaType? = "application/json".toMediaTypeOrNull()
        val requestBody: RequestBody = botConfig.toRequestBody(CONTENT_TYPE)

        _loading.value = true

        coroutineScope.launch {
            _loading.value = true
            val url = sharedPreferences.getString("asfUrl", "")
            if (url == null) {
                Log.e("getSystemInfo", "url error")
            } else {

                val changeBotConfigDeferred =
                    AsfApi.retrofitService().changeBotConfigAsync(
                        sharedPreferences.getString("asfIpcPass", "") ?: "",
                        basicAuthorization(
                            sharedPreferences.getString("basicAuthUsername", "") ?: "",
                            sharedPreferences.getString("basicAuthPass", "") ?: ""
                        ),
                        botName,
                        requestBody
                    )
                try {
                    val rs = changeBotConfigDeferred.await()
                    if (rs.success) {
                        Log.d("changeBotConfigDeferred", "success")
                        _result.value = rs
                    }
                    _loading.value = false
                } catch (e: Exception) {
                    _loading.value = false
                    Log.e("changeBotInfo", e.message.toString())
                }
            }
        }
    }

    fun changeLoadStatus() {
        if (loading.value != null) {
            if (loading.value != false) {
                _loading.value = false
            }
        }
    }

    fun setRSNull() {
        if (result.value != null) {
            _result.value = null
        }
    }
}