package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.repository.FavRepository
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.data.retrofit.FavoriteUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val favRepository: FavRepository) : ViewModel() {

    private val _userDetailResponse = MutableLiveData<DetailUserResponse?>()
    val userDetailResponse: LiveData<DetailUserResponse?> = _userDetailResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(username: String?) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>,
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetailResponse.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    fun getFavUser(username: String) = favRepository.cekUser(username)
    fun insertFav(favoriteUser: FavoriteUser) = favRepository.insert(favoriteUser)
    fun delete(favoriteUser: FavoriteUser) = favRepository.delete(favoriteUser)


    companion object {
        private const val TAG = "DetailUserViewModel"
    }

}