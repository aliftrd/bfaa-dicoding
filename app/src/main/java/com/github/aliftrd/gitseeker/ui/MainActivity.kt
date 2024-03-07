package com.github.aliftrd.gitseeker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aliftrd.gitseeker.data.response.UserResponse
import com.github.aliftrd.gitseeker.databinding.ActivityMainBinding
import com.github.aliftrd.gitseeker.ui.adapter.UserAdapter
import com.github.aliftrd.gitseeker.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        mainViewModel.users.observe(this) { user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        mainViewModel.isError.observe(this) { isError ->
            showErrorOccurred(isError)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                mainViewModel.findGithubUser(searchBar.text.toString())
                false

            }
        }
    }

    private fun setToolbar() {
        supportActionBar?.hide()
    }

    private fun setUserData(users: List<UserResponse>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val adapter = UserAdapter()
        adapter.submitList(users)
        binding.rvUser.adapter = adapter
    }

    private fun showErrorOccurred(isError: Boolean) {
        if (isError) Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}