package com.yara.kinoapp.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.yara.kinoapp.App
import com.yara.kinoapp.R
import com.yara.kinoapp.databinding.ActivityMainBinding
import com.yara.kinoapp.domain.Film
import com.yara.kinoapp.receivers.ConnectionChecker
import com.yara.kinoapp.view.fragments.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: BroadcastReceiver
    private var backPressed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        // run fragment on start
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()

        receiver = ConnectionChecker()
        val filters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(receiver, filters)

        if (!App.instance.isPromoShown) {
            // get Remote Config
            val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            // settings
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
            // fetch data
            firebaseRemoteConfig.fetch()
                .addOnCompleteListener {
                    // success
                    if (it.isSuccessful) {
                        // activate last config
                        firebaseRemoteConfig.activate()
                        // get link
                        val filmLink = firebaseRemoteConfig.getString("film_link")
                        // if not blank
                        if (filmLink.isNotBlank()) {
                            // set isPromoShown to true
                            App.instance.isPromoShown = true
                            // include promo view
                            binding.promoViewGroup.apply {
                                // set visible
                                visibility = View.VISIBLE
                                // animate
                                animate()
                                    .setDuration(1500)
                                    .alpha(1f)
                                    .start()
                                // load poster
                                setLinkForPoster(filmLink)
                                // close promo button
                                watchButton.setOnClickListener {
                                    visibility = View.GONE
                                }
                            }
                        }
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed()
                finish()
            } else {
                Toast.makeText(this, "Double tap to exit", Toast.LENGTH_SHORT).show()
            }

            backPressed = System.currentTimeMillis()
        } else
            super.onBackPressed()
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        // run fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    // if fragment not found (null) create new one
                    changeFragment( fragment?: HomeFragment(), tag)
                    true
                }
                R.id.favorites -> {
                    Toast.makeText(this, R.string.toast_pro_version, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, R.string.toast_pro_version, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SettingsFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    // find Fragment by tag
    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }


    companion object {
        const val TIME_INTERVAL = 2000
    }
}