package com.example.rossen.squareinclibs.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.rossen.squareinclibs.model.DataProvider
import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.model.Stargazer

class LibraryListViewModel(val context: Application) : AndroidViewModel(context) {

    var repositories: MutableLiveData<List<Repository>> = MutableLiveData()
    var selectedRepo: MutableLiveData<Repository> = MutableLiveData()
    var stargazers: MutableLiveData<List<Stargazer>> = MutableLiveData()

    private val dataProvider: DataProvider = DataProvider()

    init {
        repositories = dataProvider.repos
        dataProvider.getRepos()
        stargazers = dataProvider.stargazers
    }

    fun setSelectedRepoValue(selected: Repository?) {
       //nullify stargazers data when showing new fragment, because if it should
       // be loaded form web there is a chance to show old data for a moment
        stargazers.value = null
        //its safe to assume that the repos list is not null since the user has selected one
        for (repository in repositories.value!!) {
            if (selected == repository) {
                selectedRepo.value = selected
                if (repository.stargazers == null) {
                    dataProvider.getStargazers(repository)
                } else {
                    stargazers.value = repository.stargazers
//TODO do i need to do anything here ?
                }
                break
            }
        }
    }

//    fun getStargazers(): MutableLiveData<List<Stargazer>>
//    {
//        return stargazers
//    }
//    fun getStargazers(repository: Repository) {
//        dataProvider.getStargazers(repository)
//    }

}