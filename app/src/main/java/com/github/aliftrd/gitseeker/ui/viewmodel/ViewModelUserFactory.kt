package com.github.aliftrd.gitseeker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelUserFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserFavoriteViewModel::class.java)) {
            return UserFavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelUserFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelUserFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelUserFactory::class.java) {
                    INSTANCE = ViewModelUserFactory(application)
                }
            }
            return INSTANCE as ViewModelUserFactory
        }
    }
}