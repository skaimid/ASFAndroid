package tech.saltyfish.asfandroid.command

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.saltyfish.asfandroid.network.AsfApi
import tech.saltyfish.asfandroid.network.CommandPostProperty
import java.lang.Exception

class CommandViewModel : ViewModel() {
    private val _commandLine = MutableLiveData<String>()
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
            var getCommandResultDeferred = AsfApi.retrofitService.getCommandResultAsync(
                "zzx20001223",
                CommandPostProperty(command)
            )

            try {
                var rs = getCommandResultDeferred.await()
                _commandLine.value = _commandLine.value + "<<< " + rs.result + '\n'
            }catch (e:Exception){
                _commandLine.value = _commandLine.value + "Error: " + e.message + '\n'
            }

        }
    }
}