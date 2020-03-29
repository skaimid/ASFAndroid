package tech.saltyfish.asfandroid.botStatus

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BotStatusViewModelFactory(
    private val botName:String,
    private val application: Application
):ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BotStatusViewModel::class.java)) {
            return BotStatusViewModel(botName,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}