package com.example.rossen.squareinclibs.model
/**
 * Class representing the state of stargazers.
 * They can be in state Loading - when the app is loading the stargazers for the current repo
 * state StargazersError is when we were not able to fetch the stargazers due to a network error
 * state Stargazers is used for when we already have all the stargazers in memory
 */
sealed class StargazersState {
    object Loading : StargazersState()
    data class StargazersError(val message: String?) : StargazersState()
    data class Stargazers(val stargazers: List<Stargazer>) : StargazersState()
}