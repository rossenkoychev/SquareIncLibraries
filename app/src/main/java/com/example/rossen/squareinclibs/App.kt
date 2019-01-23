package com.example.rossen.squareinclibs

import android.app.Application
import com.example.rossen.squareinclibs.dependancyInjection.component.DaggerDbComponent
import com.example.rossen.squareinclibs.dependancyInjection.component.DbComponent
import com.example.rossen.squareinclibs.dependancyInjection.module.DBModule

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