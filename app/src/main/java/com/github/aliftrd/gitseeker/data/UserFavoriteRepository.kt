package com.github.aliftrd.gitseeker.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.github.aliftrd.gitseeker.data.source.local.entity.UserFavorite
import com.github.aliftrd.gitseeker.data.source.local.room.GitSeekerRoomDatabase
import com.github.aliftrd.gitseeker.data.source.local.room.UserFavoriteDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserFavoriteRepository(application: Application) {
    private val userFavoriteDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GitSeekerRoomDatabase.getDatabase(application)
        userFavoriteDao = db.userFavoriteDao()
    }

    fun getAll(): LiveData<List<UserFavorite>> = userFavoriteDao.getAll()

    fun setFavorite(user: UserFavorite) {
        executorService.execute { userFavoriteDao.setFavorite(user) }
    }

    fun unsetFavorite(user: UserFavorite) {
        executorService.execute { userFavoriteDao.unsetFavorite(user) }
    }

    fun checkFavorite(username: String): LiveData<UserFavorite> = userFavoriteDao.checkFavorite(username)
}