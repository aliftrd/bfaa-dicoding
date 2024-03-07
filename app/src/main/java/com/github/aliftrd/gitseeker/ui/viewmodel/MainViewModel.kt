package com.github.aliftrd.gitseeker.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.aliftrd.gitseeker.data.response.UserResponse
import com.github.aliftrd.gitseeker.data.response.UserSearchResponse
import com.github.aliftrd.gitseeker.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> = _users

    init {
        getGithubUser()
    }

    private fun getGithubUser() {
        _isLoading.value = true
        _users.value = arrayListOf()

        val client = ApiConfig.getApiService().getAll()
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) _users.value = response.body()
                else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findGithubUser(username: String? = null) {
        if (username.isNullOrEmpty()) {
            getGithubUser()
        } else {
            _isLoading.value = true
            _users.value = arrayListOf()

            val client = ApiConfig.getApiService().searchUser(username)
            client.enqueue(object : Callback<UserSearchResponse> {
                override fun onResponse(
                    call: Call<UserSearchResponse>,
                    response: Response<UserSearchResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) _users.value = response.body()?.items
                    else Log.e(TAG, "onFailure: ${response.message()}")

                }

                override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}