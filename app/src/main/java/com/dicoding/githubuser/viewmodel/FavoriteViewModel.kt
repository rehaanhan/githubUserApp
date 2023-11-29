package com.dicoding.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.repository.FavRepository
import com.dicoding.githubuser.data.retrofit.FavRoom
import com.dicoding.githubuser.data.retrofit.FavoriteUser
import com.dicoding.githubuser.data.retrofit.UserDao

class FavoriteViewModel(private val favRepository: FavRepository): ViewModel(){


    fun getFavorite() = favRepository.getFavorite()

}