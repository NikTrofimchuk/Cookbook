package com.example.cookbook.data

import com.example.cookbook.data.database.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readMyRecipesDatabase(): Flow<List<MyRecipesEntity>> {
        return recipesDao.readMyRecipes()
    }

    suspend fun insertMyRecipes(myRecipesEntity: MyRecipesEntity) {
        recipesDao.insertMyRecipes(myRecipesEntity)
    }

    suspend fun deleteMyRecipes(title: String){
        recipesDao.deleteMyRecipes(title)
    }

    fun readBookmarksTable(): Flow<List<BookmarkEntity>> {
        return recipesDao.readBookmarks()
    }

    suspend fun insertBookmarks(bookmarkEntity: BookmarkEntity):Long {
        return recipesDao.insertBookmarks(bookmarkEntity)
    }

    suspend fun deleteFromBookmark(id: Int){
        recipesDao.deleteBookmark(id)
    }

    suspend fun insertBasket(basketEntity: BasketEntity){
        recipesDao.insertBasket(basketEntity)
    }

    fun readBasket() : Flow<List<BasketEntity>> {
        return recipesDao.readBasket()
    }

    suspend fun deleteBasketTable(){
        recipesDao.deleteBasketTable()
    }

    suspend fun updateBasket(name:String, multiplier : Int){
        recipesDao.updateBasket(name, multiplier)
    }
}