package com.example.cookbook.data.database

import androidx.room.TypeConverter
import com.example.cookbook.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BookmarksTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listType)
    }

}