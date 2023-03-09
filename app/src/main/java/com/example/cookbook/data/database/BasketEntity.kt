package com.example.cookbook.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cookbook.models.ExtendedIngredient
import com.example.cookbook.util.Constants

@TypeConverters(BasketTypeConverter::class)
@Entity(tableName = Constants.BASKET_TABLE)
class BasketEntity (

    var recipesName: String,

    var extendedIngredient: List<ExtendedIngredient>,

    var multiplier: Int = 1

    )
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}