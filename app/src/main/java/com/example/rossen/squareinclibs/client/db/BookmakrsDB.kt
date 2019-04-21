package com.example.rossen.squareinclibs.client.db

import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Singleton

/**
 * Database for the app's bookmarks. Uses a singleton pattern to always get the same instance of the DB
 */
@Singleton
@Database(entities = [BookmarkEntity::class], version = 1)
abstract class BookmarksDB : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao
}