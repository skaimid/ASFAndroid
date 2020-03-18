package tech.saltyfish.asfandroid.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.saltyfish.asfandroid.network.AsfApi
import java.lang.Exception

class OverviewViewModel : ViewModel() {

    private val _system = MutableLiveData<String>()
    val system: LiveData<String>
        get() = _system


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    init {
        _system.value = "-"

        getSystemInfo()
    }

    private fun getSystemInfo() {
        coroutineScope.launch {


            var getAsfPropertyDeferred = AsfApi.retrofitService.getAsfPropertiesAsync("zzx20001223")
//            try {
//                // Await the completion of our Retrofit request
//                var listResult = getPropertiesDeferred.await()
//                _response.value = "Success: ${listResult.size} Mars properties retrieved"
//            } catch (e: Exception) {
//                _response.value = "Failure: ${e.message}"
//            }
                try {
                    var rs = getAsfPropertyDeferred.await()
                    _system.value = rs.result.globalConfig.iPC.toString()
                }catch (e:Exception){
                    _system.value = e.message
                }

        }

    }

}