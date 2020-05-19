package tech.saltyfish.asfandroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.saltyfish.asfandroid.network.AsfApi


/**
 * @see PreferenceFragmentCompat
 *
 *  for more detail
 *  https://developer.android.com/guide/topics/ui/settings
 */
class AppSettingFragment : PreferenceFragmentCompat() {

    private val serviceStatus = MutableLiveData<Boolean>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        (requireActivity() as MainActivity).bottom_nav.visibility = View.INVISIBLE
        setPreferencesFromResource(R.xml.fragment_app_setting, rootKey)


        // on every chanage of Preference we test connect

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

        // observe need to be placed in onCreateView rather than onCreatePreferences !!!
        serviceStatus.observe(viewLifecycleOwner, Observer {
            findPreference<EditTextPreference>("status")?.title = when (it) {
                null -> "Untested"
                false -> "Test failed"
                true -> "Test successful"
            }

            Toast.makeText(
                activity?.applicationContext,
                "Connection Check Complete!",
                Toast.LENGTH_SHORT
            ).show()

        })


        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * test connect is correct
     *
     */
    private fun testConn() {
        Toast.makeText(activity?.applicationContext, "Checking Connection ...", Toast.LENGTH_SHORT)
            .show()

        coroutineScope.launch {

            // getAsfPropertyDeferred
            val getAsfPropertyDeferred =
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
