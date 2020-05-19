package tech.saltyfish.asfandroid.botStatus

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
import tech.saltyfish.asfandroid.basicAuthorization
import tech.saltyfish.asfandroid.network.AsfApi
import tech.saltyfish.asfandroid.network.Bot
import tech.saltyfish.asfandroid.network.CommandInfo
import tech.saltyfish.asfandroid.network.ExeResult

class BotStatusViewModel(botName: String, application: Application) :
    AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context /* Activity context */)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )


    private val _bot = MutableLiveData<Bot>()
    val bot: LiveData<Bot>
        get() = _bot

    private val _result = MutableLiveData<ExeResult>()
    val result: LiveData<ExeResult>
        get() = _result


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _operator = MutableLiveData<String>()
    val operator: LiveData<String>
        get() = _operator


    // init
    init {
        getBotInfo(botName)
    }

    /**
     * @param botName bot name
     *
     * this is a async function
     * it is a function with side effect
     * so it is not very easy to reuse
     */
    fun deleteBot(botName: String) {
        coroutineScope.launch {
            _loading.value = true
            val url = sharedPreferences.getString("asfUrl", "")
            if (url == null) {
                Log.e("getSystemInfo", "url error")             // if url == null it will crash

            } else {

                val getBotPropertyDeferred =
                    AsfApi.retrofitService().deleteBotAsync(
                        sharedPreferences.getString("asfIpcPass", "") ?: "",
                        basicAuthorization(
                            sharedPreferences.getString("basicAuthUsername", "") ?: "",
                            sharedPreferences.getString("basicAuthPass", "") ?: ""
                        ), botName
                    )
                try {
                    val rs = getBotPropertyDeferred.await()
                    _result.value = rs
                    _loading.value = false
                } catch (e: Exception) {
                    Log.e("PauseBot", e.message.toString())
                }
            }
        }
    }

    /**
     * @param botName bot name
     *
     *
     **/
    fun pauseBot(botName: String) {
        coroutineScope.launch {
            _loading.value = true
            val url = sharedPreferences.getString("asfUrl", "")
            if (url == null) {
                Log.e("getSystemInfo", "url error")             // if url == null it will crash

            } else {

                val getBotPropertyDeferred =
                    AsfApi.retrofitService().pauseBotAsync(
                        sharedPreferences.getString("asfIpcPass", "") ?: "",
                        basicAuthorization(
                            sharedPreferences.getString("basicAuthUsername", "") ?: "",
                            sharedPreferences.getString("basicAuthPass", "") ?: ""
                        ), botName, CommandInfo(true, 0)
                    )
                try {
                    val rs = getBotPropertyDeferred.await()
                    _result.value = rs
                } catch (e: Exception) {
                    Log.e("PauseBot", e.message.toString())
                }
            }
        }
    }


    /**
     * @param botName bot name
     *
     *
     **/
    fun resumeBot(botName: String) {
        coroutineScope.launch {
            _loading.value = true
            val url = sharedPreferences.getString("asfUrl", "")
            if (url == null) {
                Log.e("getSystemInfo", "url error")             // if url == null it will crash

            } else {

                val getBotPropertyDeferred =
                    AsfApi.retrofitService().resumeBotAsync(
                        sharedPreferences.getString("asfIpcPass", "") ?: "",
                        basicAuthorization(
                            sharedPreferences.getString("basicAuthUsername", "") ?: "",
                            sharedPreferences.getString("basicAuthPass", "") ?: ""
                        ), botName
                    )
                try {
                    val rs = getBotPropertyDeferred.await()
                    _result.value = rs
                } catch (e: Exception) {
                    Log.e("PauseBot", e.message.toString())
                }
            }
        }
    }


    /**
     * @param botName bot name
     *
     *
     **/
    fun getBotInfo(botName: String) {
        coroutineScope.launch {
            _loading.value = true
            val url = sharedPreferences.getString("asfUrl", "")
            // if url == null it will crash
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
                    _bot.value?.avatarHash = _bot.value?.avatarHash ?: " "


                } catch (e: Exception) {
                    Log.e("getBotInfo", e.message.toString())
                }
            }
        }
    }


    /**
     * those two functions is designed to used by view
     * it's used to change vm's status
     * since view can't change viewModel's status
     */
    fun changeLoadStatus() {
        if (loading.value != null) {
            if (loading.value != false) {
                _loading.value = false
            }
        }
    }

    fun changeOperator(op: String) {
        _operator.value = op
    }

}