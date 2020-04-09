package tech.saltyfish.asfandroid.command

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.saltyfish.asfandroid.MainActivity
import tech.saltyfish.asfandroid.basicAuthorization
import tech.saltyfish.asfandroid.network.AsfApi
import tech.saltyfish.asfandroid.network.CommandPostProperty
import java.lang.Exception

class CommandViewModel : ViewModel() {
    private val _commandLine = MutableLiveData<String>()
    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MainActivity.context /* Activity context */)
    val baseUrl = sharedPreferences.getString("asfUrl", "")
    val commandLine: LiveData<String>
        get() = _commandLine

    // coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    init {
        _commandLine.value = ""
    }

    fun updateCommandLine(command: String) {
        _commandLine.value = _commandLine.value + ">>> " + command + '\n'
        coroutineScope.launch {
            if (baseUrl == null) {
                Log.e("getSystemInfo", "url error")
            } else {
                var getCommandResultDeferred = AsfApi.retrofitService().getCommandResultAsync(
                    "zzx20001223",
                    basicAuthorization("skaimid", "vEY8xU9H9fjmXP2"),
                    CommandPostProperty(command)
                )

                try {
                    var rs = getCommandResultDeferred.await()
                    _commandLine.value = _commandLine.value + "<<< " + rs.result + '\n'
                } catch (e: Exception) {
                    _commandLine.value = _commandLine.value + "Error: " + e.message + '\n'
                }
            }

        }
    }
}