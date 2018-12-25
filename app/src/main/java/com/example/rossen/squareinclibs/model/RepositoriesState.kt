package com.example.rossen.squareinclibs.model


sealed class RepositoriesState {
    object Loading : RepositoriesState()
    data class ReposError(val message: String?) : RepositoriesState()
    data class Repositories(val repositories: List<Repository>) : RepositoriesState()
}