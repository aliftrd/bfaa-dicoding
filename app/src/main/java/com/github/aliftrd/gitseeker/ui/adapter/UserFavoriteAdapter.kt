package com.github.aliftrd.gitseeker.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.aliftrd.gitseeker.data.source.local.entity.UserFavorite
import com.github.aliftrd.gitseeker.databinding.ItemUserFavoriteBinding
import com.github.aliftrd.gitseeker.ui.DetailUserActivity
import com.github.aliftrd.gitseeker.ui.viewmodel.UserFavoriteViewModel
import com.github.aliftrd.gitseeker.ui.viewmodel.ViewModelUserFactory
import com.github.aliftrd.gitseeker.util.callback.UserFavoriteDiffCallback

class UserFavoriteAdapter : RecyclerView.Adapter<UserFavoriteAdapter.UserFavoriteViewHolder>() {
    private val users = ArrayList<UserFavorite>()

    fun setListUser(users: List<UserFavorite>) {
        val diffCallback = UserFavoriteDiffCallback(this.users, users)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.users.clear()
        this.users.addAll(users)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavoriteViewHolder {
        val binding =
            ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFavoriteViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UserFavoriteViewHolder(private val binding: ItemUserFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserFavorite) {
            val userFavoriteViewModel = obtainViewModel(itemView.context as AppCompatActivity)
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(userImage)
                username.text = user.username

                btnUnfavorite.setOnClickListener {
                    userFavoriteViewModel.unsetFavorite(user)
                }
            }

            with(itemView) {
                setOnClickListener {
                    Intent(context, DetailUserActivity::class.java).apply {
                        putExtra(DetailUserActivity.EXTRA_USER, user.username)
                        context.startActivity(this)
                    }
                }
            }
        }

        private fun obtainViewModel(activity: AppCompatActivity): UserFavoriteViewModel {
            val factory = ViewModelUserFactory.getInstance(activity.application)
            return ViewModelProvider(activity, factory)[UserFavoriteViewModel::class.java]
        }
    }
}