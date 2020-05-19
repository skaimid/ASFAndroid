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

class CommandViewModel : ViewModel() {
    private val _commandLine = MutableLiveData<String>()
    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MainActivity.context /* Activity context */)
    val baseUrl = sharedPreferences.getString("asfUrl", "")
    val commandLine: LiveData<String>
        get() = _commandLine

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    // coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    init {
        _loading.value = false
        _commandLine.value = ""
    }

    /**
     * @param command
     *
     * excuse command in server
     *
     */
    fun updateCommandLine(command: String) {
        _commandLine.value = _commandLine.value + ">>>  " + command + '\n'
        coroutineScope.launch {
            _loading.value = true
            if (baseUrl == null) {
                Log.e("getSystemInfo", "url error") // may crash without it
            } else {
                val getCommandResultDeferred = AsfApi.retrofitService().getCommandResultAsync(
                    sharedPreferences.getString("asfIpcPass", "") ?: "",
                    basicAuthorization(
                        sharedPreferences.getString("basicAuthUsername", "") ?: "",
                        sharedPreferences.getString("basicAuthPass", "") ?: ""
                    ),
                    CommandPostProperty(command)
                )

                try {
                    val rs = getCommandResultDeferred.await()
                    _commandLine.value = _commandLine.value + "<<<  " + rs.result + '\n' + '\n'
                } catch (e: Exception) {
                    _commandLine.value = _commandLine.value + "Error: " + e.message + '\n' + '\n'
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
}