package com.example.rossen.squareinclibs.model

sealed class StargazersState {
    object Loading : StargazersState()
    data class StargazersError(val message: String?) : StargazersState()
    data class Stargazers(val stargazers: List<Stargazer>) : StargazersState()
}