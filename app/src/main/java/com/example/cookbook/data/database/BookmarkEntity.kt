package com.example.cookbook.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cookbook.util.Constants

@TypeConverters(BookmarksTypeConverter::class)
@Entity(tableName = Constants.BOOKMARKS_TABLE)
class BookmarkEntity (
    var result: com.example.cookbook.models.Result
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}