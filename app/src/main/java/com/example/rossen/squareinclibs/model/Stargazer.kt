package com.example.rossen.squareinclibs.model

import com.google.gson.annotations.SerializedName

class Stargazer(
    @SerializedName("login") val userName: String,
    @SerializedName("avatar_url") val avatar: String
)