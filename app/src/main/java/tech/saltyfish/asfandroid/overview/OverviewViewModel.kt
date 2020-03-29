package tech.saltyfish.asfandroid.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.saltyfish.asfandroid.basicAuthorization
import tech.saltyfish.asfandroid.network.AsfApi
import tech.saltyfish.asfandroid.network.Bot

class OverviewViewModel : ViewModel() {

    private val _system = MutableLiveData<String>()
    val system: LiveData<String>
        get() = _system

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
        _system.value = "-"

        getSystemInfo()
        getBotInfo()

    }


    private fun getSystemInfo() {
        coroutineScope.launch {

            var getAsfPropertyDeferred =
                AsfApi.retrofitService.getAsfPropertiesAsync(
                    "zzx20001223",
                    basicAuthorization("skaimid", "vEY8xU9H9fjmXP2")
                )
            try {
                var rs = getAsfPropertyDeferred.await()

                _system.value = rs.result.globalConfig.iPC.toString()
            } catch (e: Exception) {
                Log.e("getSystemInfo", e.message.toString())
            }
        }
    }


    private fun getBotInfo() {
        coroutineScope.launch {

            var getBotPropertyDeferred =
                AsfApi.retrofitService.getBotPropertiesAsync(
                    "zzx20001223",
                    basicAuthorization("skaimid", "vEY8xU9H9fjmXP2")
                )
            try {
                var rs = getBotPropertyDeferred.await()
                _bots.value = rs.result.values.toList()
            } catch (e: Exception) {
                Log.e("getBotInfo", e.message.toString())
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

}