package com.github.aliftrd.gitseeker.ui

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.github.aliftrd.gitseeker.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


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