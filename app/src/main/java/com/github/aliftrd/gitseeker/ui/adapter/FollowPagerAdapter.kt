package com.github.aliftrd.gitseeker.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.aliftrd.gitseeker.ui.fragment.FollowFragment

class FollowPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(username, when(position) {
            0 -> EXTRA_FOLLOWER
            else -> EXTRA_FOLLOWING
        })
    }

    companion object {
        const val EXTRA_FOLLOW_MODE: String = "follower"
        const val EXTRA_USER: String = "username"
        const val EXTRA_FOLLOWER: String = "follower"
        const val EXTRA_FOLLOWING: String = "following"
    }
}