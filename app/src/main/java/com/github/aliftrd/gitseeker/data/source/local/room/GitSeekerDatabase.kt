package com.github.aliftrd.gitseeker.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.aliftrd.gitseeker.data.source.local.entity.UserFavorite

@Database(entities = [UserFavorite::class], version = 1)
abstract class GitSeekerRoomDatabase : RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: GitSeekerRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GitSeekerRoomDatabase {
            if (INSTANCE == null) {
                synchronized(GitSeekerRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GitSeekerRoomDatabase::class.java, "gitseeker_database")
                        .build()
                }
            }
            return INSTANCE as GitSeekerRoomDatabase
        }
    }
}