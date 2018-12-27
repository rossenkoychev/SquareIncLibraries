package com.example.rossen.squareinclibs.client.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Database for the app's bookmarks. Uses a singleton pattern to always get the same instance of the DB
 */
const val DB_NAME = "bookmarks.db"

@Database(entities = [BookmarkEntity::class], version = 1)
internal abstract class BookmarksDB : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        private var INSTANCE: BookmarksDB? = null

        fun getInstance(context: Context): BookmarksDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BookmarksDB::class.java, DB_NAME
            )
                .build()

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}