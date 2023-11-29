package com.dicoding.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class FollowResponse(
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String
)
