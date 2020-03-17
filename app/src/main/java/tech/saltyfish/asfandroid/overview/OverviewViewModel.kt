package tech.saltyfish.asfandroid.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header
import tech.saltyfish.asfandroid.network.AsfApi

class OverviewViewModel : ViewModel() {

    private val _system = MutableLiveData<String>()
    val system:LiveData<String>
        get()  = _system

    init {
        _system.value = ""

        getSystemInfo()
    }

    private fun getSystemInfo(){
        AsfApi.retrofitService.getProperties("zzx20001223").enqueue(
            object: Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    _system.value = "Failure: " + t.message
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _system.value = response.body()
                    Log.d("rs",system.value)
                }
            }

        )
    }

}