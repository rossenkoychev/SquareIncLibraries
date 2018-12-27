package com.example.rossen.squareinclibs.model

/**
 * Class representing the state of repositories.
 * They can be in state Loading - when the app is just started;
 * state RepoError is when we were not able to fetch the repositories due to a network error
 * state Repositories is used for when we already have all the repositories in memory
 */
sealed class RepositoriesState {
    object Loading : RepositoriesState()
    data class ReposError(val message: String?) : RepositoriesState()
    data class Repositories(val repositories: List<Repository>) : RepositoriesState()
}