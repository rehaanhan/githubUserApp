package com.dicoding.githubuser.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.mygithubprofile.R

class FollowUserAdapter(private val listFollowUser: List<FollowResponse>) : RecyclerView.Adapter<FollowUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_search, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvUsername.text = listFollowUser[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listFollowUser[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.imgProfileUser)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProfileUser: ImageView = view.findViewById(R.id.iv_profile)
        val tvUsername: TextView = view.findViewById(R.id.tv_Username)
    }

    override fun getItemCount() = listFollowUser.size
}