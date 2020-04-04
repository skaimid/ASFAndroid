package tech.saltyfish.asfandroid.botStatus

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.saltyfish.asfandroid.basicAuthorization
import tech.saltyfish.asfandroid.network.AsfApi
import tech.saltyfish.asfandroid.network.Bot

class BotStatusViewModel(botName: String, application: Application) :
    AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )
    private val _bot = MutableLiveData<Bot>();
    val bot: LiveData<Bot>
        get() = _bot


    init {
        //getBotInfo(botName)

    }

//    private fun getBotInfo(botName: String) {
//        coroutineScope.launch {
//
//            var getBotPropertyDeferred =
//                AsfApi.retrofitService.getBotPropertiesAsync(
//                    "zzx20001223",
//                    basicAuthorization("skaimid", "vEY8xU9H9fjmXP2"), botName
//                )
//            try {
//                var rs = getBotPropertyDeferred.await()
//                _bot.value = rs.result.values.toList()[0]
//            } catch (e: Exception) {
//                Log.e("getBotInfo", e.message.toString())
//            }
//        }
//    }

}