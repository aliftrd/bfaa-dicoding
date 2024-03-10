package com.github.aliftrd.gitseeker.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.aliftrd.gitseeker.data.source.local.entity.UserFavorite

@Dao
interface UserFavoriteDao {
    @Query("SELECT * FROM UserFavorite ORDER BY username ASC")
    fun getAll(): LiveData<List<UserFavorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setFavorite(user: UserFavorite)

    @Delete
    fun unsetFavorite(user: UserFavorite)

    @Query("SELECT * FROM UserFavorite WHERE username = :username")
    fun checkFavorite(username: String): LiveData<UserFavorite>
}