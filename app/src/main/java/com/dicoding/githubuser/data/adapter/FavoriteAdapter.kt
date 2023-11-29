package com.dicoding.githubuser.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.retrofit.FavoriteUser
import com.dicoding.githubuser.ui.DetailUserActivity
import com.dicoding.mygithubprofile.R


class FavoriteAdapter(private val listFavorite: List<FavoriteUser>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_search, viewGroup, false))
    }

    override fun getItemCount() = listFavorite.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvusername.text = listFavorite[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listFavorite[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.imgProfile)

        viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, listFavorite[position].username)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgProfile: ImageView = view.findViewById(R.id.iv_profile)
        val tvusername: TextView = view.findViewById(R.id.tv_Username)

    }
}