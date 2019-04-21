package com.example.rossen.squareinclibs.di.component

import com.example.rossen.squareinclibs.client.db.BookmarksDB
import com.example.rossen.squareinclibs.dataProvider.DataProvider
import com.example.rossen.squareinclibs.di.module.ContextModule
import com.example.rossen.squareinclibs.di.module.DBModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, DBModule::class])
interface DbComponent {
    fun inject(dataProvider: DataProvider)

    fun providesDataBase() : BookmarksDB
}