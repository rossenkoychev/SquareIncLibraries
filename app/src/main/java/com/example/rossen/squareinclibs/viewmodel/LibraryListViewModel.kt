package com.example.rossen.squareinclibs.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.rossen.squareinclibs.dataProvider.DataProvider
import com.example.rossen.squareinclibs.model.RepositoriesState
import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.model.StargazersState

/**
 * LibraryListViewModel holds data for all repos, the currently selected repository and all its stargazers
 */
class LibraryListViewModel(val context: Application) : AndroidViewModel(context) {
    private val dataProvider: DataProvider =
        DataProvider(context)

    val repoState: LiveData<RepositoriesState>
    val stargazersState: LiveData<StargazersState>
    var selectedRepo: MutableLiveData<Repository> = MutableLiveData()

    init {
        repoState = dataProvider.reposState
        stargazersState = dataProvider.stargazersState
        dataProvider.getRepos()
    }

    fun setSelectedRepoValue(selected: Repository?) {
        //its safe to assume that the repos list is not null since the user has selected one
        selected?.let {
            selectedRepo.value = it
            dataProvider.getStargazers(it)
        }
    }

    fun addToBookmarks(repo:Repository){
        dataProvider.addBookmark(repo)
    }

    fun deleteBookMark(repo:Repository){
        dataProvider.deleteBookmark(repo)
    }
}