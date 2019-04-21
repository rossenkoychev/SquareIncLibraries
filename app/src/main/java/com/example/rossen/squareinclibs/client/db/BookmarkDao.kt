package com.example.rossen.squareinclibs.client.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import javax.inject.Singleton

/**
 * Interface for all methods to interact with the database
 */
@Dao
@Singleton
interface BookmarkDao {

    @Query("SELECT * FROM Bookmarks")
    fun getBookmarks(): Flowable<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(entity: BookmarkEntity)

    @Query("DELETE FROM Bookmarks WHERE repoName = :repositoryName")
    fun deleteBookmark(repositoryName: String)
}