package tech.saltyfish.asfandroid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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


        // Bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        if(!urlTest(sharedPreferences.getString("asfUrl",""))){
            navController.navigate(R.id.appSettingFragment)
            Toast.makeText(this,"Check you url",Toast.LENGTH_LONG).show()
        }
    }
}
