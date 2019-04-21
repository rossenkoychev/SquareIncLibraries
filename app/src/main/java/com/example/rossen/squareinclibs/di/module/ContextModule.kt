package com.example.rossen.squareinclibs.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ContextModule (val context: Context){

    @Provides
    @Singleton
    @Named("ApplicationContext")
    fun provideContext():Context{
        return context
    }
}