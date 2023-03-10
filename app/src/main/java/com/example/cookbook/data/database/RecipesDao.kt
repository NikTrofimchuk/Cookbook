package com.example.cookbook.data.database

import androidx.room.*
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
    suspend fun insertBookmarks(bookmarkEntity: BookmarkEntity):Long

    @Query("SELECT * FROM bookmarks_table ORDER BY id ASC")
    fun readBookmarks(): Flow<List<BookmarkEntity>>

    @Query("DELETE FROM bookmarks_table WHERE id = :id")
    suspend fun deleteBookmark(id: Int)

    @Query("SELECT * FROM basket_table ORDER BY id ASC")
    fun readBasket(): Flow<List<BasketEntity>>

    @Query("DELETE FROM basket_table")
    fun deleteBasketTable()

    @Transaction
    fun insertBasket(basketEntity: BasketEntity) {
        // Проверяем, есть ли запись с таким же названием рецепта
        val existing = getByName(basketEntity.recipesName)
        if (existing == null) {
            // Если запись не найдена, вставляем новую
            insert(basketEntity)
        } else {
            // Иначе, обновляем старую запись
            update(existing.multiplier+1, existing.recipesName)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(basketEntity: BasketEntity)

    @Query("UPDATE basket_table SET multiplier = :multiplier WHERE recipesName = :name")
    fun update(multiplier: Int, name: String)

    @Query("SELECT * FROM basket_table WHERE recipesName = :name")
    fun getByName(name: String): BasketEntity?
}