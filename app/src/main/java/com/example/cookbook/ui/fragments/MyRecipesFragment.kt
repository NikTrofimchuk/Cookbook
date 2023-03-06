package com.example.cookbook.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.R
import com.example.cookbook.adapters.MyRecipesAdapter
import com.example.cookbook.databinding.FragmentMyRecipesBinding
import com.example.cookbook.viewmodels.MyRecipesViewModel
import kotlinx.android.synthetic.main.fragment_my_recipes.*
import kotlinx.coroutines.launch

class MyRecipesFragment : Fragment() {

    private val mAdapter by lazy { MyRecipesAdapter() }
    private lateinit var binding: FragmentMyRecipesBinding
    private lateinit var myRecipesViewModel: MyRecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myRecipesViewModel = ViewModelProvider(requireActivity()).get(MyRecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyRecipesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ){
        super.onViewCreated(view, savedInstanceState)
        add_recipes_btn.setOnClickListener {
            Log.v("myrecipe","click")
            findNavController().navigate(R.id.action_myRecipesFragment_to_addRecipeActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        loadDataFromCache()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            myRecipesViewModel.readMyRecipes.observe(viewLifecycleOwner) { database ->
                mAdapter.setData(database)
                Log.v("myrecipe",database.toString())
            }
        }
    }
}