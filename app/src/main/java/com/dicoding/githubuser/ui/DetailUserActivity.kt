package com.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.adapter.SectionsPagerAdapter
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.retrofit.FavoriteUser
import com.dicoding.githubuser.viewmodel.DetailUserViewModel
import com.dicoding.githubuser.viewmodel.ViewModelFactory
import com.dicoding.mygithubprofile.R
import com.dicoding.mygithubprofile.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(this)
    }


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "User Detail"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        viewModel.getDetailUser(username)

        viewModel.userDetailResponse.observe(this) {detailResponse ->
            setDetailData(detailResponse)
        }


        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


    }

    private fun setDetailData(detailUserResponse: DetailUserResponse?) {
        if (detailUserResponse != null) {
            Glide.with(this)
                .load(detailUserResponse.avatarUrl)
                .circleCrop()
                .into(binding.ivProfile)
            binding.tvName.text = detailUserResponse.name
            binding.tvUsername.text = detailUserResponse.username
            binding.tvFollowers.text = getString(R.string.followers_count, detailUserResponse.followers)
            binding.tvFollowing.text = getString(R.string.following_count, detailUserResponse.following)

            viewModel.getFavUser(detailUserResponse.username as String).observe(this@DetailUserActivity){favUser ->
                if(favUser == null){
                    setFavIcon(false)
                    binding.fabAdd.setOnClickListener {
                            val favUser = FavoriteUser(detailUserResponse.username, detailUserResponse.avatarUrl)
                        viewModel.insertFav(favUser)
                    }
                }else{
                    setFavIcon(true)
                    binding.fabAdd.setOnClickListener {
                        viewModel.delete(favUser)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setFavIcon(isfavorite: Boolean){
        if (isfavorite){
            binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(this@DetailUserActivity, R.drawable.ic_favorite))
        }else{
            binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(this@DetailUserActivity, R.drawable.ic_favorite_border))
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}