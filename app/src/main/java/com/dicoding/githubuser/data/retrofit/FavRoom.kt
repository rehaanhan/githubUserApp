package com.dicoding.githubuser.data.retrofit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavRoom : RoomDatabase(){
    abstract fun favDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE : FavRoom? = null

        @JvmStatic
        fun getDataBase(context: Context):FavRoom{
            if (INSTANCE == null){
                synchronized(FavRoom::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavRoom::class.java, "favorite_database")
                        .build()
                }
            }
            return INSTANCE as FavRoom

        }
    }
}