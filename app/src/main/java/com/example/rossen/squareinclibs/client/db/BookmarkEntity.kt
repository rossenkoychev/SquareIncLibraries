package com.example.rossen.squareinclibs.client.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Class representing a single Bookmark entity.
 */
@Entity(tableName = "Bookmarks")
data class BookmarkEntity(
    @ColumnInfo(name = "repoName") val repoName: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}