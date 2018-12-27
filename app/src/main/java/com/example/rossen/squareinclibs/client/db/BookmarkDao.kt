package com.example.rossen.squareinclibs.client.db

import android.arch.persistence.room.*
import io.reactivex.Flowable

/**
 * Interface for all methods to interact with the database
 */
@Dao
internal interface BookmarkDao {

    @Query("SELECT * FROM Bookmarks")
    fun getBookmarks(): Flowable<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(entity: BookmarkEntity)

    @Query("DELETE FROM Bookmarks WHERE repoName = :repositoryName")
    fun deleteBookmark(repositoryName: String)
}