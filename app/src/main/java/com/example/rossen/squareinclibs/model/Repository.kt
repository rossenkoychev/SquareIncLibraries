package com.example.rossen.squareinclibs.model

import com.google.gson.annotations.SerializedName

class Repository(@SerializedName("name") val name: String, @SerializedName("stargazers_count") val stargazerCount: Int) {
    var isBookmarked = false
    var stargazers: List<Stargazer> = listOf()
}