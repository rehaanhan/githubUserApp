package com.dicoding.githubuser.data.di

import android.content.Context
import com.dicoding.githubuser.data.repository.FavRepository
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.data.retrofit.FavRoom
import com.dicoding.githubuser.data.setting.SettingPreference
import com.dicoding.githubuser.data.setting.dataStore
import com.dicoding.githubuser.data.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavRepository {
        val database = FavRoom.getDataBase(context)
        val dao = database.favDao()
        val preferences = SettingPreference.getInstance(context.dataStore)
        val appExecutors = AppExecutors()
        return FavRepository.getInstance(dao, appExecutors, preferences)
    }
}