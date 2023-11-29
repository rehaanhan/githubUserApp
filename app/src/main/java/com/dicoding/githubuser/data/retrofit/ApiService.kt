package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.githubuser.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun searchUser(
        @Query("q") query: String?
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String?
    ): Call<List<FollowResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String?
    ): Call<List<FollowResponse>>
}