package com.github.aliftrd.gitseeker.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.aliftrd.gitseeker.data.UserFavoriteRepository
import com.github.aliftrd.gitseeker.data.source.local.entity.UserFavorite
import com.github.aliftrd.gitseeker.data.source.remote.response.DetailUserResponse
import com.github.aliftrd.gitseeker.data.source.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {
    private val userFavoriteRepository = UserFavoriteRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    val isFavorite = MutableLiveData<Boolean>()

    fun setFavorite(user: UserFavorite) = if (isFavorite.value == true) userFavoriteRepository.unsetFavorite(user) else userFavoriteRepository.setFavorite(user)
    fun checkFavorite(username: String): LiveData<UserFavorite> = userFavoriteRepository.checkFavorite(username)

    fun showUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().showUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private val TAG = DetailUserViewModel::class.java.simpleName
    }
}