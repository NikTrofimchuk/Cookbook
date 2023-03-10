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
import com.example.cookbook.data.database.BasketEntity
import com.example.cookbook.databinding.FragmentBasketBinding
import com.example.cookbook.models.BasketRecipe
import com.example.cookbook.models.ExtendedIngredient
import com.example.cookbook.viewmodels.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BasketFragment : Fragment(), BasketRecipesAdapter.OnItemClickListener {

    private val basketIngredientsAdapter by lazy { BasketIngredientsAdapter() }
    private val basketRecipesAdapter = BasketRecipesAdapter(this)

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
        loadDataFromCache()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clear_basket_btn.setOnClickListener{
            Log.d("BasketFragment", "setOnClickListener")
            basketViewModel.clearBasket()
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            basketViewModel.readBasket.observe(viewLifecycleOwner) { database ->
                val ingredients = mutableListOf<ExtendedIngredient>()
                val recipes = mutableListOf<BasketRecipe>()
                database.forEach { basketEntity ->
                    Log.d("BasketFragment", basketEntity.toString())
                    basketEntity.extendedIngredient.forEach { ingredient ->
                        //Тут идет подсчет количества ингредиента по количеству блюд в корзине
                        ingredient.amount = ingredient.amount * basketEntity.multiplier

                        //Ишем повторяющиеся по названию ингредиенты в коллекции
                        val existingIngredient = ingredients.find { it.name == ingredient.name }
                        //Если не найден повторяющийся, то добавляем новый ингридиент в коллекцию
                        if (existingIngredient == null) {
                            ingredients.add(ingredient)
                            //А иначе, то есть когда в коллекции имеется ингредиент с одинаковым названием, мы плюсуем его количество к
                            // имеющемуся в коллекции ингредиенту
                        } else {
                            existingIngredient.amount += ingredient.amount
                        }
                    }
                    val recipe = BasketRecipe(basketEntity.recipesName, basketEntity.multiplier)
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

    override fun onMinusClick(position: Int, name: String) {
        basketViewModel.updateMultiplier(name,-1)
    }

    override fun onPlusClick(position: Int, name: String) {
        basketViewModel.updateMultiplier(name,1)
    }
}