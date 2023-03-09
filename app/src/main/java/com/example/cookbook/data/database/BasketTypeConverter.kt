package com.example.cookbook.data.database

import androidx.room.TypeConverter
import com.example.cookbook.models.ExtendedIngredient
import com.example.cookbook.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BasketTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun ingredientsToString(ingredients: List<ExtendedIngredient>): String {
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun stringToIngredients(data: String): List<ExtendedIngredient> {
        val listType = object : TypeToken<ExtendedIngredient>() {}.type
        return gson.fromJson(data, listType)
    }
}