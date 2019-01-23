package com.example.rossen.squareinclibs.dependancyInjection.module

import android.content.Context
import com.example.rossen.squareinclibs.client.db.BookmarkDao
import com.example.rossen.squareinclibs.client.db.BookmarksDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule(context: Context) {

    var db: BookmarksDB = BookmarksDB.getInstance(context)

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