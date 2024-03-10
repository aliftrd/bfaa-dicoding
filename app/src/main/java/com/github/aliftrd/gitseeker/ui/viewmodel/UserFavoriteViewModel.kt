package com.github.aliftrd.gitseeker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.github.aliftrd.gitseeker.data.UserFavoriteRepository
import com.github.aliftrd.gitseeker.data.source.local.entity.UserFavorite

class UserFavoriteViewModel(application: Application): ViewModel() {
    private val userFavoriteRepository = UserFavoriteRepository(application)

    fun getFavoriteUsers() = userFavoriteRepository.getAll()
    fun unsetFavorite(user: UserFavorite) = userFavoriteRepository.unsetFavorite(user)
}