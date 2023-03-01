package com.example.cookbook.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.R
import com.example.cookbook.adapters.RecipesAdapter
import com.example.cookbook.databinding.FragmentBookmarksBinding
import com.example.cookbook.databinding.FragmentRecipesBinding
import com.example.cookbook.util.observeOnce
import com.example.cookbook.viewmodels.MainViewModel
import com.example.cookbook.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadDataFromCache()
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readBookmarks.observe(viewLifecycleOwner) { database ->
                database.forEach(){bookmarks ->
                    bookmarks.result.IdBookmark = bookmarks.id
                }
                mAdapter.setDataBookmarks(database)
                hideShimmerEffect()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.bookmarksRC.adapter = mAdapter
        binding.bookmarksRC.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.bookmarksRC.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.bookmarksRC.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}