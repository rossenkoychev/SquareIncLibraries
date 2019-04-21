package com.example.rossen.squareinclibs.dataProvider

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource


object LiveDataStateIdlingResource {
    private const val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource {
        return mCountingIdlingResource
    }
}