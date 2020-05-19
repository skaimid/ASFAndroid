package tech.saltyfish.asfandroid

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    /* to get activity context in the viewModel and List Adapter
      I use a companion object in MainActivity.
     */
    companion object {
        var context: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        setContentView(R.layout.activity_main)


        // Bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        /*
         data binding and navigation have some kind of conflict so I use findViewById
         and it is a known bug
         for more detail see https://issuetracker.google.com/issues/142847973
         and https://stackoverflow.com/questions/58703451/fragmentcontainerview-as-navhostfragment
         */
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)


        /* test if config is correct
        if config is incorrect
        then direct to appSettingFragment
         */

        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context /* Activity context */)
        if (!urlTest(sharedPreferences.getString("asfUrl", ""))) {
            navController.navigate(R.id.appSettingFragment)
            Toast.makeText(this, "Check you url", Toast.LENGTH_LONG).show()
        }
    }
}
