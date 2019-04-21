package com.example.rossen.squareinclibs.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rossen.squareinclibs.dataProvider.DataProvider
import com.example.rossen.squareinclibs.model.RepositoriesState
import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.model.StargazersState

/**
 * SquareIncLibsViewModel holds state of data for all repos, the currently selected repository and all its stargazers
 */
class SquareIncLibsViewModel(val context: Application) : AndroidViewModel(context) {
    private val dataProvider: DataProvider = DataProvider()

    val repoState: LiveData<RepositoriesState>
    val stargazersState: LiveData<StargazersState>
    var selectedRepo: MutableLiveData<Repository> = MutableLiveData()

    init {
        repoState = dataProvider.reposState
        stargazersState = dataProvider.stargazersState
        getRepos()
    }

    private fun getRepos() {
        dataProvider.getRepos()
    }

    fun setSelectedRepoValue(selected: Repository?) {
        selected?.let {
            selectedRepo.value = it
            dataProvider.getStargazers(it)
        }
    }

    fun addToBookmarks(repo: Repository) {
        dataProvider.addBookmark(repo)
    }

    fun deleteBookMark(repo: Repository) {
        dataProvider.deleteBookmark(repo)
    }
}