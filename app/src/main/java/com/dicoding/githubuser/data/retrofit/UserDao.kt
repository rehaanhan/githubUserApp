package com.dicoding.githubuser.data.retrofit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser")
    fun getFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun checkUser(username: String): LiveData<FavoriteUser>

    @Delete
    fun delete(favoriteUser : FavoriteUser )

}