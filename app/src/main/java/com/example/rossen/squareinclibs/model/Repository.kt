package com.example.rossen.squareinclibs.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName

/**
 * POJO for repository object
 */
class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("stargazers_count") val stargazerCount: Int
) : BaseObservable() {
    var isBookmarked: Boolean=false
        @Bindable get() = field
        set(value) {
            field = value
            notifyChange()
        }

    var stargazers: List<Stargazer>? = null
}