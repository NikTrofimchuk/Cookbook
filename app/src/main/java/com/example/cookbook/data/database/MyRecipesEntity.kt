package com.example.cookbook.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookbook.util.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.MYRECIPES_TABLE)
class MyRecipesEntity(
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
): Parcelable
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}