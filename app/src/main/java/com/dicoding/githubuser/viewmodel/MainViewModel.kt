package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.repository.FavRepository
import com.dicoding.githubuser.data.response.SearchResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.data.setting.SettingPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val favRepository: FavRepository): ViewModel() {

    private val _searchUserResponse = MutableLiveData<SearchResponse?>()
    val searchUserResponse: LiveData<SearchResponse?> = _searchUserResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        searchUser("rehaanhan")
    }

    fun searchUser(username: String?) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object: Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _searchUserResponse.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun saveThemeSetting(isDarModeActive : Boolean){
        viewModelScope.launch {
            favRepository.saveThemeSetting(isDarModeActive)
        }

    }

    fun getThemeSetting(): LiveData<Boolean> {
        return favRepository.getThemeSetting().asLiveData()
    }

}