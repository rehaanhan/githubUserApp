package com.dicoding.githubuser.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.data.di.Injection
import com.dicoding.githubuser.data.repository.FavRepository
import com.dicoding.githubuser.data.setting.SettingPreference
import com.dicoding.githubuser.data.setting.SettingViewModel

class ViewModelFactory(private val favRepository: FavRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(favRepository) as T
        }
        else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)){
            return DetailUserViewModel(favRepository) as T
        }
        else if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(favRepository) as T
            }
        throw IllegalArgumentException("Uknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance : ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}