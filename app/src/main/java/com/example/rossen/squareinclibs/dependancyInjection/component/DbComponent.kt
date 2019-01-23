package com.example.rossen.squareinclibs.dependancyInjection.component

import com.example.rossen.squareinclibs.client.db.BookmarksDB
import com.example.rossen.squareinclibs.dataProvider.DataProvider
import com.example.rossen.squareinclibs.dependancyInjection.module.ContextModule
import com.example.rossen.squareinclibs.dependancyInjection.module.DBModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, DBModule::class])
interface DbComponent {
    fun inject(dataProvider: DataProvider)

    fun providesDataBase() : BookmarksDB
}