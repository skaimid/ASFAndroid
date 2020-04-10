package tech.saltyfish.asfandroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.saltyfish.asfandroid.network.AsfApi
import tech.saltyfish.asfandroid.network.GlobalConfig

class ASFSettingFragment : PreferenceFragmentCompat() {
    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MainActivity.context /* Activity context */)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )
    val globalConfig = MutableLiveData<GlobalConfig>()


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_asf_setting, rootKey)
        getSystemInfo()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        globalConfig.observe(viewLifecycleOwner, Observer {
            if (globalConfig.value != null) {
                sharedPreferences.edit()
                    .putString("temp_key", (globalConfig.value ?: GlobalConfig()).sSteamOwnerID)
                    .apply()
            }
        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }




    private fun getSystemInfo() {
        coroutineScope.launch {

            val url = sharedPreferences.getString("asfUrl", "");
            if (url == null) {
                Log.e("getSystemInfo", "url error")
            } else {
                val getAsfPropertyDeferred =
                    AsfApi.retrofitService().getAsfPropertiesAsync(
                        "zzx20001223",
                        basicAuthorization("skaimid", "vEY8xU9H9fjmXP2")
                    )
                try {
                    val rs = getAsfPropertyDeferred.await()
                    globalConfig.value = rs.result.globalConfig

                } catch (e: Exception) {
                    Log.e("getSystemInfo", e.message.toString())
                }
            }
        }
    }

    private fun updateConfig() {
        coroutineScope.launch {

            val url = sharedPreferences.getString("asfUrl", "");
            if (url == null) {
                Log.e("getSystemInfo", "url error")
            } else {
                val getAsfPropertyDeferred =
                    AsfApi.retrofitService().updateAsfConfigAsync(
                        "zzx20001223",
                        basicAuthorization("skaimid", "vEY8xU9H9fjmXP2"),
                        GlobalConfig(

                        )
                    )
                try {
                    val rs = getAsfPropertyDeferred.await()


                } catch (e: Exception) {
                    Log.e("getSystemInfo", e.message.toString())
                }
            }
        }
    }

    fun submitButtonClicked() {
        Toast.makeText(MainActivity.context, "clicked", Toast.LENGTH_SHORT).show()
    }
}