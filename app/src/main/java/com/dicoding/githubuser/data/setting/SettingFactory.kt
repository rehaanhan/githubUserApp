package com.dicoding.githubuser.data.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingFactory(private val pref: SettingPreference): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Uknown viewModel class: "+ modelClass.name)
    }
}