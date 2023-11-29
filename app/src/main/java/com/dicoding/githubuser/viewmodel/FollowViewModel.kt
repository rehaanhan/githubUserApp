package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

    private var _followersUserResponse = MutableLiveData<List<FollowResponse>?>()
    val followersUserResponse: LiveData<List<FollowResponse>?> = _followersUserResponse

    private var _followingUserResponse = MutableLiveData<List<FollowResponse>?>()
    val followingUserResponse: LiveData<List<FollowResponse>?> = _followingUserResponse

    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    companion object {
        private const val TAG = "FollowViewModel"
    }

    fun getUserFollowers(username: String?) {
        _isLoadingFollowers.value = true

        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object: Callback<List<FollowResponse>> {
            override fun onResponse(
                call: Call<List<FollowResponse>>,
                response: Response<List<FollowResponse>>
            ) {
                _isLoadingFollowers.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followersUserResponse.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponse>>, t: Throwable) {
                _isLoadingFollowers.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserFollowing(username: String?) {
        _isLoadingFollowing.value = true

        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object: Callback<List<FollowResponse>> {
            override fun onResponse(
                call: Call<List<FollowResponse>>,
                response: Response<List<FollowResponse>>
            ) {
                _isLoadingFollowing.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followingUserResponse.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponse>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}