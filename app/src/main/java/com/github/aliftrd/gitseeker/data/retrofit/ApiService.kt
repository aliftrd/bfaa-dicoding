package com.github.aliftrd.gitseeker.data.retrofit

import com.github.aliftrd.gitseeker.data.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getAll(): Call<List<UserResponse>>

    @GET("search/users")
    fun searchUser(@Query("q") username: String): Call<UserSearchResponse>

    @GET("users/{username}")
    fun showUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun showFollowers(@Path("username") username: String): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun showFollowing(@Path("username") username: String): Call<List<UserResponse>>
}