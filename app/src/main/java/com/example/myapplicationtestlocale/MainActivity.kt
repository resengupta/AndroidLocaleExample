package com.example.myapplicationtestlocale

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {

    private val MY_APP_SHARED_PREF_KEY = "myapp"
    private val MY_APP_SHARED_PREF_LANG_KEY = "Language"
    private val MY_APP_SHARED_PREF_COUNTRY_KEY = "Country"

    override fun attachBaseContext(newBase: Context?) {
        val sharedPref = newBase?.getSharedPreferences(MY_APP_SHARED_PREF_KEY, Context.MODE_PRIVATE)
        val langPref = sharedPref?.getString(MY_APP_SHARED_PREF_LANG_KEY, Locale.US.language)
            ?: Locale.US.language
        val countryPref = sharedPref?.getString(MY_APP_SHARED_PREF_COUNTRY_KEY, Locale.US.country)
            ?: Locale.US.country
        val locale = Locale(langPref, countryPref)
        Locale.setDefault(locale)
        val config = newBase?.resources?.configuration
        config?.setLocales(LocaleList(locale))
        super.attachBaseContext(newBase?.createConfigurationContext(config!!)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener {
            changeLocale(Locale.US)
            recreate()

        }
        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener {
            changeLocale(Locale.CANADA_FRENCH)
            recreate()
        }
        button1.text = resources.getString(R.string.title_Button1)
        button2.text = resources.getString(R.string.title_Button2)

        val primaryLocale: Locale = resources.configuration.locales[0]
        val locale: String = primaryLocale.displayName
        Toast.makeText(this, "Locale: $locale", Toast.LENGTH_LONG).show()

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun changeLocale(locale: Locale) {
        val sharedPref = getSharedPreferences(MY_APP_SHARED_PREF_KEY, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(MY_APP_SHARED_PREF_COUNTRY_KEY, locale.country)
            putString(MY_APP_SHARED_PREF_LANG_KEY, locale.language)
            commit()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Toast.makeText(this, "Configuration changed", Toast.LENGTH_SHORT).show()
    }
}
