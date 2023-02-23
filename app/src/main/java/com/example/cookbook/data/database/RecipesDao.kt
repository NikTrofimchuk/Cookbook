package com.example.cookbook.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyRecipes(myRecipesEntity: MyRecipesEntity)

    @Query("SELECT * FROM myrecipes_table ORDER BY id ASC")
    fun readMyRecipes(): Flow<List<MyRecipesEntity>>

    @Query("DELETE FROM myrecipes_table WHERE title = :title")
    suspend fun deleteMyRecipes(title: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarks(bookmarkEntity: BookmarkEntity)

    @Query("SELECT * FROM bookmarks_table ORDER BY id ASC")
    fun readBookmarks(): Flow<List<BookmarkEntity>>

    @Query("DELETE FROM bookmarks_table WHERE id = :id")
    suspend fun deleteBookmark(id: Int)
}