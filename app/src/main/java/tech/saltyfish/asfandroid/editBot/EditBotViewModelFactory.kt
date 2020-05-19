package tech.saltyfish.asfandroid.editBot

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditBotViewModelFactory(
    private val botName: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditBotViewModel::class.java)) {
            return EditBotViewModel(botName, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}