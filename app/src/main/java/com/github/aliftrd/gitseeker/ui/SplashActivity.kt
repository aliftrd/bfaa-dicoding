package com.github.aliftrd.gitseeker.ui

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.github.aliftrd.gitseeker.databinding.ActivitySplashBinding
import com.github.aliftrd.gitseeker.ui.viewmodel.SettingViewModel
import com.github.aliftrd.gitseeker.ui.viewmodel.ViewModelSettingFactory
import com.github.aliftrd.gitseeker.util.SettingPreferences
import com.github.aliftrd.gitseeker.util.dataStore

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelSettingFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { themeMode: Int ->
            when(themeMode) {
                0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        Handler(mainLooper).postDelayed({
            binding.progressBar.max = 1000
            ObjectAnimator.ofInt(binding.progressBar, "progress", 800).setDuration(2000).start()

            Handler(mainLooper).postDelayed({
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }, 2000)
        }, 1000)
    }
}