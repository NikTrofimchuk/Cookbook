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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clear_basket_btn.setOnClickListener{
            Log.d("BasketFragment", "setOnClickListener")
            basketViewModel.clearBasket()
        }
    }

    private fun setupRecyclerView() {
        binding.ingredientsRC.adapter = basketIngredientsAdapter
        binding.ingredientsRC.layoutManager = LinearLayoutManager(requireContext())

        binding.recipesRC.adapter = basketRecipesAdapter
        binding.recipesRC.layoutManager = LinearLayoutManager(requireContext())
        Log.d("BasketFragment", "Set adapter")

        basketViewModel.ingredientsLiveData.observe(viewLifecycleOwner) { ingredients ->
            basketIngredientsAdapter.setData(ingredients)
        }
        basketViewModel.recipesLiveData.observe(viewLifecycleOwner) { recipes ->
            basketRecipesAdapter.setData(recipes)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        // Остановка наблюдения за LiveData при уничтожении фрагмента
        basketViewModel.ingredientsLiveData.removeObservers(viewLifecycleOwner)
        basketViewModel.recipesLiveData.removeObservers(viewLifecycleOwner)
    }

}