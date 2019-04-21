package com.example.rossen.squareinclibs

import android.app.Application
import com.example.rossen.squareinclibs.di.component.DaggerDbComponent
import com.example.rossen.squareinclibs.di.component.DbComponent
import com.example.rossen.squareinclibs.di.module.DBModule

class App : Application() {

    lateinit var dbComponent: DbComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        dbComponent = DaggerDbComponent.builder()
            .dBModule(DBModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App private set
    }
}