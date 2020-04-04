package tech.saltyfish.asfandroid.appSetting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat
import tech.saltyfish.asfandroid.R
import tech.saltyfish.asfandroid.network.newUrl

class AppSettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_app_setting, rootKey)

        val url = findPreference<EditTextPreference>("asfUrl")

        findPreference<EditTextPreference>("asfUrl")?.setOnPreferenceChangeListener { preference, newValue ->
            newUrl()
            true
        }
    }
}
