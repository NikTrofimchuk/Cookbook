package com.example.cookbook.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookbook.util.Constants

@Entity(tableName = Constants.MYRECIPES_TABLE)
class MyRecipesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "time")
    var time: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "instruction")
    var instruction: String,

    @ColumnInfo(name = "image")
    var image: String,
)
{

}