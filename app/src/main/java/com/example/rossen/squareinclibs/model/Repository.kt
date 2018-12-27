package com.example.rossen.squareinclibs.model

import com.google.gson.annotations.SerializedName

/**
 * POJO for repository object
 */
class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("stargazers_count") val stargazerCount: Int
) {
    var isBookmarked: Boolean = false
    var stargazers: List<Stargazer>? = null
}