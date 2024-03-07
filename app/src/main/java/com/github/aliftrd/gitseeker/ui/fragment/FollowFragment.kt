package com.github.aliftrd.gitseeker.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aliftrd.gitseeker.data.response.UserResponse
import com.github.aliftrd.gitseeker.databinding.FragmentFollowBinding
import com.github.aliftrd.gitseeker.ui.adapter.FollowPagerAdapter
import com.github.aliftrd.gitseeker.ui.adapter.UserAdapter
import com.github.aliftrd.gitseeker.ui.viewmodel.FollowerViewModel
import com.github.aliftrd.gitseeker.ui.viewmodel.FollowingViewModel

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var username: String
    private lateinit var followMode: String
    private val followerViewModel by viewModels<FollowerViewModel>()
    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)

        username = arguments?.getString(FollowPagerAdapter.EXTRA_USER) as String
        followMode = arguments?.getString(FollowPagerAdapter.EXTRA_FOLLOW_MODE) as String

        if(followMode == FollowPagerAdapter.EXTRA_FOLLOWER) {
            if (followerViewModel.followers.value.isNullOrEmpty()) {
                followerViewModel.getFollower(username)
            }

            followerViewModel.followers.observe(viewLifecycleOwner) {
                setFollowerContent(it)
            }

            followerViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            followerViewModel.isError.observe(viewLifecycleOwner) {
                showErrorOccurred(it)
            }
        } else {
            if (followingViewModel.followings.value.isNullOrEmpty()) {
                followingViewModel.getFollowing(username)
            }

            followingViewModel.followings.observe(viewLifecycleOwner) {
                setFollowerContent(it)
            }

            followingViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            followingViewModel.isError.observe(viewLifecycleOwner) {
                showErrorOccurred(it)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setFollowerContent(users: List<UserResponse>) {
        val layoutManager = LinearLayoutManager(context)
        binding.rvUsers.layoutManager = layoutManager

        val adapter = UserAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter
    }

    private fun showErrorOccurred(isError: Boolean) {
        if (isError) Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        fun newInstance(username: String, followMode: String): Fragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowPagerAdapter.EXTRA_USER, username)
                    putString(FollowPagerAdapter.EXTRA_FOLLOW_MODE, followMode)
                }
            }
        }
    }
}