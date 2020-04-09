package tech.saltyfish.asfandroid

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.preference.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.saltyfish.asfandroid.R
import tech.saltyfish.asfandroid.network.AsfApi

class AppSettingFragment : PreferenceFragmentCompat() {

    private val serviceStatus = MutableLiveData<Boolean>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        (requireActivity() as MainActivity).bottom_nav.visibility = View.INVISIBLE
        setPreferencesFromResource(R.xml.fragment_app_setting, rootKey)



        findPreference<EditTextPreference>("asfUrl")?.setOnPreferenceChangeListener { preference: Preference, any: Any ->
            testConn()
            true
        }

        findPreference<EditTextPreference>("asfIpcPass")?.setOnPreferenceChangeListener { preference: Preference, any: Any ->
            testConn()
            true
        }

        findPreference<EditTextPreference>("basicAuthUsername")?.setOnPreferenceChangeListener { preference: Preference, any: Any ->
            testConn()
            true
        }

        findPreference<EditTextPreference>("basicAuthPass")?.setOnPreferenceChangeListener { preference: Preference, any: Any ->
            testConn()
            true
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testConn()
        serviceStatus.observe(viewLifecycleOwner, Observer {
             findPreference<EditTextPreference>("status")?.title = when (it) {
                null -> "Untested"
                false -> "Test failed"
                true -> "Test successful"
            }

            Toast.makeText(activity?.applicationContext, "Connection Check Complete!", Toast.LENGTH_SHORT).show()

        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun testConn() {
        Toast.makeText(activity?.applicationContext, "Checking Connection ...", Toast.LENGTH_SHORT).show()

        coroutineScope.launch {
            var getAsfPropertyDeferred =
                AsfApi.retrofitService().getAsfPropertiesAsync(
                    findPreference<EditTextPreference>("asfIpcPass")?.text ?: "",
                    basicAuthorization(
                        findPreference<EditTextPreference>("basicAuthUsername")?.text ?: "",
                        findPreference<EditTextPreference>("basicAuthPass")?.text ?: ""
                    )
                )
            try {
                val rs = getAsfPropertyDeferred.await()

                serviceStatus.value = (rs.result.version != "")
            } catch (e: Exception) {
                serviceStatus.value = false
                Log.e("getSystemInfo", e.message.toString())
            }
        }
    }
}
