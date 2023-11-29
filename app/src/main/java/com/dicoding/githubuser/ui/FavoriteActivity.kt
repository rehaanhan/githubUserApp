package com.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuser.data.adapter.FavoriteAdapter
import com.dicoding.githubuser.viewmodel.FavoriteViewModel
import com.dicoding.githubuser.viewmodel.ViewModelFactory
import com.dicoding.mygithubprofile.R
import com.dicoding.mygithubprofile.databinding.ActivityFavoriteBinding
import java.util.ArrayList

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)

        favoriteViewModel.getFavorite().observe(this){listUserFavorite ->
            binding.rvFavorite.adapter = FavoriteAdapter(listUserFavorite)
        }
    }

}