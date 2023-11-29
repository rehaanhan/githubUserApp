package com.dicoding.githubuser.data.repository


import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.retrofit.FavoriteUser
import com.dicoding.githubuser.data.retrofit.UserDao
import com.dicoding.githubuser.data.setting.SettingPreference
import com.dicoding.githubuser.data.utils.AppExecutors
import kotlinx.coroutines.flow.Flow


class FavRepository (
    private val mUserDao : UserDao,
    private val appExecutors: AppExecutors,
    private val preference: SettingPreference
) {
    fun getFavorite():LiveData<List<FavoriteUser>> {
        return mUserDao.getFavorite()
    }
    fun insert(favoriteUser: FavoriteUser){
        appExecutors.diskIO.execute { mUserDao.insertUser(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser){
        appExecutors.diskIO.execute { mUserDao.delete(favoriteUser) }
    }

    fun cekUser(username: String): LiveData<FavoriteUser>{
        return mUserDao.checkUser(username)
    }

    fun getThemeSetting(): Flow<Boolean> {
        return preference.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarModeActive : Boolean){
        return preference.saveThemeSetting(isDarModeActive)
    }

    companion object{
        @Volatile
        private var instance : FavRepository? = null

        fun getInstance(
            userDao: UserDao,
            appExecutors: AppExecutors,
            preference: SettingPreference
        ):FavRepository = instance ?: synchronized(this){
            instance ?: FavRepository(userDao, appExecutors, preference)
        }.also { instance = it }
    }

}