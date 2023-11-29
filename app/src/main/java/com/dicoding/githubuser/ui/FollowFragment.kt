package com.dicoding.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.adapter.FollowUserAdapter
import com.dicoding.githubuser.data.repository.FavRepository
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.githubuser.viewmodel.FollowViewModel
import com.dicoding.mygithubprofile.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowViewModel

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[FollowViewModel::class.java]

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME)


        viewModel.getUserFollowing(username)
        viewModel.getUserFollowers(username)

        if (index == 1) {
            viewModel.followersUserResponse.observe(requireActivity()){ followersResponse ->
                setUserFollowData(followersResponse)
            }

            viewModel.isLoadingFollowers.observe(requireActivity()){
                showLoading(it)
            }
        } else {
            viewModel.followingUserResponse.observe(requireActivity()){followingResponse ->
                setUserFollowData(followingResponse)
            }

            viewModel.isLoadingFollowing.observe(requireActivity()) {
                showLoading(it)
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
    }

    private fun setUserFollowData(data: List<FollowResponse>?) {
        if (!data.isNullOrEmpty()) {
            val listFollow = ArrayList<FollowResponse>()
            for (user in data) {
                listFollow.add(
                    FollowResponse(user.avatarUrl, user.username)
                )
            }

            val adapter = FollowUserAdapter(listFollow)
            binding.rvUser.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}