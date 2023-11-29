package com.dicoding.githubuser.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.SearchUserResponse
import com.dicoding.mygithubprofile.R

class SearchUserAdapter(private val listSearchUser: List<SearchUserResponse>) : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_search, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvUserName.text = listSearchUser[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listSearchUser[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.imgProfileUser)

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listSearchUser[position].username)
        }
    }

    override fun getItemCount() = listSearchUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProfileUser: ImageView = view.findViewById(R.id.iv_profile)
        val tvUserName: TextView = view.findViewById(R.id.tv_Username)
    }

    interface OnItemClickCallback {
        fun onItemClicked(username: String)
    }
}