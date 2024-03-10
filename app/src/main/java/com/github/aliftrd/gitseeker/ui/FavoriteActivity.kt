package com.github.aliftrd.gitseeker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aliftrd.gitseeker.data.source.local.entity.UserFavorite
import com.github.aliftrd.gitseeker.databinding.ActivityFavoriteBinding
import com.github.aliftrd.gitseeker.ui.adapter.UserFavoriteAdapter
import com.github.aliftrd.gitseeker.ui.viewmodel.UserFavoriteViewModel
import com.github.aliftrd.gitseeker.ui.viewmodel.ViewModelUserFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userFavoriteViewModel: UserFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        userFavoriteViewModel = obtainViewModel(this)
        userFavoriteViewModel.getFavoriteUsers().observe(this) { users ->
            if (users.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
            }
            setUserData(users)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserFavoriteViewModel {
        val factory = ViewModelUserFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[UserFavoriteViewModel::class.java]
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUserData(users: List<UserFavorite>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        val adapter = UserFavoriteAdapter()
        adapter.setListUser(users)
        binding.rvFavorite.adapter = adapter
    }


    companion object {
        private val TAG = FavoriteActivity::class.java.simpleName
    }
}