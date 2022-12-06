package com.example.cookbook.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cookbook.data.Repository
import com.example.cookbook.data.database.MyRecipesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val repository: Repository
): AndroidViewModel(application) {

    val readMyRecipes: LiveData<List<MyRecipesEntity>> = repository.local.readMyRecipesDatabase().asLiveData()

    private fun insertMyRecipes(myRecipesEntity: MyRecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertMyRecipes(myRecipesEntity)
        }

}