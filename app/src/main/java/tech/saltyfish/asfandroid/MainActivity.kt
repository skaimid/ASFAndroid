package tech.saltyfish.asfandroid

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    companion object {
        var context: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        setContentView(R.layout.activity_main)

        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context /* Activity context */)

        val testConfigFragment = TestConfigFragment()
        testConfigFragment.show(supportFragmentManager, "waiting")


        // Bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)


        if (connTest(
                sharedPreferences.getString("asfUrl", ""),
                sharedPreferences.getString("asfIpcPass", ""),
                sharedPreferences.getString("basicAuthUsername", ""),
                sharedPreferences.getString("basicAuthPass", "")
            )
        ) {
            testConfigFragment.dismiss()
        } else {
            navController.navigate(R.id.appSettingFragment)
            findViewById<BottomNavigationView>(R.id.bottom_nav)
                .visibility = View.INVISIBLE
            testConfigFragment.dismiss()
        }

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .visibility = View.VISIBLE
    }
}
