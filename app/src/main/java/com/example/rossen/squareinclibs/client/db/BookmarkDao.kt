package com.example.rossen.squareinclibs.client.db

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
internal interface BookmarkDao {

    @Query("SELECT * FROM Bookmarks")
    fun getBookmarks(): Flowable<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(entity: BookmarkEntity)

    @Delete
    fun deleteBookmark(entity: BookmarkEntity)
}