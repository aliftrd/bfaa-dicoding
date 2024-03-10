package com.github.aliftrd.gitseeker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.github.aliftrd.gitseeker.databinding.ActivitySettingBinding
import com.github.aliftrd.gitseeker.ui.viewmodel.SettingViewModel
import com.github.aliftrd.gitseeker.ui.viewmodel.ViewModelSettingFactory
import com.github.aliftrd.gitseeker.util.SettingPreferences
import com.github.aliftrd.gitseeker.util.dataStore

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelSettingFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { themeMode: Int ->
            when (themeMode) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    binding.themeAuto.isChecked = true
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.themeLight.isChecked = true
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.themeDark.isChecked = true
                }
            }
        }

        binding.themeContainer.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.themeAuto.id -> settingViewModel.saveThemeSetting(0)
                binding.themeLight.id -> settingViewModel.saveThemeSetting(1)
                binding.themeDark.id -> settingViewModel.saveThemeSetting(2)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}