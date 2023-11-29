package com.dicoding.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.adapter.SearchUserAdapter
import com.dicoding.githubuser.data.response.SearchResponse
import com.dicoding.githubuser.data.response.SearchUserResponse
import com.dicoding.githubuser.data.setting.SettingActivity
import com.dicoding.githubuser.data.setting.SettingFactory
import com.dicoding.githubuser.data.setting.SettingPreference
import com.dicoding.githubuser.data.setting.SettingViewModel
import com.dicoding.githubuser.data.setting.dataStore
import com.dicoding.githubuser.ui.DetailUserActivity.Companion.EXTRA_ID
import com.dicoding.githubuser.viewmodel.MainViewModel
import com.dicoding.githubuser.viewmodel.ViewModelFactory
import com.dicoding.mygithubprofile.R
import com.dicoding.mygithubprofile.databinding.ActivityMainBinding
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels{ViewModelFactory.getInstance(this)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User"

        viewModel.searchUserResponse.observe(this) { searchUserResponse ->
            setSearchUserData(searchUserResponse)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        viewModel.getThemeSetting().observe(this@MainActivity) { isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setSearchUserData(searchResponse: SearchResponse?){
        if (searchResponse != null && searchResponse.totalCount > 0) {
            val listUser = ArrayList<SearchUserResponse>()
            for (user in searchResponse.items) {
                listUser.add(
                    SearchUserResponse(user.avatarUrl, user.username)
                )
            }

            val adapter = SearchUserAdapter(listUser)
            binding.rvUser.adapter = adapter
            adapter.setOnItemClickCallback(object: SearchUserAdapter.OnItemClickCallback {
                override fun onItemClicked(username: String) {
                    val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, username)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchUserManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchUserView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchUserView.setSearchableInfo(searchUserManager.getSearchableInfo(componentName))
        searchUserView.queryHint = resources.getString(R.string.search_hint)
        searchUserView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query)
                searchUserView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorite->{
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.settings->{
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else-> false

        }
    }



    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}