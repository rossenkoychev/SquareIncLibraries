package com.example.rossen.squareinclibs.di.module

import android.content.Context
import com.example.rossen.squareinclibs.client.db.BookmarkDao
import com.example.rossen.squareinclibs.client.db.BookmarksDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room



@Module
class DBModule(context: Context) {

    val db: BookmarksDB = Room.databaseBuilder(context, BookmarksDB::class.java, "bookmarks.db").build()
    //BookmarksDB.getInstance(context)

    @Provides
    @Singleton
    fun providesDataBase(): BookmarksDB {
        return db
    }

    @Provides
    @Singleton
    fun providesBookmarkDao(): BookmarkDao {
        return db.bookmarkDao()
    }
}