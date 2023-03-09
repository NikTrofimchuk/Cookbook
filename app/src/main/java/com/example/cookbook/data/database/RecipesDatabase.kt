package com.example.cookbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [RecipesEntity::class, MyRecipesEntity::class, BookmarkEntity::class, BasketEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class, BookmarksTypeConverter::class, BasketTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}