package com.dicoding.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val items: List<SearchUserResponse>
)

data class SearchUserResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String
)
