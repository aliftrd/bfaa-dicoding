package com.github.aliftrd.gitseeker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.github.aliftrd.gitseeker.R
import com.github.aliftrd.gitseeker.databinding.ActivityDetailUserBinding
import com.github.aliftrd.gitseeker.ui.adapter.FollowPagerAdapter
import com.github.aliftrd.gitseeker.ui.viewmodel.DetailUserViewModel
import com.github.aliftrd.gitseeker.util.getFormattedNumber
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USER) as String

        setToolbar()
        setViewPager()

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        if (detailUserViewModel.user.value == null) {
            detailUserViewModel.showUser(username)
        }

        detailUserViewModel.user.observe(this) { user ->
            with(binding) {
                Glide.with(this@DetailUserActivity).load(user.avatarUrl).into(userImage)
                repoCount.text = getFormattedNumber(user.publicRepos.toLong())
                followerCount.text = getFormattedNumber(user.followers.toLong())
                followingCount.text = getFormattedNumber(user.following.toLong())
                name.text = user.name ?: "-"
                location.text = user.location ?: "-"

                btnGoToProfile.setOnClickListener {
                    Intent(Intent.ACTION_VIEW, user.htmlUrl.toUri()).apply {
                        startActivity(this)
                    }
                }
            }
        }

        detailUserViewModel.isError.observe(this) {
            if (it) showErrorOccurred(it)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.collapsingToolbar.isTitleEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = username
    }

    private fun setViewPager() {
        binding.viewPager.adapter = FollowPagerAdapter(this, username)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showErrorOccurred(isError: Boolean) {
        if (isError) Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            appbar.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            viewPager.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    companion object {
        const val EXTRA_USER: String = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }
}