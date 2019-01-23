package com.example.rossen.squareinclibs.client.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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