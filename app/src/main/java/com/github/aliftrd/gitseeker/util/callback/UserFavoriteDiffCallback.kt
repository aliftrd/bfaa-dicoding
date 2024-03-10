package com.github.aliftrd.gitseeker.util.callback

import androidx.recyclerview.widget.DiffUtil
import com.github.aliftrd.gitseeker.data.source.local.entity.UserFavorite

class UserFavoriteDiffCallback(
    private val oldFavoriteList: List<UserFavorite>,
    private val newFavoriteList: List<UserFavorite>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size

    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavoriteList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavoriteList[oldItemPosition]
        val newFavorite = newFavoriteList[newItemPosition]
        return oldFavorite.username == newFavorite.username && oldFavorite.avatarUrl == newFavorite.avatarUrl
    }
}