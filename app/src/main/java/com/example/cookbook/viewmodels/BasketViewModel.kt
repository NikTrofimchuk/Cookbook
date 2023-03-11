package com.example.cookbook.viewmodels

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cookbook.data.Repository
import com.example.cookbook.data.database.BasketEntity
import com.example.cookbook.models.BasketRecipe
import com.example.cookbook.models.ExtendedIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BasketViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    private val readBasket: LiveData<List<BasketEntity>> = repository.local.readBasket().asLiveData()

    private val ingredients = MutableLiveData<List<ExtendedIngredient>>()
    private val recipes = MutableLiveData<List<BasketRecipe>>()

    val ingredientsLiveData: LiveData<List<ExtendedIngredient>> = ingredients
    val recipesLiveData: LiveData<List<BasketRecipe>> = recipes

    init {
        loadData()
    }

    private fun insertBasket(basketEntity: BasketEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertBasket(basketEntity)
        }

    private fun deleteBasketTable(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteBasketTable()
        }
    }

    private fun updateBasket(name:String, multiplier:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.updateBasket(name, multiplier)
        }
    }

    fun writeInBasket(basketEntity: BasketEntity): Job {
        return insertBasket(basketEntity)
    }

    fun clearBasket(){
        deleteBasketTable()
    }

    fun updateMultiplier(name: String, multiplier: Int){
        updateBasket(name, multiplier)
    }

    fun loadData(){
        viewModelScope.launch {
            readBasket.observeForever { database ->
                val ingredientsList = mutableListOf<ExtendedIngredient>()
                val recipesList = mutableListOf<BasketRecipe>()
                database.forEach { basketEntity ->
                    basketEntity.extendedIngredient.forEach { ingredient ->
                        // Тут идет подсчет количества ингредиента по количеству блюд в корзине
                        ingredient.amount = ingredient.amount * basketEntity.multiplier

                        // Ищем повторяющиеся по названию ингредиенты в коллекции
                        val existingIngredient = ingredientsList.find { it.name == ingredient.name }

                        // Если не найден повторяющийся, то добавляем новый ингредиент в коллекцию
                        if (existingIngredient == null) {
                            ingredientsList.add(ingredient)
                            // А иначе, то есть когда в коллекции имеется ингредиент с одинаковым названием,
                            // мы плюсуем его количество к имеющемуся в коллекции ингредиенту
                            Log.d("BasketViewModel", "setOnClickListener")
                        } else {
                            existingIngredient.amount += ingredient.amount
                        }
                    }
                    val recipe = BasketRecipe(basketEntity.recipesName, basketEntity.multiplier)
                    recipesList.add(recipe)
                }
                ingredients.value = ingredientsList
                recipes.value = recipesList
            }
        }
    }
}