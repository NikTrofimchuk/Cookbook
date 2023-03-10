package com.example.cookbook.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cookbook.data.Repository
import com.example.cookbook.data.database.BasketEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BasketViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    val readBasket: LiveData<List<BasketEntity>> = repository.local.readBasket().asLiveData()

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
}