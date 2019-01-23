package com.example.rossen.squareinclibs.dependancyInjection.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ContextModule (val context: Context){

    @Provides
    @Named("ApplicationContext")
    fun provideContext():Context{
        return context
    }
}