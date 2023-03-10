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
import com.example.cookbook.adapters.BasketIngredientsAdapter
import com.example.cookbook.adapters.BasketRecipesAdapter
import com.example.cookbook.databinding.FragmentBasketBinding
import com.example.cookbook.models.BasketRecipe
import com.example.cookbook.models.ExtendedIngredient
import com.example.cookbook.viewmodels.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BasketFragment : Fragment() {

    private val basketIngredientsAdapter by lazy { BasketIngredientsAdapter() }
    private val basketRecipesAdapter by lazy { BasketRecipesAdapter() }

    private lateinit var basketViewModel: BasketViewModel

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        basketViewModel = ViewModelProvider(requireActivity()).get(BasketViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadDataFromCache()
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            basketViewModel.readBasket.observe(viewLifecycleOwner) { database ->
                Log.d("BasketFragment", database.toString())
                val ingredients = mutableListOf<ExtendedIngredient>()
                val recipes = mutableListOf<BasketRecipe>()
                database.forEach { basket ->
                    Log.d("BasketFragment", basket.toString())
                    ingredients.addAll(basket.extendedIngredient.toList())

                    val recipe = BasketRecipe(basket.recipesName, basket.multiplier)
                    recipes.add(recipe)
                }
                basketIngredientsAdapter.setData(ingredients)
                basketRecipesAdapter.setData(recipes)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.ingredientsRC.adapter = basketIngredientsAdapter
        binding.ingredientsRC.layoutManager = LinearLayoutManager(requireContext())

        binding.recipesRC.adapter = basketRecipesAdapter
        binding.recipesRC.layoutManager = LinearLayoutManager(requireContext())
        Log.d("BasketFragment", "Set adapter")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}