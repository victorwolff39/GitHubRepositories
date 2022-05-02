package com.victorwolff.githubrepositories.ui

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victorwolff.githubrepositories.adapter.RepositoryListAdapter
import com.victorwolff.githubrepositories.databinding.FragmentRepositoryListBinding


class RepositoryListFragment : Fragment() {
    private var _binding: FragmentRepositoryListBinding? = null
    private val binding get() = _binding!!
    private var repositoryListViewModel: RepositoryListViewModel? = null
    private val adapter = RepositoryListAdapter()
    private var isLoading = false
    private var isScroll = false
    private var message = ""
    private var currentItem = -1
    private var totalItem = -1
    private var scrollOutItem = -1
    private var areAllRepositoriesLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)


            super.onViewCreated(view, savedInstanceState)

            val manager = LinearLayoutManager(requireContext())
            binding.rvRepository.adapter = adapter
            binding.rvRepository.layoutManager = manager

            binding.rvRepository.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScroll = true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    currentItem = manager.childCount
                    totalItem = manager.itemCount
                    scrollOutItem = manager.findFirstVisibleItemPosition()

                    if (isScroll && (currentItem + scrollOutItem == totalItem)) {
                        isScroll = false
                        if (!isLoading) {
                            if (!areAllRepositoriesLoaded) {
                                repositoryListViewModel?.getRepositoryList()
                            } else {
                                repositoryListViewModel?.getRepositoryList()
                            }
                        }
                    }
                }
            })

            binding.swipeContainer.setOnRefreshListener {
                adapter.clear()
                repositoryListViewModel?.refreshRepositoryList()
                binding.swipeContainer.isRefreshing = false
            }

            repositoryListViewModel?.repository?.observe(viewLifecycleOwner) {
                if (it != null && it.items.isNotEmpty()) {
                    adapter.setData(it.items, binding.rvRepository)
                }
            }

            repositoryListViewModel?.isLoading?.observe(viewLifecycleOwner) {
                isLoading = it
                binding.swipeContainer.isRefreshing = isLoading
            }

            repositoryListViewModel?.areAllRepositoriesLoaded?.observe(viewLifecycleOwner) {
                areAllRepositoriesLoaded = it

                //When all repositories are loaded...
                /*if (it) Toast.makeText(
                    requireContext(),
                    "All repositories loaded",
                    Toast.LENGTH_SHORT
                ).show()*/
            }

            repositoryListViewModel?.message?.observe(viewLifecycleOwner) {
                message = it
                Toast.makeText(
                    requireContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repositoryListViewModel = ViewModelProvider(this).get(RepositoryListViewModel::class.java)
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        return binding.root
    }
}