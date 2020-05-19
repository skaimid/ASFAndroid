package tech.saltyfish.asfandroid.overview

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

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context /* Activity context */)


    private val _addBot = MutableLiveData<Boolean>()
    val addBot: LiveData<Boolean>
        get() = _addBot

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    private val _bots = MutableLiveData<List<Bot>>()
    val bots: LiveData<List<Bot>>
        get() = _bots

    private val _navigateToSelectedProperty = MutableLiveData<Bot>()
    val navigateToSelectedProperty: LiveData<Bot>
        get() = _navigateToSelectedProperty

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    init {
        _addBot.value = false

        //getSystemInfo()
        getBotInfo()

    }


//    private fun getSystemInfo() {
//        coroutineScope.launch {
//
//            val url = sharedPreferences.getString("asfUrl", "");
//            if (url == null) {
//                Log.e("getSystemInfo", "url error")
//            } else {
//                val getAsfPropertyDeferred =
//                    AsfApi.retrofitService().getAsfPropertiesAsync(
//                        "zzx20001223",
//                        basicAuthorization("skaimid", "vEY8xU9H9fjmXP2")
//                    )
//                try {
//                    val rs = getAsfPropertyDeferred.await()
//
//                    _system.value = rs.result.globalConfig.iPC.toString()
//                } catch (e: Exception) {
//                    Log.e("getSystemInfo", e.message.toString())
//                }
//            }
//        }
//    }


    private fun getBotInfo() {
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
                        )
                    )
                try {
                    val rs = getBotPropertyDeferred.await()
                    _bots.value = rs.result.values.toList()
                    _bots.value?.forEach {
                        if (it.avatarHash == null)
                            it.avatarHash = " "
                    }


                } catch (e: Exception) {
                    Log.e("getBotInfo 1", e.message.toString())
                }
            }
        }
    }

    fun displayPropertyDetails(bot: Bot) {
        _navigateToSelectedProperty.value = bot
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun getGameLeft(): Int {
        var sum = 0
        bots.value?.forEach {
            sum += it.cardsFarmer.gamesToFarm.size
        }
        return sum
    }

    fun getTimeLeft(): String {
        return bots.value?.maxBy {
            it.cardsFarmer.timeRemaining
        }?.cardsFarmer?.timeRemaining ?: "0"
    }

    fun getCardLeft(): Int {
        var sum = 0
        bots.value?.forEach { bot ->
            bot.cardsFarmer.gamesToFarm.forEach {
                sum += it.cardsRemaining
            }
            bot.cardsFarmer.gamesToFarm.forEach {
                sum += it.cardsRemaining
            }
        }
        return sum
    }

    fun changeLoadStatus() {
        if (loading.value != null) {
            if (loading.value != false) {
                _loading.value = false
            }
        }
    }

    fun changeAddBotStatus() {
        if (addBot.value != null) {
            _addBot.value = !addBot.value!!
        }
    }
}