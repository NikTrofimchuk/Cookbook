package com.example.cookbook.data

import com.example.cookbook.data.database.BookmarkEntity
import com.example.cookbook.data.database.MyRecipesEntity
import com.example.cookbook.data.database.RecipesDao
import com.example.cookbook.data.database.RecipesEntity
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

    suspend fun insertBookmarks(bookmarkEntity: BookmarkEntity) {
        recipesDao.insertBookmarks(bookmarkEntity)
    }

    suspend fun deleteBookmark(id: Int){
        recipesDao.deleteBookmark(id)
    }
}