package com.example.cookbook.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookbook.util.Constants

@Entity(tableName = Constants.BASKET_TABLE)
class BasketEntity (

    val recipesName: String,

    val amount: Double,

    val image: String?,

    val unit: String,

    var multiplier: Int = 1

    )

{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}