package com.github.aliftrd.gitseeker.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
	@field:SerializedName("items")
	val items: List<UserResponse>
)

data class UserResponse(
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String
)

data class DetailUserResponse(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("login")
	val username: String,

	@field:SerializedName("html_url")
	val htmlUrl: String
)
